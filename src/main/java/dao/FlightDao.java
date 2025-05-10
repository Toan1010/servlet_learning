package dao;

import models.Flight;
import utils.DatabaseUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class FlightDao {

    public static List<Flight> getListFlight(int page, int size) {
        List<Flight> flights = new ArrayList<>();
        String sql = "SELECT * FROM chuyenbay LIMIT ? OFFSET ?";

        try (Connection conn = DatabaseUtil.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            int offset = (page - 1) * size;

            stmt.setInt(1, size);
            stmt.setInt(2, offset);

            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    Flight flight = Flight.builder()
                            .macb(rs.getString("macb"))
                            .gadi(rs.getString("gadi"))
                            .gaden(rs.getString("gaden"))
                            .dodai(rs.getInt("dodai"))
                            .giodi(rs.getTime("giodi"))
                            .gioden(rs.getTime("gioden"))
                            .chiphi(rs.getInt("chiphi"))
                            .build();

                    flights.add(flight);
                }
            }

        } catch (SQLException e) {
            e.printStackTrace(); // hoặc log lỗi
            throw new RuntimeException(e);
        }

        return flights;
    }

    public static int countTotalFlights() {

        String sql = "select count(*) from chuyenbay";

        try (Connection conn = DatabaseUtil.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    return rs.getInt(1);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace(); // hoặc log lỗi
            throw new RuntimeException(e);
        }
        return 0;
    }


    public static boolean addNewFlight(Flight flight) {
        return true;
    }

    public static boolean updateFlight(Flight flight) {


        return true;
    }

    public static boolean deleteFlight() {
        return true;
    }

}
