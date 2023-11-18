package blogservices.blogdevbackend.global.jwt.filter;

import blogservices.blogdevbackend.global.jwt.provider.JwtTokenProvider;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


@Slf4j
@RequiredArgsConstructor
@Component
// 가입, 로그인, 재발급을 제외한 모든 Request 요청은 이 필터를 거치기 때문에 토큰 정보가 없거나 유효하지 않으면 정상적으로 수행되지 않음.
public class JwtAuthFilter extends OncePerRequestFilter {

    private final JwtTokenProvider jwtTokenProvider;

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {

        String auth = request.getHeader("Authorization");

        // log.info("auth : {}", auth);

        try {
            // Requset header에서 토큰을 꺼냄
            String token = resolveToken(request);

            // log.info("token = {}", token);

            // 트콘이 null 이 아니거나 잘못된 토큰이 아니거나 기간이 만료가 되지 않았을 경우
            if (token != null && jwtTokenProvider.validationToken(token)) {
                Authentication authentication = jwtTokenProvider.getAuthentication(token);
                SecurityContextHolder.getContext().setAuthentication(authentication);
            }
            // 다음 필터 넘기기
            filterChain.doFilter(request, response);
        } catch (Exception e) {
            log.error("Exception::Err_Msg = {}", e.getStackTrace()[0]);
            throw new RuntimeException(e);
        }
        //filterChain.doFilter(request, response);
    }

    // Request Header 에서 토큰 정보 꺼내오기..
    private String resolveToken(HttpServletRequest request) {
        String authorization = request.getHeader("Authorization");

        // log.info("authorization = {}", request.getHeader("Accept"));

        // StringUtils.hasText() 문자열이 진정한 Text형태인지 확인합니다. 즉, null을 포함해서 공백만 존재한다면 False를 반환합니다
        if (StringUtils.hasText(authorization) && authorization.startsWith("Bearer ")) {
            return authorization.substring(7);
        }
        return null;
    }
}
