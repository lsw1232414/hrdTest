package com.lsw.crud;

import com.lsw.conn.DBConnection;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class CrudClass {

    public CrudClass() {

        // 1. 사원 등록
        insertEmployees();

        // 2. 조회
        selectByDept("개발부");      // 부서가 개발부인 사원
        selectBySalary(3000000);     // 급여 300만원 이상인 사원

        // 3. 수정: 이순신 급여 3,500,000
        updateSalary("이순신", 3500000);

        // 4. 삭제: 사번 1번 사원 삭제
        deleteEmployee(1);
    }

    // 사원 등록
    private void insertEmployees() {
        String sql = "INSERT INTO Employee (EmpName, Dept, HireDate, Salary) VALUES (?, ?, ?, ?)";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            String[][] data = {
                {"홍길동", "영업부", "2020-03-01", "2500000"},
                {"이순신", "인사부", "2019-07-15", "3200000"},
                {"강감찬", "개발부", "2021-01-10", "2800000"}
            };

            for (String[] emp : data) {
                pstmt.setString(1, emp[0]);
                pstmt.setString(2, emp[1]);
                pstmt.setString(3, emp[2]);
                pstmt.setInt(4, Integer.parseInt(emp[3]));
                pstmt.executeUpdate();
            }

            System.out.println("사원 등록 완료");

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // 부서 기준 조회
    private void selectByDept(String dept) {
        String sql = "SELECT EmpNO, EmpName, Salary FROM Employee WHERE Dept = '" + dept + "'";
        try (Connection conn = DBConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            System.out.println("부서가 " + dept + "인 사원:");
            while (rs.next()) {
                System.out.println(rs.getInt("EmpNO") + " | " +
                                   rs.getString("EmpName") + " | " +
                                   rs.getInt("Salary"));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // 급여 기준 조회
    private void selectBySalary(int minSalary) {
        String sql = "SELECT EmpName, Dept FROM Employee WHERE Salary >= " + minSalary;
        try (Connection conn = DBConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            System.out.println("급여 " + minSalary + " 이상인 사원:");
            while (rs.next()) {
                System.out.println(rs.getString("EmpName") + " | " +
                                   rs.getString("Dept"));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // 급여 수정
    private void updateSalary(String name, int newSalary) {
        String sql = "UPDATE Employee SET Salary = ? WHERE EmpName = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, newSalary);
            pstmt.setString(2, name);
            int count = pstmt.executeUpdate();
            System.out.println(name + " 급여 수정 완료: " + count + "건");

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // 사원 삭제
    private void deleteEmployee(int empNo) {
        String sql = "DELETE FROM Employee WHERE EmpNO = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, empNo);
            int count = pstmt.executeUpdate();
            System.out.println("사번 " + empNo + " 삭제 완료: " + count + "건");

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}