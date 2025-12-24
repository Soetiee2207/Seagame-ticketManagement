package edu.thanglong.infrastructure.config;

import edu.thanglong.domain.entity.User;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.springframework.web.servlet.HandlerInterceptor;

public class AuthInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        HttpSession session = request.getSession(false);
        
        if (session == null || session.getAttribute("currentUser") == null) {
            response.sendRedirect("/login");
            return false;
        }

        String requestURI = request.getRequestURI();
        if (requestURI.startsWith("/admin")) {
            User user = (User) session.getAttribute("currentUser");
            if (!"ADMIN".equals(user.getRole())) {
                response.sendRedirect("/");
                return false;
            }
        }

        return true;
    }
}
