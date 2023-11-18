package blogservices.blogdevbackend.domain.user.dto.token;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
public class TokenRequestDto {
    private String accessToken;


    @Builder
    public TokenRequestDto(String accessToken) {
        this.accessToken = accessToken;
    }
}
