package blogservices.blogdevbackend.global.jwt.provider;

import blogservices.blogdevbackend.domain.user.domain.Role;
import blogservices.blogdevbackend.domain.user.dto.token.TokenDto;
import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.*;
import java.util.stream.Collectors;

/* JWT 생성 및 휴요성 검증 */
@Slf4j
@Component
@RequiredArgsConstructor
public class JwtTokenProvider {

    @Value("${spring.jwt.secret-key}")
    private String secretKey;
    @Value("${spring.jwt.access-token-exp}")
    private long accessTokenExpiresIn;
    @Value("${spring.jwt.refresh-token-exp}")
    private long refreshTokenExpiresIn;

    // secretKey를 Base64로 인코딩
    private Key getSignInKey() {
        byte[] keyBytes = Decoders.BASE64URL.decode(secretKey);
        return Keys.hmacShaKeyFor(keyBytes);
    }

    public TokenDto generateAccessToken(Long userId, Role roles) {
        // user 구분을 위해 Claims에 User Pk값 넣어줌
        Claims claims = Jwts.claims().setSubject(String.valueOf(userId));

        // 생성날짜, 만료날짜를 위한 Date
        Date now = new Date();

        String accessToken = Jwts.builder()
                .setSubject(claims.getSubject()) // 주체
                .claim("roles", roles) // roles
                //.setClaims(claims)
                .setIssuedAt(now) // 생성일
                .setExpiration(new Date(now.getTime() + accessTokenExpiresIn)) // 만료일
                .signWith(getSignInKey(), SignatureAlgorithm.HS256) // header
                .compact();

        return TokenDto.builder()
                .grantType("Bearer")
                .accessToken(accessToken)
                .accessTokenExpiresIn(accessTokenExpiresIn)
                .build();
    }

    public TokenDto generateRefreshToken(Long userId, Role roles) {
        // 생성날짜, 만료날짜를 위한 Date
        Date now = new Date();

        // Refresh 토큰 생성
        String refreshToken = Jwts.builder()
                .setExpiration(new Date(now.getTime() + refreshTokenExpiresIn))
                .signWith(getSignInKey(), SignatureAlgorithm.HS256)
                .compact();

        return TokenDto.builder()
                .grantType("Bearer")
                .refreshToken(refreshToken)
                .accessTokenExpiresIn(accessTokenExpiresIn)
                .build();
    }

    // Jwt 인증정보 조회.
    public Authentication getAuthentication(String token) {
        Claims claims = extractAllClaims(token);

        if (claims.get("roles") == null) {
            throw new RuntimeException("권한 정보가 없는 토큰입니다");
        }

        // 클레임에서 권한 정보 가져오기.
        Collection<? extends GrantedAuthority> authorities =
                Arrays.stream(claims.get("roles").toString().split(","))
                        .map(SimpleGrantedAuthority::new)
                        .collect(Collectors.toList());

        // UserDetails 객체를 만들어서 Authentication 리턴
        UserDetails principal = new User(claims.getSubject(), "", authorities);

        return new UsernamePasswordAuthenticationToken(principal, "", authorities);
    }

    // JWT 검증
    public boolean validationToken(String token) {
        try {
            Jwts.parserBuilder().setSigningKey(getSignInKey()).build().parseClaimsJws(token);
            return true;
        } catch (io.jsonwebtoken.security.SecurityException | MalformedJwtException e) {

            log.info("잘못된 JWT 서명입니다.");
        } catch (ExpiredJwtException e) {
            log.info("만료된 JWT 토큰입니다.");
        } catch (UnsupportedJwtException e) {
            log.info("지원되지 않는 JWT 토큰입니다.");
        } catch (IllegalArgumentException e) {
            log.info("JWT 토큰이 잘못되었습니다.");
        }
        return false;
    }

    // 토큰 복호화
    private Claims extractAllClaims(String token) {
        try {
            return Jwts
                    .parserBuilder()
                    .setSigningKey(getSignInKey())
                    .build()
                    .parseClaimsJws(token)
                    .getBody();
        } catch (ExpiredJwtException e) {
            return e.getClaims();
        }

    }

}
