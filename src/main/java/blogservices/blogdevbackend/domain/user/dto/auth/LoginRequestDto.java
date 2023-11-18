package blogservices.blogdevbackend.domain.user.dto.auth;

import blogservices.blogdevbackend.domain.user.domain.Role;
import blogservices.blogdevbackend.domain.user.domain.User;
import lombok.*;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.validation.constraints.NotBlank;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class LoginRequestDto {
    @NotBlank
    private String email; // 이메일
    @NotBlank
    private String password; // 비밀번호

    private User toUser(PasswordEncoder passwordEncoder) {
        return User.builder()
                .email(email)
                .password(passwordEncoder.encode(password))
                .build();
    }

}
