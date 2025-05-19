package servlet;

import dao.UserDao;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.mindrot.jbcrypt.BCrypt;

import java.io.IOException;
import java.io.PrintWriter;

@WebServlet("/user")
public class UserServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        String _method = req.getParameter("_method").toLowerCase();

        if (_method != null) {
            if ("PUT".equalsIgnoreCase(_method)) {
                this.doPut(req, resp);
                return;
            } else if ("DELETE".equalsIgnoreCase(_method)) {
                this.doDelete(req, resp);
                return;
            } else {
                System.out.println("Contine POST");
            }
        }

        String username = req.getParameter("username");
        String password = req.getParameter("password");
        String role = req.getParameter("role");

        String salt = BCrypt.gensalt(10);
        String hash_password = BCrypt.hashpw(password, salt);
        boolean isSuccess = UserDao.addNewUser(username, hash_password, role.toLowerCase());

        if (!isSuccess) {
            resp.sendRedirect(req.getContextPath() + "/home?error=Them_moi_khong_thanh_cong");
            return;
        }

        resp.sendRedirect(req.getContextPath() + "/home?success=Them_moi_thanh_cong");
    }

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        String _action = req.getParameter("_action");

        if (_action == null) {
            return;
        }
        int id = Integer.valueOf(req.getParameter("id"));
        boolean isSuccess = false;

        if ("user".equalsIgnoreCase(_action)) {
            String username = req.getParameter("username");
            String password = req.getParameter("password");

            String salt = BCrypt.gensalt(10);
            String hash_password = BCrypt.hashpw(password, salt);

            System.out.println(hash_password);

            isSuccess = UserDao.userUpdate(id, username, hash_password);

        } else {
            String role = req.getParameter("role").toLowerCase();

            isSuccess = UserDao.adminUpdate(id, role);
        }

        if (!isSuccess) {
            resp.sendRedirect(req.getContextPath() + "/home?error=Update_khong_thanh_cong");
            return;
        }

        resp.sendRedirect(req.getContextPath() + "/home?success=Update_thanh_cong");

    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        int id = Integer.parseInt(req.getParameter("id"));

        boolean success = UserDao.deleteUser(id);
        if (success) {
            resp.sendRedirect(req.getContextPath() + "/home?success=Xoa_thanh_cong");
            return;
        }
        resp.sendRedirect(req.getContextPath() + "/home?error=Xoa_khong_thanh_cong");

    }
}
