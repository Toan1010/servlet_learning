package filter;

import jakarta.servlet.*;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;

@WebFilter("/*")
public class AuthFilter implements Filter {

    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {

        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse resp = (HttpServletResponse) response;

        String path = req.getRequestURI();
        HttpSession session = req.getSession(false);
        boolean loggedIn = (session != null && session.getAttribute("user") != null);

        boolean isLoginPage = path.endsWith("/login") || path.endsWith("/login.jsp");

        if (!loggedIn && !isLoginPage) {
            // Chưa login, về lại login
            resp.sendRedirect(req.getContextPath() + "/login");
        } else if (loggedIn && isLoginPage) {
            // Đã login mà vào login => chuyển sang home
            resp.sendRedirect(req.getContextPath() + "/home");
        } else {
            chain.doFilter(request, response); // tiếp tục
        }
    }
}
