package com.mini2.event;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import com.mini2.jdbcUtil.JdbcUtil;

public class UsersPointDao {

	// 사용자의 총 포인트를 조회하는 메서드
	public int getUserPoints(int userId) {
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		int totalPoints = 0;

		try {
			// 유틸리티 클래스로부터 데이터베이스 연결 가져오기
			conn = JdbcUtil.connection();

			// 특정 사용자의 포인트를 조회하는 쿼리
			String sql = "SELECT user_point FROM Users WHERE user_id = ?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, userId);
			rs = pstmt.executeQuery();

			if (rs.next()) {
				totalPoints = rs.getInt("user_point");
			}
		} catch (SQLException e) {
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

		return totalPoints;
	}

	// 사용자의 포인트를 차감하는 메서드
	public boolean deductUserPoints(int userId, int pointsToDeduct) {
		Connection conn = null;
		PreparedStatement pstmt = null;

		try {
			conn = JdbcUtil.connection();

			// Users 테이블에서 포인트 차감
			String updateUserSql = "UPDATE Users SET user_point = user_point - ? WHERE user_id = ?";
			pstmt = conn.prepareStatement(updateUserSql);
			pstmt.setInt(1, pointsToDeduct);
			pstmt.setInt(2, userId);
			int rowsAffected = pstmt.executeUpdate();

			// 포인트 차감 성공 여부 확인
			return rowsAffected > 0;
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		} finally {
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
