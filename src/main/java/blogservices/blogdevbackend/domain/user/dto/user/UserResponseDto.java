package blogservices.blogdevbackend.domain.user.dto.user;


import blogservices.blogdevbackend.domain.user.domain.User;
import lombok.Getter;

@Getter
public class UserResponseDto {
    private final Long userId;
    private final String email;
    private final String name;

    public UserResponseDto(User entity) {
        this.userId = entity.getUserId();
        this.email = entity.getEmail();
        this.name = entity.getName();
    }
}
