package blogservices.blogdevbackend.domain.user.domain;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(name = "token")
public class RefreshToken {


    //@GeneratedValue(strategy = GenerationType.IDENTITY)
    //private Long id;
    @Id
    private Long userId; // 사용자 Id, SNS 로그인을 위해 따로등록
    @Column(length = 50, name = "keyId")
    private String key; // 이메일
    private String token; // 토큰


    public RefreshToken updateToken(String token) {
        this.token = token;
        return this;
    }

    @Builder
    public RefreshToken(String key, Long userId, String token) {
        this.key = key;
        this.userId = userId;
        this.token = token;
    }
}
