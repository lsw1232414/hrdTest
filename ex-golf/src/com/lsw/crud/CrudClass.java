package com.lsw.crud;

import com.lsw.conn.DBConnection;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class CrudClass {

    public CrudClass() {

        // 조회
        selectAGradeMembers();
        selectExpensiveLessons();
        selectTotalUsagePerMember();
        selectGradeStatistics();

        // 수정
        updateMemberGrade("이순신", "A");

        // 삭제
        deleteMember(3);
    }

    // 1. A등급 회원 조회
    private void selectAGradeMembers() {
        String sql = "SELECT MName, Phone, JoinDate FROM GolfMember WHERE Grade = 'A'";
        try (Connection conn = DBConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            System.out.println("A등급 회원:");
            while (rs.next()) {
                System.out.println(rs.getString("MName") + " | " +
                                   rs.getString("Phone") + " | " +
                                   rs.getDate("JoinDate"));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // 2. 강습비 250000 이상 강습 조회
    private void selectExpensiveLessons() {
        String sql = "SELECT * FROM Lesson WHERE Fee >= 250000";
        try (Connection conn = DBConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            System.out.println("강습비 250000 이상 강습:");
            while (rs.next()) {
                System.out.println(rs.getInt("LNo") + " | " +
                                   rs.getInt("MNo") + " | " +
                                   rs.getString("Coach") + " | " +
                                   rs.getDate("StartDate") + " | " +
                                   rs.getInt("Fee"));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // 3. 회원별 총 이용요금 집계
    private void selectTotalUsagePerMember() {
        String sql = "SELECT MName, SUM(Usages.Cost) " +
                     "FROM GolfMember " +
                     "JOIN Usages ON GolfMember.MNo = Usages.MNo " +
                     "GROUP BY MName";
        try (Connection conn = DBConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            System.out.println("회원별 총 이용요금:");
            while (rs.next()) {
                System.out.println(rs.getString("MName") + " | " +
                                   rs.getInt(2));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // 4. 등급별 통계
    private void selectGradeStatistics() {
        String sql = "SELECT Grade, COUNT(DISTINCT GolfMember.MNo), AVG(Lesson.Fee), SUM(Usages.Cost) " +
                     "FROM GolfMember " +
                     "LEFT JOIN Lesson ON GolfMember.MNo = Lesson.MNo " +
                     "LEFT JOIN Usages ON GolfMember.MNo = Usages.MNo " +
                     "GROUP BY GolfMember.Grade";
        try (Connection conn = DBConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            System.out.println("등급별 통계:");
            while (rs.next()) {
                System.out.println(rs.getString(1) + " | " +
                                   rs.getInt(2) + " | " +
                                   rs.getDouble(3) + " | " +
                                   rs.getDouble(4));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // 5. 회원 등급 수정
    private void updateMemberGrade(String memberName, String newGrade) {
        String sql = "UPDATE GolfMember SET Grade = ? WHERE MName = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, newGrade);
            pstmt.setString(2, memberName);
            int count = pstmt.executeUpdate();
            System.out.println(memberName + " 등급 수정 완료: " + count + "건");

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // 6. 회원 삭제
    private void deleteMember(int memberNo) {
        try (Connection conn = DBConnection.getConnection()) {

            // Lesson 먼저 삭제
            try (PreparedStatement pstmtLesson = conn.prepareStatement("DELETE FROM Lesson WHERE MNo = ?")) {
                pstmtLesson.setInt(1, memberNo);
                pstmtLesson.executeUpdate();
            }

            // Usages 삭제
            try (PreparedStatement pstmtUsages = conn.prepareStatement("DELETE FROM Usages WHERE MNo = ?")) {
                pstmtUsages.setInt(1, memberNo);
                pstmtUsages.executeUpdate();
            }

            // GolfMember 삭제
            try (PreparedStatement pstmtMember = conn.prepareStatement("DELETE FROM GolfMember WHERE MNo = ?")) {
                pstmtMember.setInt(1, memberNo);
                int count = pstmtMember.executeUpdate();
                System.out.println("회원 MNo=" + memberNo + " 삭제 완료: " + count + "건");
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
