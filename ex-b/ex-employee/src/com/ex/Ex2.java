package com.ex;

import com.lsw.conn.DBConnection;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class Ex2 {

    public Ex2() {
        try (Connection conn = DBConnection.getConnection();
             Statement stmt = conn.createStatement()) {

            if (conn == null) return;

            // 1️⃣ 개발부 사원 조회
            String sqlDept = "SELECT EmpNO, EmpName, Salary FROM Employee WHERE Dept = '개발부'";
            try (ResultSet rs = stmt.executeQuery(sqlDept)) {
                System.out.println("=== 부서가 개발부인 사원 ===");
                while (rs.next()) {
                    System.out.println(rs.getInt("EmpNO") + " | " +
                                       rs.getString("EmpName") + " | " +
                                       rs.getInt("Salary"));
                }
            }

            // 2️⃣ 급여 3,000,000 이상인 사원 조회
            String sqlSalary = "SELECT EmpName, Dept FROM Employee WHERE Salary >= 3000000";
            try (ResultSet rs = stmt.executeQuery(sqlSalary)) {
                System.out.println("=== 급여 3,000,000 이상인 사원 ===");
                while (rs.next()) {
                    System.out.println(rs.getString("EmpName") + " | " +
                                       rs.getString("Dept"));
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}