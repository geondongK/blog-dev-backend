package blogservices.blogdevbackend.domain.user.service.authService;

import blogservices.blogdevbackend.domain.user.domain.RefreshToken;
import blogservices.blogdevbackend.domain.user.domain.Role;
import blogservices.blogdevbackend.domain.user.domain.User;
import blogservices.blogdevbackend.domain.user.dto.auth.LoginRequestDto;
import blogservices.blogdevbackend.domain.user.dto.auth.SignupRequestDto;
import blogservices.blogdevbackend.domain.user.dto.token.TokenDto;
import blogservices.blogdevbackend.domain.user.dto.token.TokenRequestDto;
import blogservices.blogdevbackend.domain.user.dto.user.UserResponseDto;
import blogservices.blogdevbackend.global.exception.GlobalErrorCode;
import blogservices.blogdevbackend.global.exception.GlobalException;
import blogservices.blogdevbackend.domain.user.repository.RefreshRepository;
import blogservices.blogdevbackend.domain.user.repository.UserRepository;
import blogservices.blogdevbackend.global.auth.JwtTokenProvider;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional
public class AuthServiceImpl implements AuthService {

    private final Logger LOGGER = LoggerFactory.getLogger(AuthServiceImpl.class);

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
            throw new GlobalException(GlobalErrorCode.USER_EXISTS_FOUND);
        }

        return userRepository.save(request.toEntity(passwordEncoder)).getUserId();
    }

    /* 로그인 */
    @Override
    public List<UserResponseDto> login(LoginRequestDto request) {

        // 사용자 존재 유무 체크
        Optional<User> user = Optional.ofNullable(userRepository.findByEmail(request.getEmail()).orElseThrow(() -> new GlobalException(GlobalErrorCode.USER_NOT_FOUND)));

        log.info("password = {}", request.getPassword() != null ? request.getPassword() : "빈값");

        // 비밀번호 체크
        if (!passwordEncoder.matches(request.getPassword(), user.orElseThrow().getPassword()))
            throw new GlobalException(GlobalErrorCode.USER_NOT_FOUND);

        TokenDto tokenDto = jwtTokenProvider.generateToken(user.orElseThrow().getUserId(), Role.ROLE_USER);

        RefreshToken refreshToken = RefreshToken.builder()
                .key(String.valueOf(user.orElseThrow().getEmail()))
                .token(tokenDto.getRefreshToken())
                .build();

        refreshRepository.save(refreshToken);

        log.info("access = {}", tokenDto.getAccessToken());
        log.info("refresh = {}", tokenDto.getRefreshToken());

        return user.stream().map(UserResponseDto::new).collect(Collectors.toList());
    }

    @Override
    public TokenDto reissue(TokenRequestDto request) {
        // 만료된 refresh token 에러
        if (!jwtTokenProvider.validationToken(request.getRefreshToken()))
            throw new RuntimeException("Refresh Token이 유효하지 않습니다");

        // AccessToken -> user pk 값 가져오기.
        String accessToken = request.getAccessToken();
        Authentication authentication = jwtTokenProvider.getAuthentication(accessToken);

        //log.info("authentication = {}", authentication.getName());

        log.info("reiss::ueuser = {}", authentication.getName());

        // user pk로 유저 검색 / repo에 저장된 refresh token 이 없음
        User user = userRepository.findById(Long.valueOf(authentication.getName()))
                .orElseThrow(() -> new GlobalException(GlobalErrorCode.USER_NOT_FOUND));

        log.info("reiss::ueuser = {} role = {}", user.getUserId(), user.getRole());

        // 저장소에서 User ID 를 기반으로 RefreshToken 값 가져옴
        log.info("AuthRefresh::userEmail = {}", user.getEmail());

        RefreshToken refreshToken = refreshRepository.findByKey(user.getEmail())
                .orElseThrow(() -> new GlobalException(GlobalErrorCode.USER_NOT_FOUND));

        log.info("reiss::refreshToken = {}", refreshToken.getToken());

        // Refresh Token 일치하는지 검사
        if (!refreshToken.getToken().equals(refreshToken.getToken()))
            throw new RuntimeException("토큰의 유저 정보가 일치하지 않습니다.");

//        // AccesToken, RefreshToken 토큰 재발급, 리프레쉬 토큰 저장
        TokenDto newGeneratToken = jwtTokenProvider.generateToken(user.getUserId(), user.getRole());

        log.info("reiss::newGeneratToken = {}", newGeneratToken.getRefreshToken());
        RefreshToken updateRefreshToken = refreshToken.updateToken(newGeneratToken.getRefreshToken());
        refreshRepository.save(updateRefreshToken);

        return newGeneratToken;
    }


}
