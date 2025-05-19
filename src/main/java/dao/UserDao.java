package dao;

import enumurate.UserRole;
import models.User;
import utils.DatabaseUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class UserDao {
    public static List<User> getAllUser() {
        String sql = "SELECT * FROM table_user";
        List<User> users = new ArrayList<>();

        try (Connection conn = DatabaseUtil.getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    User user = User.builder()
                            .id(Integer.parseInt(rs.getString("id")))
                            .username((rs.getString("username")))
                            .role(UserRole.valueOf(rs.getString("role").toUpperCase()))
                            .build();
                    users.add(user);
                }
                return users;
            }
        } catch (SQLException e) {
            e.printStackTrace(); // hoặc log lỗi
            throw new RuntimeException(e);
        }
    }

    public static boolean adminUpdate(int id, String role) {
        String sql = "UPDATE table_user set role = ?::user_role where id = ?";
        try (Connection conn = DatabaseUtil.getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, role);
            stmt.setInt(2, id);

            int success = stmt.executeUpdate();

            return success > 0;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public static boolean userUpdate(int id, String username, String hash_password) {
        String sql = "UPDATE table_user set username = ?," +
                "hash_password = ?" +
                " where id = ?";
        try (Connection conn = DatabaseUtil.getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, username);
            stmt.setString(2, hash_password);
            stmt.setInt(3, id);

            int success = stmt.executeUpdate();

            return success > 0;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public static User findUserByNameAndPassword(String username) {
        String sql = "SELECT * FROM table_user WHERE username = ? ";

        try (Connection conn = DatabaseUtil.getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, username);

            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                User user = new User();
                user.setId(rs.getInt("id"));
                user.setUsername(rs.getString("username"));
                user.setHash_password(rs.getString("hash_password"));
                UserRole role = UserRole.valueOf((rs.getString("role").toUpperCase()));
                user.setRole(role);
                return user;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;

    }

    public static boolean addNewUser(String username, String hash_password, String role) {
        String sql = "INSERT INTO table_user (username, hash_password, role) VALUES (?, ?, ?::user_role)";

        try (Connection conn = DatabaseUtil.getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, username);
            stmt.setString(2, hash_password);
            stmt.setString(3, role);

            int result = stmt.executeUpdate();

            return result > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }

    }

    public static boolean deleteUser(int user_id) {
        String sql = "DELETE FROM table_user WHERE id = ? ";
        try (Connection conn = DatabaseUtil.getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, (user_id));
            int result = stmt.executeUpdate();
            return result > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}
