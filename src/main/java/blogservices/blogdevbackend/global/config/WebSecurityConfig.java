package blogservices.blogdevbackend.global.config;

import blogservices.blogdevbackend.global.jwt.exception.JwtAccessDeniedHandler;
import blogservices.blogdevbackend.global.jwt.exception.JwtAuthEntryPoint;
import blogservices.blogdevbackend.global.jwt.filter.JwtAuthFilter;
import blogservices.blogdevbackend.global.jwt.provider.JwtTokenProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.filter.CorsFilter;


@Configuration
@RequiredArgsConstructor
// Spring Security 의 가장 기본적인 설정이며 JWT 를 사용하지 않더라도 이 설정은 기본으로 들어간다.
public class WebSecurityConfig {

    private final JwtTokenProvider jwtTokenProvider;
    private final JwtAuthEntryPoint jwtAuthEntryPoint;
    private final JwtAccessDeniedHandler jwtAccessDeniedHandler;
    private final CorsFilter corsFilter;

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    // 요청이 들어오면 Spring Security 확인.
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .csrf().disable()
                // Basic 인증, Bearer token 인증방법을 사용하기 때문에 비활성화
                .httpBasic().disable()

                // 시큐리티는 기본적으로 세션을 사용
                // 세션을 사용하지 않기 때문에 세션 설정을 Stateless 로 설정
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)

                //.and()
                //.logout().deleteCookies(CookieAuth)
                .and()
                .authorizeHttpRequests()
                //.requestMatchers(HttpMethod.OPTIONS, "/**").permitAll()
                // 보호되지 않은 엔드포인트
                .antMatchers("/api/v1/auth/**", "/api/v1/oauth/kakao/callback/**", "/api/v1/oauth/kakao/**").permitAll()
                .antMatchers(HttpMethod.GET,
                        "/api/v1/post/**", "/api/v1/comment/**", "/api/v1/like/**"
                ).permitAll()
                // 그 이외에는 인증이 필요
                .anyRequest().authenticated()

                // exception handling 클래스를 추가
                .and()
                .exceptionHandling()
                .authenticationEntryPoint(jwtAuthEntryPoint)
                .accessDeniedHandler(jwtAccessDeniedHandler)

                .and()
                .addFilter(corsFilter)
                .addFilterBefore(new JwtAuthFilter(jwtTokenProvider), UsernamePasswordAuthenticationFilter.class);
        //.apply(new JwtSecurityConfig(jwtTokenProvider));

        return http.build();
    }
}
