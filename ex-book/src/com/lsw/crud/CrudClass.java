package com.lsw.crud;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.conn.DBConnection;

public class CrudClass {

    public CrudClass() {
        // 예시 실행: 주석처리/선택 가능
//        insertBook("B001", "자바 입문", "홍길동", "도서출판", 25000, 2022);
//        updateBookPrice("B001", 28000);
//        deleteBook("B001");
        selectAllBooks();
    }

    // CREATE - 도서 등록
    public void insertBook(String bookID, String title, String author, String publisher, int price, int pubYear) {
        String sql = "INSERT INTO Book(BookID, Title, Author, Publisher, Price, PubYear) VALUES (?, ?, ?, ?, ?, ?)";
        Connection conn = null;
        PreparedStatement pstmt = null;

        try {
            conn = DBConnection.getConnection();
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, bookID);
            pstmt.setString(2, title);
            pstmt.setString(3, author);
            pstmt.setString(4, publisher);
            pstmt.setInt(5, price);
            pstmt.setInt(6, pubYear);
            int rows = pstmt.executeUpdate();
            System.out.println(rows + "행이 추가되었습니다.");
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DBConnection.close(pstmt, conn);
        }
    }

    // READ - 전체 도서 조회
    public void selectAllBooks() {
        String sql = "SELECT * FROM Book";
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        try {
            conn = DBConnection.getConnection();
            pstmt = conn.prepareStatement(sql);
            rs = pstmt.executeQuery();

            System.out.println("----- 도서 목록 -----");
            while (rs.next()) {
                System.out.println(
                        rs.getString("BookID") + " | " +
                        rs.getString("Title") + " | " +
                        rs.getString("Author") + " | " +
                        rs.getString("Publisher") + " | " +
                        rs.getInt("Price") + " | " +
                        rs.getInt("PubYear")
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DBConnection.close(rs, pstmt, conn);
        }
    }

    // UPDATE - 가격 수정
    public void updateBookPrice(String bookID, int newPrice) {
        String sql = "UPDATE Book SET Price = ? WHERE BookID = ?";
        Connection conn = null;
        PreparedStatement pstmt = null;

        try {
            conn = DBConnection.getConnection();
            pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, newPrice);
            pstmt.setString(2, bookID);
            int rows = pstmt.executeUpdate();
            System.out.println(bookID + " 가격 수정 완료: " + rows + "건");
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DBConnection.close(pstmt, conn);
        }
    }

    // DELETE - 도서 삭제
    public void deleteBook(String bookID) {
        String sql = "DELETE FROM Book WHERE BookID = ?";
        Connection conn = null;
        PreparedStatement pstmt = null;

        try {
            conn = DBConnection.getConnection();
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, bookID);
            int rows = pstmt.executeUpdate();
            System.out.println(bookID + " 삭제 완료: " + rows + "건");
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DBConnection.close(pstmt, conn);
        }
    }
}
