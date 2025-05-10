package servlet;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import dao.UserDao;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import models.User;

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

//        BufferedReader reader = req.getReader();
//        StringBuilder sb = new StringBuilder();
//        String line;
//
//        while ((line = reader.readLine()) != null) {
//            sb.append(line);
//        }
//
//        // Lấy JSON body từ request
//        String requestBody = sb.toString();
//
// Parse JSON body
//        JsonObject jsonObject = JsonParser.parseString(requestBody).getAsJsonObject();
//
//        // Truy xuất các trường từ JSON mà không cần gắn vào một class
//        String username1 = jsonObject.get("username").getAsString();
//        String password1 = jsonObject.get("password").getAsString();
//
//        // Sử dụng các trường
//        System.out.println("Username: " + username1);
//        System.out.println("Password: " + password1);
//

        User user = UserDao.findUserByNameAndPassword(username, password);

        if (user != null) {
            HttpSession session = req.getSession();
            session.setAttribute("user", username); // lưu username

            // Redirect tới /jsp-app/home
            resp.sendRedirect(req.getContextPath() + "/home");
        } else {
            // Redirect lại trang login kèm theo thông báo (tùy chọn)
            resp.sendRedirect(req.getContextPath() + "/login?error=true");
        }
    }
}
