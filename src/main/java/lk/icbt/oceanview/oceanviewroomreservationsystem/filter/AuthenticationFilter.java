package lk.icbt.oceanview.oceanviewroomreservationsystem.filter;

import jakarta.servlet.*;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;


@WebFilter(filterName = "AuthenticationFilter", urlPatterns = {
        "/dashboard",
        "/reservation",
        "/bill",
        "/help"
})
public class AuthenticationFilter implements Filter {


    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse,
                         FilterChain filterChain) throws IOException, ServletException {

        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;

        HttpSession session = request.getSession(false);

        String requestURI = request.getRequestURI();
        String contextPath = request.getContextPath();

        boolean isLoggedIn = (session != null && session.getAttribute("user") != null);

        String path = requestURI.substring(contextPath.length());

        if (isPublicResource(path)) {
            filterChain.doFilter(request, response);
            return;
        }

        if (isLoggedIn) {
            filterChain.doFilter(request, response);
        } else {
            String loginURL = contextPath + "/login";

            String originalURL = requestURI;
            if (request.getQueryString() != null) {
                originalURL += "?" + request.getQueryString();
            }
            session = request.getSession(true);
            session.setAttribute("redirectAfterLogin", originalURL);

            response.sendRedirect(loginURL);
        }
    }


    private boolean isPublicResource(String path) {
        return path.equals("/login") ||
                path.equals("/logout") ||
                path.startsWith("/css/") ||
                path.startsWith("/js/") ||
                path.startsWith("/images/") ||
                path.startsWith("/static/") ||
                path.equals("/") ||
                path.equals("/index.jsp");
    }


    @Override
    public void destroy() {
    }
}
