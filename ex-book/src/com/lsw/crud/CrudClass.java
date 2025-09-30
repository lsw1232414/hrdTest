package com.lsw.crud;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.conn.DBConnection;

public class CrudClass {

    public CrudClass() {
    	books2020();
        booksByMember("홍길동");
        notReturned();
        rentalCount();
        mostExpensive();
    }

    // 2020년 이상 출판된 도서
    private void books2020() {
        System.out.println("=== 2020년 이상 출판된 도서 ===");
        String sql = "SELECT * FROM Book WHERE PubYear >= 2020";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql);
             ResultSet rs = pstmt.executeQuery()) {
            while (rs.next()) {
                System.out.println(rs.getString("BookID") + " | " +
                        rs.getString("Title") + " | " +
                        rs.getString("Author") + " | " +
                        rs.getString("Publisher") + " | " +
                        rs.getInt("Price") + " | " +
                        rs.getInt("PubYear"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // 특정 회원이 대출한 도서
    private void booksByMember(String memberName) {
        System.out.println("\n=== '" + memberName + "' 회원이 대출한 도서 ===");
        String sql = "SELECT B.BookID, B.Title, B.Author, B.Publisher, B.Price, B.PubYear " +
                     "FROM Rental R " +
                     "JOIN Member M ON R.MemberID = M.MemberID " +
                     "JOIN Book B ON R.BookID = B.BookID " +
                     "WHERE M.Name = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, memberName);
            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    System.out.println(rs.getString("BookID") + " | " +
                            rs.getString("Title") + " | " +
                            rs.getString("Author") + " | " +
                            rs.getString("Publisher") + " | " +
                            rs.getInt("Price") + " | " +
                            rs.getInt("PubYear"));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // 반납하지 않은 도서
    private void notReturned() {
        System.out.println("\n=== 반납하지 않은 도서 ===");
        String sql = "SELECT R.RentalID, M.Name, B.Title, R.RentDate " +
                     "FROM Rental R " +
                     "JOIN Member M ON R.MemberID = M.MemberID " +
                     "JOIN Book B ON R.BookID = B.BookID " +
                     "WHERE R.ReturnDate IS NULL";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql);
             ResultSet rs = pstmt.executeQuery()) {
            while (rs.next()) {
                System.out.println(rs.getString("RentalID") + " | " +
                        rs.getString("Name") + " | " +
                        rs.getString("Title") + " | " +
                        rs.getDate("RentDate"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // 도서별 대출 횟수
    private void rentalCount() {
        System.out.println("\n=== 도서별 대출 횟수 ===");
        String sql = "SELECT B.Title, COUNT(R.RentalID) AS RentCount " +
                     "FROM Book B " +
                     "LEFT JOIN Rental R ON B.BookID = R.BookID " +
                     "GROUP BY B.BookID, B.Title";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql);
             ResultSet rs = pstmt.executeQuery()) {
            while (rs.next()) {
                System.out.println(rs.getString("Title") + " | " + rs.getInt("RentCount"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // 가격이 가장 비싼 도서
    private void mostExpensive() {
        System.out.println("\n=== 가격이 가장 비싼 도서 ===");
        String sql = "SELECT * FROM Book WHERE Price = (SELECT MAX(Price) FROM Book)";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql);
             ResultSet rs = pstmt.executeQuery()) {
            while (rs.next()) {
                System.out.println(rs.getString("BookID") + " | " +
                        rs.getString("Title") + " | " +
                        rs.getInt("Price"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
