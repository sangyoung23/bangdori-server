package bangdori.api.comm.jwt;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;


// JWT 기반 인증에서 클라이언트가 보낸 요청의 헤더에서 토큰을 추출하고 유효성을 검증한 뒤, 인증 정보를 설정하는 필터를 작성
@AllArgsConstructor
public class JwtFilter extends OncePerRequestFilter {
    private final TokenProvider tokenProvider;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String token = resolveToken(request); // 요청에서 토큰 추출

        if (request.getRequestURI().equals("/api/users/auth/login")|| request.getRequestURI().equals("/api/ContentItem")) {
            filterChain.doFilter(request, response); // 필터를 건너뛰고 다음 필터로 이동
            return;
        }


        if (token != null && tokenProvider.validateToken(token)) { // 토큰 유효성 검증
            SecurityContextHolder.getContext().setAuthentication(tokenProvider.getAuthentication(token));
        }
        filterChain.doFilter(request, response); // 다음 필터로 이동
    }

    private String resolveToken(HttpServletRequest request) {
        String bearerToken = request.getHeader("Authorization"); // 헤더에서 Authorization 추출
        if (bearerToken != null && bearerToken.startsWith("Bearer ")) {
            return bearerToken.substring(7); // "Bearer " 제거 후 토큰 반환
        }
        return null;
    }
}