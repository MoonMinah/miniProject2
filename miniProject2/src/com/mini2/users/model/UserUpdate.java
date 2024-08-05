package com.mini2.users.model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import org.mindrot.jbcrypt.BCrypt;

//사용자 정보 업데이트
public class UserUpdate {
	private Connection conn = null;

	public UserUpdate() {
		try {
			// JDBC Driver 로드
			Class.forName("com.mysql.cj.jdbc.Driver");
			// 데이터베이스 연결
			conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/project2", "root", "0000");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	// 비밀번호 업데이트
	public void updatePassword(int userId, String newPassword) {
		String sql = "UPDATE Users SET password=? WHERE user_id=?";
		try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
			pstmt.setString(1, BCrypt.hashpw(newPassword, BCrypt.gensalt())); // 비밀번호 해싱
			pstmt.setInt(2, userId);
			int rows = pstmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	// 이메일 업데이트
	public void updateEmail(int userId, String newEmail) {
		String sql = "UPDATE Users SET email=? WHERE user_id=?";
		try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
			pstmt.setString(1, newEmail);
			pstmt.setInt(2, userId);
			int rows = pstmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	// 유저 이름 업데이트
	public void updateUserName(int userId, String newUserName) {
		String sql = "UPDATE Users SET user_name=? WHERE user_id=?";
		try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
			pstmt.setString(1, newUserName);
			pstmt.setInt(2, userId);
			int rows = pstmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	// 전화번호 업데이트
	public void updatePhoneNumber(int userId, String newPhoneNumber) {
		String sql = "UPDATE Users SET phone_number=? WHERE user_id=?";
		try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
			pstmt.setString(1, newPhoneNumber);
			pstmt.setInt(2, userId);
			int rows = pstmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	// 주소 업데이트
	public void updateAddress(int userId, String newAddress) {
		String sql = "UPDATE Users SET address=? WHERE user_id=?";
		try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
			pstmt.setString(1, newAddress);
			pstmt.setInt(2, userId);
			int rows = pstmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	// 종료
	public void close() {
		if (conn != null) {
			try {
				// 연결 종료
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}
}
