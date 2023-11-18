package blogservices.blogdevbackend.domain.user.dto.auth;


import blogservices.blogdevbackend.domain.user.domain.Role;
import blogservices.blogdevbackend.domain.user.domain.User;
import lombok.*;

import org.springframework.security.crypto.password.PasswordEncoder;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class SignupRequestDto {
    @Email // null 과 "" 과 " " 모두 허용하지 않는다.
    private String email;

    private String password;
    @Size(min = 2, max = 6)
    @NotBlank
    private String name;
    private String passwordConfirm;
    private String provider;
    private Long providerId;

    // 일반 회원가입
    public User toEntity(PasswordEncoder passwordEncoder) {
        return User.builder()
                .email(email) // 이메일
                .password(passwordEncoder.encode(password)) // 비밀번호
                .name(name) // 이름
                .role(Role.ROLE_USER) // 유저 역활
                .build();
    }

    // OAuth2 회원가입
    public User toEntity() {
        return User.builder()
                .email(email)
                .name(name)
                .provider(provider)
                .providerId(providerId)
                .role(Role.ROLE_USER) // 유저 역활
                .build();
    }


}
