package blogservices.blogdevbackend.domain.user.service.auth;

import blogservices.blogdevbackend.domain.user.dto.auth.LoginRequestDto;
import blogservices.blogdevbackend.domain.user.dto.auth.LoginResponseDto;
import blogservices.blogdevbackend.domain.user.dto.auth.SignupRequestDto;
import blogservices.blogdevbackend.domain.user.dto.token.TokenDto;
import blogservices.blogdevbackend.domain.user.dto.token.TokenRequestDto;

public interface AuthService {

    Long signup(SignupRequestDto request);

    LoginResponseDto login(LoginRequestDto request);

    TokenDto reissue(TokenRequestDto request);

}
