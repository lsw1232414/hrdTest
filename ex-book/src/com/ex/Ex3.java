package com.ex;

import com.conn.DBConnection;
import java.sql.*;

public class Ex3 {

    public Ex3() {
        String sql = "SELECT B.Title, M.Name FROM Rental R " +
                     "JOIN Book B ON R.BookID = B.BookID " +
                     "JOIN Member M ON R.MemberID = M.MemberID " +
                     "WHERE R.ReturnDate IS NULL";

        try (Connection conn = DBConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            System.out.println("=== 반납하지 않은 도서 ===");
            while (rs.next()) {
                System.out.println(rs.getString("Title") + " | " + rs.getString("Name"));
            }
            System.out.println("------------------------------");

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}