package com.ex;

import com.conn.DBConnection;
import java.sql.*;

public class Ex4 {

    public Ex4() {
        String sql = "SELECT B.Title, COUNT(R.RentalID) AS RentalCount " +
                     "FROM Book B " +
                     "LEFT JOIN Rental R ON B.BookID = R.BookID " +
                     "GROUP BY B.BookID, B.Title";

        try (Connection conn = DBConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            System.out.println("=== 도서별 대출 횟수 ===");
            while (rs.next()) {
                System.out.println(rs.getString("Title") + " | 대출횟수: " + rs.getInt("RentalCount"));
            }
            System.out.println("------------------------------");

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
