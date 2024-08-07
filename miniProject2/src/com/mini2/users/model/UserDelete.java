package com.mini2.users.model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class UserDelete {
	public void delete(int userId) {
		Connection conn = null;
		try {
			// JDBC Driver 로드
			Class.forName("com.mysql.cj.jdbc.Driver");
			// 데이터베이스 연결
			conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/project2", "root", "0000");

			// SQL 문 작성
			String sql = "DELETE FROM Users WHERE user_id=?";

			// PreparedStatement 객체 생성
			PreparedStatement pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, userId); // userId를 정수형으로 설정

			// SQL 실행
			int rows = pstmt.executeUpdate();

			// PreparedStatement 종료
			pstmt.close();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			// 데이터베이스 연결 종료
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
