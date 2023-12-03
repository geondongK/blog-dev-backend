package blogservices.blogdevbackend.domain.user.service.auth;

import blogservices.blogdevbackend.domain.user.domain.RefreshToken;
import blogservices.blogdevbackend.domain.user.domain.Role;
import blogservices.blogdevbackend.domain.user.domain.User;
import blogservices.blogdevbackend.domain.user.dto.auth.LoginRequestDto;
import blogservices.blogdevbackend.domain.user.dto.auth.LoginResponseDto;
import blogservices.blogdevbackend.domain.user.dto.auth.SignupRequestDto;
import blogservices.blogdevbackend.domain.user.dto.token.TokenDto;
import blogservices.blogdevbackend.domain.user.dto.token.TokenRequestDto;
import blogservices.blogdevbackend.global.common.cookies.CookiesUtil;
import blogservices.blogdevbackend.global.exception.GlobalExceptionResponseCode;
import blogservices.blogdevbackend.global.exception.GlobalException;
import blogservices.blogdevbackend.domain.user.repository.RefreshRepository;
import blogservices.blogdevbackend.domain.user.repository.UserRepository;
import blogservices.blogdevbackend.global.jwt.provider.JwtTokenProvider;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseCookie;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional
public class AuthServiceImpl implements AuthService {
    private final UserRepository userRepository;
    private final RefreshRepository refreshRepository;
    private final JwtTokenProvider jwtTokenProvider;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManagerBuilder authenticationManagerBuilder;


    /* 회원가입 */
    @Override
    public Long signup(SignupRequestDto request) {

        // 이메일 중복 여부 체크
        if (userRepository.existsByEmail(request.getEmail())) {
            throw new GlobalException(GlobalExceptionResponseCode.EMAIL_EXISTS_FOUND);
        }

        // 별명 중복 여부 체크
        if (userRepository.existsByName(request.getName())) {
            throw new GlobalException(GlobalExceptionResponseCode.NAME_EXISTS_FOUND);
        }

        // 비밀번호 확인
        if (!request.getPassword().equals(request.getPasswordConfirm())) {
            throw new GlobalException(GlobalExceptionResponseCode.PASSWORD_NOT_SAME);
        }

        return userRepository.save(request.toEntity(passwordEncoder)).getUserId();

    }

    /* 로그인 */
    @Override
    public LoginResponseDto login(LoginRequestDto request, HttpServletResponse cookieResponse) {

        // 사용자 존재 유무 체크
        User user = userRepository.findByEmail(request.getEmail());
        if (user == null) throw new GlobalException(GlobalExceptionResponseCode.USER_NOT_FOUND);

        // 비밀번호 체크
        if (!passwordEncoder.matches(request.getPassword(), user.getPassword()))
            throw new GlobalException(GlobalExceptionResponseCode.USER_NOT_FOUND);

        // AccessToken 생성.
        TokenDto getAccessToken = jwtTokenProvider.generateAccessToken(user.getUserId(), Role.ROLE_USER);
        TokenDto getRefreshToken = jwtTokenProvider.generateRefreshToken(user.getUserId(), Role.ROLE_USER);

        // RefreshToken 정보 저장.
        RefreshToken refreshToken = RefreshToken.builder()
                .userId(user.getUserId())
                .key(String.valueOf(user.getEmail()))
                .token(getRefreshToken.getRefreshToken())
                .build();

        refreshRepository.save(refreshToken);

//        HttpHeaders httpHeaders = new HttpHeaders();
//        httpHeaders.add("Authorization", "Bearer " + getAccessToken.getAccessToken());

        // 쿠키 생성
        CookiesUtil.setCookies(cookieResponse, "token", getAccessToken.getAccessToken());

        // 로그인 정보 반환.
        LoginResponseDto response = new LoginResponseDto(user, getAccessToken.getAccessTokenExpiresIn(), getAccessToken.getAccessToken());

        return response;
    }

    /* 로그아웃 */
    @Override
    public void logout(HttpServletResponse cookieResponse) {
        // 쿠키 삭제
        CookiesUtil.removeCookies(cookieResponse, "token");
    }

    /* 토큰 재발급 */
    @Override
    public TokenDto reissue(TokenDto request, HttpServletRequest cookieRequest, HttpServletResponse cookieResponse) {
        // AccessToken 가져오기.
        String getAccessToken = CookiesUtil.getCookies(cookieRequest);

        log.info("getAccessToken = {}", getAccessToken);

        // 토큰 인증정보 조회.
        Authentication getAuthentication = jwtTokenProvider.getAuthentication(getAccessToken);


        // user pk로 유저 검색 / repo에 저장된 refresh token 이 없음
        User user = userRepository.findById(Long.valueOf(getAuthentication.getName()))
                .orElseThrow(() -> new GlobalException(GlobalExceptionResponseCode.USER_NOT_FOUND));


        // 저장소에서 User ID 를 기반으로 RefreshToken 값 가져옴
        RefreshToken refreshToken = refreshRepository.findByKey(user.getEmail())
                .orElseThrow(() -> new GlobalException(GlobalExceptionResponseCode.REFRESH_TOKEN_NOT_EXIST));

        log.info("refreshToken = {}", refreshToken.getToken());

        // 만료된 refresh token 에러
        if (!jwtTokenProvider.validationToken(refreshToken.getToken()))
            throw new GlobalException(GlobalExceptionResponseCode.REFRESH_TOKEN_NOT_EXIST);

        // AccesToken 토큰 재발급 및 반환
        TokenDto newGeneratToken = jwtTokenProvider.generateAccessToken(user.getUserId(), user.getRole());

        // 쿠키 생성
        CookiesUtil.setCookies(cookieResponse, "token", newGeneratToken.getAccessToken());

        return newGeneratToken;
    }
}
