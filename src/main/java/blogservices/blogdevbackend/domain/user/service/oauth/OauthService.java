package blogservices.blogdevbackend.domain.user.service.oauth;

import blogservices.blogdevbackend.domain.user.dto.auth.SignupRequestDto;
import blogservices.blogdevbackend.domain.user.dto.oauth.KakaoProfile;
import blogservices.blogdevbackend.domain.user.dto.oauth.KakaoResponseDto;
import blogservices.blogdevbackend.domain.user.dto.oauth.KakaoTokens;

public interface OauthService {

    KakaoTokens getKakaoToken(String code);

    KakaoResponseDto kakaoSaveUser(String token, Integer expiresIn, String refreshToken);

    KakaoProfile findKakaoProfile(String kakaoAccessToken);


}
