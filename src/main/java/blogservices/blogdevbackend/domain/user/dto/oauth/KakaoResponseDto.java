package blogservices.blogdevbackend.domain.user.dto.oauth;

import blogservices.blogdevbackend.domain.user.domain.User;
import lombok.Getter;

@Getter
public class KakaoResponseDto {
    private final User user;
    private final Integer exprTime;
    private final String token;


    public KakaoResponseDto(User user, Integer exprTime, String token) {
        this.user = user;
        this.exprTime = exprTime;
        this.token = token;
    }


}
