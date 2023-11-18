package blogservices.blogdevbackend.domain.user.dto.oauth;

import blogservices.blogdevbackend.domain.user.domain.User;
import lombok.Getter;

@Getter
public class KakaoResponseDto {
    private final User user;
    private final Long expiresTime;
    private final String token;


    public KakaoResponseDto(User user, Long expiresTime, String token) {
        this.user = user;
        this.expiresTime = expiresTime;
        this.token = token;
    }
}
