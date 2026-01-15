package lk.icbt.oceanview.oceanviewroomreservationsystem.servlet;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import lk.icbt.oceanview.oceanviewroomreservationsystem.factory.ServiceFactory;
import lk.icbt.oceanview.oceanviewroomreservationsystem.model.User;
import lk.icbt.oceanview.oceanviewroomreservationsystem.service.AuthenticationService;

import java.io.IOException;

/**
 * LoginServlet - Handles user authentication
 * <p>
 * Academic Purpose:
 * - Demonstrates MVC PATTERN (Controller component)
 * - Demonstrates METHOD OVERRIDING (doGet and doPost)
 * - Implements authentication logic
 * - Session management
 *
 * @author CIS6003 Student
 * @version 1.0
 * @since 2026-01-12
 */
@WebServlet("/login")
public class LoginServlet extends HttpServlet {

    private AuthenticationService authService;


    @Override
    public void init() throws ServletException {
        authService = ServiceFactory.getAuthenticationService();
    }


    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession(false);
        if (session != null && session.getAttribute("user") != null) {
            response.sendRedirect(request.getContextPath() + "/dashboard");
            return;
        }

        request.getRequestDispatcher("/WEB-INF/views/login.jsp").forward(request, response);
    }


    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String username = request.getParameter("username");
        String password = request.getParameter("password");

        if (username == null || username.trim().isEmpty() ||
                password == null || password.trim().isEmpty()) {
            request.setAttribute("error", "Username and password are required");
            request.getRequestDispatcher("/WEB-INF/views/login.jsp").forward(request, response);
            return;
        }

        User user = authService.authenticate(username.trim(), password);

        if (user != null) {
            HttpSession session = request.getSession(true);
            session.setAttribute("user", user);
            session.setAttribute("userId", user.getUserId());
            session.setAttribute("username", user.getUsername());
            session.setAttribute("role", user.getRole());
            session.setMaxInactiveInterval(30 * 60);

            response.sendRedirect(request.getContextPath() + "/dashboard");
        } else {
            request.setAttribute("error", "Invalid username or password");
            request.setAttribute("username", username);
            request.getRequestDispatcher("/WEB-INF/views/login.jsp").forward(request, response);
        }
    }
}
