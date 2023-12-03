package blogservices.blogdevbackend.domain.user.service.auth;

import blogservices.blogdevbackend.domain.user.dto.auth.LoginRequestDto;
import blogservices.blogdevbackend.domain.user.dto.auth.LoginResponseDto;
import blogservices.blogdevbackend.domain.user.dto.auth.SignupRequestDto;
import blogservices.blogdevbackend.domain.user.dto.token.TokenDto;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public interface AuthService {

    Long signup(SignupRequestDto request);

    LoginResponseDto login(LoginRequestDto request, HttpServletResponse cookieResponse);

    void logout(HttpServletResponse response);

    TokenDto reissue(TokenDto request, HttpServletRequest cookieRequest, HttpServletResponse cookieResponse);

}
