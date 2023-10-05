package blogservices.blogdevbackend.domain.user.dto.auth;


import blogservices.blogdevbackend.domain.user.domain.Role;
import blogservices.blogdevbackend.domain.user.domain.User;
import lombok.*;

import org.springframework.security.crypto.password.PasswordEncoder;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class SignupRequestDto {
    private String email;
    private String password;
    private String name;

    public User toEntity(PasswordEncoder passwordEncoder) {
        return User.builder()
                .email(email)
                .password(passwordEncoder.encode(password))
                .name(name)
                .role(Role.ROLE_USER)
                .build();
    }

    public User toEntity() {
        return User.builder()
                .email(email)
                .name(name)
                .build();
    }

}
