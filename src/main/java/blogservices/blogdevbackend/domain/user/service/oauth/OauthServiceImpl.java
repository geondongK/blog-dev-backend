package blogservices.blogdevbackend.domain.user.service.oauth;

import blogservices.blogdevbackend.domain.user.domain.RefreshToken;
import blogservices.blogdevbackend.domain.user.domain.Role;
import blogservices.blogdevbackend.domain.user.domain.User;
import blogservices.blogdevbackend.domain.user.dto.auth.SignupRequestDto;
import blogservices.blogdevbackend.domain.user.dto.oauth.KakaoProfile;
import blogservices.blogdevbackend.domain.user.dto.oauth.KakaoResponseDto;
import blogservices.blogdevbackend.domain.user.dto.oauth.KakaoTokens;
import blogservices.blogdevbackend.domain.user.dto.token.TokenDto;
import blogservices.blogdevbackend.domain.user.repository.RefreshRepository;
import blogservices.blogdevbackend.domain.user.repository.UserRepository;
import blogservices.blogdevbackend.global.common.cookies.CookiesUtil;
import blogservices.blogdevbackend.global.jwt.provider.JwtTokenProvider;
import com.google.gson.Gson;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import javax.servlet.http.HttpServletResponse;


@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class OauthServiceImpl implements OauthService {

    private final RestTemplate restTemplate;
    private final Gson gson;
    private final UserRepository userRepository;
    private final RefreshRepository refreshRepository;
    private final JwtTokenProvider jwtTokenProvider;

    @Value("${spring.oauth.kakao.client-id}")
    private String kakaoClientId;

    @Value("${spring.oauth.kakao.redirect-uri}")
    private String kakaoRedirectUri;

    /* 카카오 이용자 저장 */
    @Override
    public KakaoResponseDto kakaoSaveUser(String token, HttpServletResponse cookieResponse) {
        // 카카오 로그인 정보 가져오기
        KakaoProfile profile = findKakaoProfile(token);

        // 카카오 유저 여부파악
        User user = userRepository.findByProviderId(profile.getId());

        SignupRequestDto signupRequestDto;
        // 카카오 유저 DB에 없으면 생성
        if (user == null) {
            signupRequestDto = SignupRequestDto.builder()
                    .email(profile.getKakao_account().getEmail())
                    .name(profile.getProperties().getNickname())
                    .provider("kakao")
                    .providerId(profile.getId())
                    .build();

            userRepository.save(signupRequestDto.toEntity());
        }

        User getUser = userRepository.findByProviderId(profile.getId());

        TokenDto getAccessToken = jwtTokenProvider.generateAccessToken(getUser.getUserId(), Role.ROLE_USER);
        TokenDto getRefreshToken = jwtTokenProvider.generateRefreshToken(getUser.getUserId(), Role.ROLE_USER);


        // 리프레쉬 토큰 저장
        RefreshToken saveRefreshToken = RefreshToken.builder()
                .userId(getUser.getUserId())
                .token(getRefreshToken.getRefreshToken())
                .build();

        refreshRepository.save(saveRefreshToken);

        // 쿠키 생성
        CookiesUtil.setCookies(cookieResponse, "token", getAccessToken.getAccessToken());

        // SNS 이용자 정보 반환.
        KakaoResponseDto kakaoResponseDto = new KakaoResponseDto(getUser, getAccessToken.getAccessTokenExpiresIn(), getAccessToken.getAccessToken());

        return kakaoResponseDto;
    }

    /* 카카오 이용자 정보 가져오기 */
    @Override
    public KakaoProfile findKakaoProfile(String kakaoAccessToken) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        headers.set("Authorization", "Bearer " + kakaoAccessToken);

        HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(null, headers);

        ResponseEntity<String> response = restTemplate.exchange(
                "https://kapi.kakao.com/v2/user/me", HttpMethod.POST, request, String.class
        );

        KakaoProfile kakaoProfile = null;
        try {
            if (response.getStatusCode() == HttpStatus.OK) {
                kakaoProfile = gson.fromJson(response.getBody(), KakaoProfile.class);
            }

        } catch (Exception e) {
            log.error("Exception::Err_Msg = {}", e.getStackTrace()[0]);
        }

        return kakaoProfile;
    }

    /* 카카오 토큰 가져오기 */
    @Override
    public KakaoTokens getKakaoToken(String code) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add("grant_type", "authorization_code");
        params.add("client_id", kakaoClientId);
        params.add("redirect_uri", kakaoRedirectUri);
        params.add("code", code);

        HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(params, headers);
        ResponseEntity<String> response = restTemplate.exchange(
                "https://kauth.kakao.com/oauth/token", HttpMethod.POST, request, String.class
        );

        KakaoTokens kakaoTokens = null;
        try {
            if (response.getStatusCode() == HttpStatus.OK) {
                kakaoTokens = gson.fromJson(response.getBody(), KakaoTokens.class);
            }

        } catch (Exception e) {
            log.error("Exception::Err_Msg = {}", e.getStackTrace()[0]);
        }

        return kakaoTokens;
    }
}
