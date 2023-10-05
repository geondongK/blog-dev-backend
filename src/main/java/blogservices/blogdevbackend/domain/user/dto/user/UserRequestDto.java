package blogservices.blogdevbackend.domain.user.dto.user;

import blogservices.blogdevbackend.domain.user.domain.User;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class UserRequestDto {

    private Long userId;
    private String email;
    private String password;
    private String name;

    @Builder
    public UserRequestDto(Long userId, String email, String password, String name) {
        this.userId = userId;
        this.email = email;
        this.password = password;
        this.name = name;
    }

    public User toEntity() {
        return User.builder()
                .userId(userId)
                .email(email)
                .password(password)
                .name(name)
                .build();
    }
}
