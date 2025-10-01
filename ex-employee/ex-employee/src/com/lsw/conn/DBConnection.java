package com.lsw.conn;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnection {

    private static final String URL = "jdbc:mariadb://localhost:3306/hrdtest"; // DB 이름 확인
    private static final String USER = "root";
    private static final String PASSWORD = "1234";

    // MariaDB 연결
    public static Connection getConnection() {
        try {
            Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
            System.out.println("*** MariaDB 연결 성공 ***");
            return conn;

        } catch (SQLException e) {
            System.err.println("=== DB 연결 실패 ===");
            e.printStackTrace();
            return null;
        }
    }
}
