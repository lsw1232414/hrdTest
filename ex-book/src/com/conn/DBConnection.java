package com.conn;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnection {

    private static final String URL = "jdbc:mariadb://localhost:3306/hrdtest";
    private static final String USER = "root";
    private static final String PASSWORD = "1234";

    // MariaDB 연결
    public static Connection getConnection() {
        try {
            // 드라이버 로드
            Class.forName("org.mariadb.jdbc.Driver");

            Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
            System.out.println("*** MariaDB 연결 성공 ***");
            return conn;

        } catch (ClassNotFoundException e) {
            System.err.println("=== MariaDB JDBC 드라이버를 찾을 수 없음 ===");
            e.printStackTrace();
            return null;

        } catch (SQLException e) {
            System.err.println("=== DB 연결 실패 ===");
            e.printStackTrace();
            return null;
        }
    }
} // <- 클래스 닫는 중괄호
