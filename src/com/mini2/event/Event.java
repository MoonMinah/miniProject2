package com.mini2.event;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;

public class Event {

	public static int getEventIdByName(String eventName) {
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		int eventId = -1;

		try {
			// JDBC Driver 로드
			Class.forName("com.mysql.cj.jdbc.Driver");
			// 데이터베이스 연결
			conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/project2", "root", "0000");

			String sql = "SELECT event_id FROM Events WHERE event_name = ? AND active = 1";
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, eventName);
			rs = pstmt.executeQuery();

			if (rs.next()) {
				eventId = rs.getInt("event_id");
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
		return eventId;
	}
}
