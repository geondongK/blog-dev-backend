package blogservices.blogdevbackend.domain.user.service.oauth;

import blogservices.blogdevbackend.domain.user.dto.auth.SignupRequestDto;
import blogservices.blogdevbackend.domain.user.dto.oauth.KakaoProfile;
import blogservices.blogdevbackend.domain.user.dto.oauth.KakaoResponseDto;
import blogservices.blogdevbackend.domain.user.dto.oauth.KakaoTokens;

import javax.servlet.http.HttpServletResponse;

public interface OauthService {

    KakaoTokens getKakaoToken(String code);

    KakaoResponseDto kakaoSaveUser(String token, HttpServletResponse cookieResponse);

    KakaoProfile findKakaoProfile(String kakaoAccessToken);


}
