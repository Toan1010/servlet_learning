import utils.DatabaseUtil;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class Main {
    public static void main(String[] args) throws SQLException {
        Connection cnn = DatabaseUtil.getConnection();

        String query = "Select * from user";

        Statement stm = cnn.createStatement();

        ResultSet resultSet = stm.executeQuery(query);


    }
}
