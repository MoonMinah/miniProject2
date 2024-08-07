package com.mini2.event;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import com.mini2.jdbcUtil.JdbcUtil;

public class UsersPointDao {

	public int getUserPoints(int userId) {
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		int totalPoints = 0;

		try {
			/// 유틸리티 클래스로부터 데이터베이스 연결 가져오기
			conn = JdbcUtil.connection();

			// 특정 사용자의 포인트 합계를 구하는 쿼리
			String sql = "SELECT SUM(points_awarded) AS total_points FROM UserEvents WHERE user_id = ?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, userId);
			rs = pstmt.executeQuery();

			if (rs.next()) {
				totalPoints = rs.getInt("total_points");
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

	// 사용한 만큼 포인트 차감
	public boolean deductUserPoints(int userId, int pointsToDeduct) {
		String sql = "UPDATE UserEvents SET points_awarded = points_awarded - ? WHERE user_id = ?";

		try (Connection conn = JdbcUtil.connection(); PreparedStatement pstmt = conn.prepareStatement(sql)) {

			pstmt.setInt(1, pointsToDeduct);
			pstmt.setInt(2, userId);

			int rows = pstmt.executeUpdate();

			// 업데이트된 행의 수가 0이면 사용자 ID가 잘못된 경우
			return rows > 0;

		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
	}

}
