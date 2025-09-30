package com.ex;

import com.conn.DBConnection;
import java.sql.*;

public class Ex1 {

    public Ex1() {
        String sql = "SELECT * FROM Book WHERE PubYear >= 2020";

        try (Connection conn = DBConnection.getConnection()) {

            if (conn == null) return; // 연결 실패 시 종료

            try (Statement stmt = conn.createStatement();
                 ResultSet rs = stmt.executeQuery(sql)) {

                System.out.println("=== 2020년 이상 출판 도서 ===");
                while (rs.next()) {
                    System.out.println(rs.getInt("BookID") + " | " +
                                       rs.getString("Title") + " | " +
                                       rs.getInt("PubYear"));
                }
                System.out.println("------------------------------");

            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}