package blogservices.blogdevbackend.domain.user.controller.v1;

import blogservices.blogdevbackend.domain.user.dto.auth.LoginRequestDto;
import blogservices.blogdevbackend.domain.user.dto.auth.LoginResponseDto;
import blogservices.blogdevbackend.domain.user.dto.auth.SignupRequestDto;
import blogservices.blogdevbackend.domain.user.dto.token.TokenDto;
import blogservices.blogdevbackend.domain.user.dto.token.TokenRequestDto;
import blogservices.blogdevbackend.domain.user.service.auth.AuthService;

import blogservices.blogdevbackend.global.common.response.CommonResponse;
import blogservices.blogdevbackend.global.common.response.ResponseService;
import blogservices.blogdevbackend.global.common.response.SingleResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
@Slf4j
public class AuthController {

    private final AuthService authService;
    private final ResponseService responseService;

    /* 회원가입 */
    @PostMapping("/signup")
    public CommonResponse signup(@RequestBody @Valid SignupRequestDto request) {
        authService.signup(request);
        return responseService.getSuccessResponse();
    }

    /* 로그인 */
    @PostMapping("/login")
    public SingleResponse<LoginResponseDto> login(@RequestBody @Valid LoginRequestDto request) {
        LoginResponseDto response = authService.login(request);
        return responseService.getSingResponse(response);
    }

    /* 토큰 재발급 */
    @PostMapping("/reissue")
    public ResponseEntity<TokenDto> reissue(@RequestBody TokenRequestDto request) {
        TokenDto response = authService.reissue(request);
        return ResponseEntity.ok(response);
    }
}


