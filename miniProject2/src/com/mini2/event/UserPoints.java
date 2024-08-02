package com.mini2.event;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UserPoints {
	public void UserPoints(int userId) {
		Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        
        try {
        	// JDBC Driver 로드
            Class.forName("com.mysql.cj.jdbc.Driver");
            // 데이터베이스 연결
            conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/project2", "root", "0000");

            // 포인트 총합 조회 쿼리 (userid가 동일할 때, point의 합을 구하기)
            String sql = "SELECT SUM(points_awarded) AS total_points FROM UserEvents WHERE user_id = ?";
            pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, userId);
            rs = pstmt.executeQuery();

            if (rs.next()) {
                int totalPoints = rs.getInt("total_points");
                System.out.println("\t💰 보유하고 있는 총 포인트는 " + totalPoints + " 입니다.");
            } else {
                System.out.println("\t💰 보유하고 있는 포인트가 존재하지 않습니다.");
            }
        } catch (SQLException e) {
            System.out.println("SQL Error: " + e.getMessage());
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            System.out.println("JDBC Driver Error: " + e.getMessage());
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
