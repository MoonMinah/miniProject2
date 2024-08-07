package com.mini2.users.model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import org.mindrot.jbcrypt.BCrypt;
import com.mini2.event.Event;

public class UserInsert {

	public UserInsert(String newUserName, String newEmail, String newPassword, String newPhoneNumber, String newAddress,
			String referrerEmail) {
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {
			// JDBC Driver 로드
			Class.forName("com.mysql.cj.jdbc.Driver");
			// 데이터베이스 연결
			conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/project2", "root", "0000");
			conn.setAutoCommit(false); // 트랜잭션 시작

			// 이메일 중복 확인
			String checkEmailSql = "SELECT COUNT(*) FROM Users WHERE email = ?";
			pstmt = conn.prepareStatement(checkEmailSql);
			pstmt.setString(1, newEmail);
			rs = pstmt.executeQuery();

			if (rs.next() && rs.getInt(1) > 0) {
				System.out.println("이메일이 이미 존재합니다.");
				return;
			}

			// 새로운 사용자 추가
			String sql = "INSERT INTO Users (user_name, email, password, phone_number, address, registration_date, user_point) "
					+ "VALUES (?, ?, ?, ?, ?, ?, ?)";
			pstmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
			pstmt.setString(1, newUserName);
			pstmt.setString(2, newEmail);
			pstmt.setString(3, BCrypt.hashpw(newPassword, BCrypt.gensalt())); // 비밀번호 해싱
			pstmt.setString(4, newPhoneNumber);
			pstmt.setString(5, newAddress);
			pstmt.setTimestamp(6, new Timestamp(System.currentTimeMillis()));
			pstmt.setInt(7, 0);

			int rows = pstmt.executeUpdate();
			int newUserId = -1;

			// user_id 값 얻기
			if (rows == 1) {
				rs = pstmt.getGeneratedKeys();
				if (rs.next()) {
					newUserId = rs.getInt(1);
				}
			}

			if (newUserId != -1 && referrerEmail != null && !referrerEmail.isEmpty()) {
				// 추천인 이메일이 제공된 경우
				String referrerSql = "SELECT user_id FROM Users WHERE email = ?";
				pstmt = conn.prepareStatement(referrerSql);
				pstmt.setString(1, referrerEmail);
				rs = pstmt.executeQuery();

				if (rs.next()) {
					int referrerId = rs.getInt("user_id");

					// 이벤트 정보 가져오기
					int eventId = Event.getEventIdByName("Referral Program");

					if (eventId != -1) {
						// 추천인 이벤트 참여 기록 추가
						String referrerEventSql = "INSERT INTO UserEvents (user_id, event_id, participation_date, points_awarded) "
								+ "VALUES (?, ?, ?, ?)";
						pstmt = conn.prepareStatement(referrerEventSql);
						pstmt.setInt(1, referrerId); // 추천인 ID
						pstmt.setInt(2, eventId); // 이벤트 ID
						pstmt.setTimestamp(3, new Timestamp(System.currentTimeMillis())); // 참여 날짜
						pstmt.setInt(4, 100); // 추천인에게 부여된 포인트
						pstmt.executeUpdate();

						// 새로운 사용자 이벤트 기록 추가
						String newUserEventSql = "INSERT INTO UserEvents (user_id, event_id, participation_date, points_awarded) "
								+ "VALUES (?, ?, ?, ?)";
						pstmt = conn.prepareStatement(newUserEventSql);
						pstmt.setInt(1, newUserId); // 새로운 사용자 ID
						pstmt.setInt(2, eventId); // 이벤트 ID
						pstmt.setTimestamp(3, new Timestamp(System.currentTimeMillis())); // 참여 날짜
						pstmt.setInt(4, 50); // 새로운 사용자에게 부여된 포인트
						pstmt.executeUpdate();

						// 추천인에게 포인트 부여
						String userUpdateReferrerSql = "UPDATE Users SET user_point = user_point + ? WHERE user_id = ?";
						pstmt = conn.prepareStatement(userUpdateReferrerSql);
						pstmt.setInt(1, 100); // 추천인에게 부여된 포인트
						pstmt.setInt(2, referrerId);
						pstmt.executeUpdate();

						// 새로운 사용자에게 포인트 부여
						String userUpdateNewUserSql = "UPDATE Users SET user_point = user_point + ? WHERE user_id = ?";
						pstmt = conn.prepareStatement(userUpdateNewUserSql);
						pstmt.setInt(1, 50); // 새로운 사용자에게 부여된 포인트
						pstmt.setInt(2, newUserId);
						pstmt.executeUpdate();

					}
				}
			}

			conn.commit(); // 트랜잭션 커밋
		} catch (SQLException e) {
			// SQL 예외 처리
			e.printStackTrace();
			if (conn != null) {
				try {
					conn.rollback(); // 트랜잭션 롤백
				} catch (SQLException ex) {
					ex.printStackTrace();
				}
			}
		} catch (ClassNotFoundException e) {
			// JDBC 드라이버 클래스 로드 실패 예외 처리
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
