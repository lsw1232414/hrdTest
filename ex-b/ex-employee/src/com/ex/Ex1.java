package com.ex;

import com.lsw.conn.DBConnection;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class Ex1 {

    public Ex1() {
        String sql = "INSERT INTO Employee (EmpName, Dept, HireDate, Salary) VALUES (?, ?, ?, ?)";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            if (conn == null) return;

            // 첫 번째 사원
            pstmt.setString(1, "홍길동");
            pstmt.setString(2, "영업부");
            pstmt.setString(3, "2020-03-01");
            pstmt.setInt(4, 2500000);
            pstmt.executeUpdate();

            // 두 번째 사원
            pstmt.setString(1, "이순신");
            pstmt.setString(2, "인사부");
            pstmt.setString(3, "2019-07-15");
            pstmt.setInt(4, 3200000);
            pstmt.executeUpdate();

            // 세 번째 사원
            pstmt.setString(1, "강감찬");
            pstmt.setString(2, "개발부");
            pstmt.setString(3, "2021-01-10");
            pstmt.setInt(4, 2800000);
            pstmt.executeUpdate();

            System.out.println("=== 사원 등록 완료 ===");

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}