package blogservices.blogdevbackend.domain.user.controller.v1;

import blogservices.blogdevbackend.domain.user.dto.auth.LoginRequestDto;
import blogservices.blogdevbackend.domain.user.dto.auth.SignupRequestDto;
import blogservices.blogdevbackend.domain.user.dto.token.TokenDto;
import blogservices.blogdevbackend.domain.user.dto.token.TokenRequestDto;
import blogservices.blogdevbackend.domain.user.dto.user.UserResponseDto;
import blogservices.blogdevbackend.domain.user.service.authService.AuthService;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService service;

    /* 회원가입 */
    @PostMapping("/signup")
    public ResponseEntity<Long> signup(@RequestBody SignupRequestDto request) {


        return ResponseEntity.ok(service.signup(request));
    }

    /* 로그인 */
    @PostMapping("/login")
    public ResponseEntity<List<UserResponseDto>> login(@RequestBody LoginRequestDto request) {
        return ResponseEntity.ok(service.login(request));
    }

    /* 토큰 재발급 */
    @PostMapping("/reissue")
    public ResponseEntity<TokenDto> reissue(@RequestBody TokenRequestDto request) {
        return ResponseEntity.ok(service.reissue(request));
    }


}


