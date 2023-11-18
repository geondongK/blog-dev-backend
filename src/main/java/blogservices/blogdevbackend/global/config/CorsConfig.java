package blogservices.blogdevbackend.global.config;


import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

@Configuration
public class CorsConfig {

    @Value("${spring.url.client-url}")
    private String clientUri;

    @Bean
    public CorsFilter corsFilter() {
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        CorsConfiguration config = new CorsConfiguration();

        // 사용자 자격 증명이 지원되는지 여부
        config.setAllowCredentials(true);
        //허용할 url 설정
        config.addAllowedOrigin(clientUri);
        // 허용할 헤더 설정
        config.addAllowedHeader("*");
        // 허용할 http method
        config.addAllowedMethod("*");
        // 클라이언트가 접근 할 수 있는 서버 응답 헤더
        config.addExposedHeader("Authorization");

        source.registerCorsConfiguration("/**", config);

        return new CorsFilter(source);
    }
}
