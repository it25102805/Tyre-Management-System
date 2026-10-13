package lk.asityre.tyrerebuild.webapp.security;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import java.io.IOException;
import java.util.Set;

@Component
public class AccessInterceptor implements HandlerInterceptor {

    @Override
    @SuppressWarnings("unchecked")
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
            throws IOException {

        String context = request.getContextPath();
        String path = request.getRequestURI().substring(context.length());

        if (isPublic(path)) {
            return true;
        }

        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("userId") == null) {
            response.sendRedirect(context + "/login");
            return false;
        }

        String page = AccessPolicy.pageForPath(path);
        if (page == null) {
            return true;   // e.g. /dashboard: any logged-in user
        }

        Set<String> pages = (Set<String>) session.getAttribute("pages");
        if (pages != null && pages.contains(page)) {
            return true;
        }

        response.sendRedirect(context + "/dashboard?denied=1");
        return false;
    }

    private boolean isPublic(String path) {
        return path.equals("/")
                || path.equals("/login")
                || path.equals("/logout")
                || path.equals("/register")
                || path.startsWith("/register/")
                || path.equals("/error");
    }
}
