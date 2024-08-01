package com.mini2.users.model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;

//사용자 DB 추가
public class UserInsert {

    public UserInsert(String newUserName, String newEmail, String newPassword, String newPhoneNumber, String newAddress) {
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        try {
            // JDBC Driver 로드
            Class.forName("com.mysql.cj.jdbc.Driver");
            // 데이터베이스 연결
            conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/project2", "root", "0000");

            // 매개변수화된 SQL 문 작성
            String sql = "INSERT INTO Users (user_name, email, password, phone_number, address, registration_date) "
                       + "VALUES (?, ?, ?, ?, ?, ?)";

            // PreparedStatement 객체 생성 및 값 설정
            pstmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            pstmt.setString(1, newUserName);
            pstmt.setString(2, newEmail);
            pstmt.setString(3, newPassword);
            pstmt.setString(4, newPhoneNumber);
            pstmt.setString(5, newAddress);
            pstmt.setTimestamp(6, new Timestamp(System.currentTimeMillis()));

            // SQL 실행
            int rows = pstmt.executeUpdate();

            // user_id 값 얻기
            if (rows == 1) {
                rs = pstmt.getGeneratedKeys();
                if (rs.next()) {
                    int userId = rs.getInt(1);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            // 자원 해제
            if (rs != null) {
                try {
                    rs.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            if (pstmt != null) {
                try {
                    pstmt.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            if (conn != null) {
                try {
                    conn.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
