package blogservices.blogdevbackend.domain.user.dto.auth;

import blogservices.blogdevbackend.domain.user.domain.User;
import lombok.Getter;

@Getter
public class LoginResponseDto {
    private final User user;
    private final Long expiresTime;
    private final String token;

    public LoginResponseDto(User user, Long expiresTime, String token) {
        this.user = user;
        this.expiresTime = expiresTime;
        this.token = token;
    }
}
