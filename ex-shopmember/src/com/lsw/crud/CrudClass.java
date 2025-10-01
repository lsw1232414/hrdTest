package com.lsw.crud;
import com.lsw.conn.DBConnection;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class CrudClass {

    public CrudClass() {
        // 1. 조회
        selectByGrade("A");         // A등급 회원 조회
        selectByJoinDate("2020-01-01");  // 2020년 이후 가입 회원 조회

        // 2. 수정: 이순신 등급 A
        updateGrade("이순신", "A");

        // 3. 삭제: CustNo 3 회원 삭제
        deleteMember(3);
    }

    // 등급 기준 조회
    private void selectByGrade(String grade) {
        String sql = "SELECT CustName, Phone, JoinDate FROM ShopMember WHERE Grade = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, grade);
            ResultSet rs = pstmt.executeQuery();

            System.out.println(grade + " 등급 회원:");
            while (rs.next()) {
                System.out.println(rs.getString("CustName") + " | " +
                                   rs.getString("Phone") + " | " +
                                   rs.getDate("JoinDate"));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // 가입일자 기준 조회
    private void selectByJoinDate(String fromDate) {
        String sql = "SELECT CustName, Phone, JoinDate FROM ShopMember WHERE JoinDate >= ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, fromDate);
            ResultSet rs = pstmt.executeQuery();

            System.out.println(fromDate + " 이후 가입 회원:");
            while (rs.next()) {
                System.out.println(rs.getString("CustName") + " | " +
                                   rs.getString("Phone") + " | " +
                                   rs.getDate("JoinDate"));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // 등급 수정
    private void updateGrade(String name, String newGrade) {
        String sql = "UPDATE ShopMember SET Grade = ? WHERE CustName = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, newGrade);
            pstmt.setString(2, name);
            int count = pstmt.executeUpdate();
            System.out.println(name + " 등급 수정 완료: " + count + "건");

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // 회원 삭제
    private void deleteMember(int custNo) {
        String deleteSales = "DELETE FROM Sale WHERE CustNo = ?";
        String deleteMember = "DELETE FROM ShopMember WHERE CustNo = ?";

        try (Connection conn = DBConnection.getConnection()) {

            // 1. Sale 테이블에서 먼저 삭제
            try (PreparedStatement pstmt = conn.prepareStatement(deleteSales)) {
                pstmt.setInt(1, custNo);
                int count = pstmt.executeUpdate();
                System.out.println("Sale 테이블에서 삭제 완료: " + count + "건");
            }

            // 2. ShopMember 테이블에서 삭제
            try (PreparedStatement pstmt = conn.prepareStatement(deleteMember)) {
                pstmt.setInt(1, custNo);
                int count = pstmt.executeUpdate();
                System.out.println("ShopMember에서 삭제 완료: " + count + "건");
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}