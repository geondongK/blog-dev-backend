package blogservices.blogdevbackend.domain.user.controller.oauth;

import blogservices.blogdevbackend.domain.user.dto.auth.SignupRequestDto;
import blogservices.blogdevbackend.domain.user.dto.oauth.KakaoResponseDto;
import blogservices.blogdevbackend.domain.user.dto.oauth.KakaoTokens;
import blogservices.blogdevbackend.domain.user.service.oauth.OauthService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/oauth")
@RequiredArgsConstructor
@Slf4j
public class OathController {

    private final OauthService oauthService;

    // JWT strings must contain exactly 2 period characters
    @GetMapping("/kakao/callback")
    public ResponseEntity<KakaoResponseDto> getLogin(@RequestParam("code") String code) {

        KakaoTokens kakaoTokens = oauthService.getKakaoToken(code);

        // log.info("kakaoTokens::contorller = {}", kakaoTokens.getRefresh_token());

        // 토큰
        KakaoResponseDto user = oauthService.kakaoSaveUser(kakaoTokens.getAccess_token());

        return ResponseEntity.ok(user);
    }
}