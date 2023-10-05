package blogservices.blogdevbackend.domain.user.domain;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(name = "refreshToken") // refresh_token
public class RefreshToken {


    //@GeneratedValue(strategy = GenerationType.IDENTITY)
    //private Long id;
    @Id
    @Column(name = "keyId")
    private String key; // key
    private String token;


    public RefreshToken updateToken(String token) {
        this.token = token;
        return this;
    }

    @Builder
    public RefreshToken(String key, String token) {
        this.key = key;
        this.token = token;
    }
}
