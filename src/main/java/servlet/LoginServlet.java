package servlet;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import dao.UserDao;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import models.User;
import org.mindrot.jbcrypt.BCrypt;

import java.io.BufferedReader;
import java.io.IOException;


@WebServlet("/login")
public class LoginServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        req.getRequestDispatcher("/login.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        String username = req.getParameter("username");
        String password = req.getParameter("password");

        User user = UserDao.findUserByNameAndPassword(username);

        if (user != null) {
            boolean isValid = BCrypt.checkpw(password, user.getHash_password());
            if (isValid) {
                HttpSession session = req.getSession();
                session.setAttribute("id", String.valueOf(user.getId())); // lưu id
                session.setAttribute("username", username); // lưu username
                session.setAttribute("role", user.getRole().name());
                // Redirect tới /jsp-app/home
                resp.sendRedirect(req.getContextPath() + "/home");
                return ;
            }
        }
        // Redirect lại trang login kèm theo thông báo (tùy chọn)
        resp.sendRedirect(req.getContextPath() + "/login?error=true");
    }
}
