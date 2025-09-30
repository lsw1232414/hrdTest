package com.ex;

import com.conn.DBConnection;
import java.sql.*;

public class Ex5 {

    public Ex5() {
        String sql = "SELECT * FROM Book WHERE Price = (SELECT MAX(Price) FROM Book)";

        try (Connection conn = DBConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            System.out.println("=== 가격이 가장 비싼 도서 ===");
            while (rs.next()) {
                System.out.println(rs.getInt("BookID") + " | " +
                                   rs.getString("Title") + " | " +
                                   rs.getDouble("Price"));
            }
            System.out.println("------------------------------");

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}