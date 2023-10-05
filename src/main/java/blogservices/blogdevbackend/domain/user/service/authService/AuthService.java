package blogservices.blogdevbackend.domain.user.service.authService;

import blogservices.blogdevbackend.domain.user.dto.auth.LoginRequestDto;
import blogservices.blogdevbackend.domain.user.dto.auth.SignupRequestDto;
import blogservices.blogdevbackend.domain.user.dto.token.TokenDto;
import blogservices.blogdevbackend.domain.user.dto.token.TokenRequestDto;
import blogservices.blogdevbackend.domain.user.dto.user.UserResponseDto;

import java.util.List;

public interface AuthService {

    Long signup(SignupRequestDto request);

    List<UserResponseDto> login(LoginRequestDto request);

    TokenDto reissue(TokenRequestDto request);

    //    List<AuthRequestDTO> login(String email);

//    Long signup(loginRequestDto authRequestDTO);


}
