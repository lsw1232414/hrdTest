package com.lsw.crud;

import com.lsw.conn.DBConnection;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class CrudClass {

    public CrudClass() {

        // 1. 장르가 K-POP 아티스트 조회
        selectKpopArtists();

        // 2. 판매량 100만 이상 앨범 조회
        selectHighSalesAlbums(1000000);

        // 3. 아티스트별 총 판매량
        selectArtistTotalSales();

        // 4. 앨범 삭제: AlbumNo = 1
        deleteAlbum(1);
    }

    // 1. 장르가 K-POP 아티스트 조회
    private void selectKpopArtists() {
        String sql = "SELECT ArtistName, Agency FROM Artist WHERE Genre = 'K-POP'";
        try (Connection conn = DBConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            System.out.println("K-POP 아티스트 목록:");
            while (rs.next()) {
                System.out.println(rs.getString("ArtistName") + " | " +
                                   rs.getString("Agency"));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // 2. 판매량 100만 이상 앨범 조회
    private void selectHighSalesAlbums(int minSales) {
        String sql = "SELECT AlbumTitle, Sales FROM Album WHERE Sales >= ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, minSales);
            ResultSet rs = pstmt.executeQuery();

            System.out.println(minSales + " 이상 판매 앨범:");
            while (rs.next()) {
                System.out.println(rs.getString("AlbumTitle") + " | " +
                                   rs.getInt("Sales"));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // 3. 아티스트별 총 판매량
    private void selectArtistTotalSales() {
        String sql = "SELECT ArtistName, SUM(Album.Sales) " +
                     "FROM Artist " +
                     "JOIN Album ON Artist.ArtistNo = Album.ArtistNo " +
                     "GROUP BY ArtistName";
        try (Connection conn = DBConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            System.out.println("아티스트별 총 판매량:");
            while (rs.next()) {
                System.out.println(rs.getString("ArtistName") + " | " +
                                   rs.getInt(2));  // SUM 결과는 컬럼 인덱스 2
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // 4. 앨범 삭제
    private void deleteAlbum(int albumNo) {
        String sql = "DELETE FROM Album WHERE AlbumNo = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, albumNo);
            int count = pstmt.executeUpdate();
            System.out.println("AlbumNo " + albumNo + " 앨범 삭제 완료: " + count + "건");

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
