package blogservices.blogdevbackend.domain.user.domain;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
// 파일명 entity 이름 동일해야함, 아닐 시 지정
@Entity
@Table(name = "user")
public class User implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long userId; // PK

    @Column(length = 50)
    private String email; // 이메일

    @Column(length = 100)
    private String password; // 비밀번호

    @Column(length = 8)
    private String name; // 유저 이름

    @Column(length = 10)
    private String provider; // OAuth2 공급자

    private Long providerId; // OAuth2 공급자 아이디

    @Column(length = 10)
    @Enumerated(EnumType.STRING)
    private Role role; // 유저 역활

    private LocalDateTime userCreateDate = LocalDateTime.now(); // 생성일

    @Builder
    public User(Long userId, String email, String password, String name, String provider, Long providerId, Role role) {
        this.userId = userId;
        this.email = email;
        this.password = password;
        this.name = name;
        this.provider = provider;
        this.providerId = providerId;
        this.role = role;
    }

    //부여된 권한 모음
    @Override
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority(role.name()));
    }

    // 사용자 패스워드 반환
    @Override
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY) // read 못하게 설정
    public String getPassword() {
        return password;
    }

    // 사용자 id 반환
    @Override
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    public String getUsername() {
        return email;
    }

    // 계정 만료 여부
    @Override
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    public boolean isAccountNonExpired() {
        return true;
    }

    // 계정 잠금 여부
    @Override
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    public boolean isAccountNonLocked() {
        return true;
    }

    // 패스워드 만료 여부
    @Override
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    public boolean isCredentialsNonExpired() {
        return true;
    }

    // 계정 사용 가능 여부
    @Override
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    public boolean isEnabled() {
        return true;
    }


}
