package com.ex;

import com.conn.DBConnection;
import java.sql.*;

public class Ex2 {

    public Ex2() {
        String sql = "SELECT B.Title FROM Rental R " +
                     "JOIN Member M ON R.MemberID = M.MemberID " +
                     "JOIN Book B ON R.BookID = B.BookID " +
                     "WHERE M.Name = '홍길동'";

        try (Connection conn = DBConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            System.out.println("=== 홍길동 회원 대출 도서 목록 ===");
            while (rs.next()) {
                System.out.println(rs.getString("Title"));
            }
            System.out.println("------------------------------");

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}