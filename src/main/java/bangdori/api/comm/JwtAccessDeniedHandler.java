package bangdori.api.comm;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;

// 권한이 없는 사용자(ROLE_ADMIN을 가지고 싶다던가..)가 보호된 자원에 액세스하려 할 때 처리 방법
@Component
public class JwtAccessDeniedHandler implements AccessDeniedHandler {
    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException accessDeniedException) throws IOException, ServletException {

        System.out.println("2222");
        response.sendError(HttpServletResponse.SC_FORBIDDEN);
    }
}