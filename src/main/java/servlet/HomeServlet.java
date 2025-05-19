package servlet;

import dao.UserDao;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import models.User;

import java.io.IOException;
import java.util.List;

@WebServlet("/home")
public class HomeServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        HttpSession session = req.getSession();
        String role = (String) session.getAttribute("role");

        if ("ADMIN".equals(role)) {
            List<User> users = UserDao.getAllUser();
            req.setAttribute("user", users);
        }

        System.out.println("Home Servlet");

        req.getRequestDispatcher("/home.jsp").forward(req, resp);
    }
}
