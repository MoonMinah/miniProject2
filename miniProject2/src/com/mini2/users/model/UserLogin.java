package com.mini2.users.model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

//사용자의 로그인 인증 처리
public class UserLogin {

	public Integer UserLogin(String email, String password) {
		Integer userId = null;
		try {
			// JDBC Driver 로드
			Class.forName("com.mysql.cj.jdbc.Driver");
			Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/project2", "root", "0000");
			String sql = "SELECT user_id, password FROM Users WHERE email=?";
			PreparedStatement pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, email);
			ResultSet rs = pstmt.executeQuery();

			if (rs.next()) {
				userId = rs.getInt("user_id");
				String storedPassword = rs.getString("password");
				
				//비밀번호가 틀렸을 경우
				if (!password.equals(storedPassword)) {
					System.out.println("비밀번호가 틀렸습니다.");
					userId = null;
				}
				// 이메일이 존재하지 않는 경우
			} else {
				System.out.println("존재하지 않는 이메일입니다.");
			}

			rs.close();
			pstmt.close();
			conn.close();

		} catch (Exception e) {
			e.printStackTrace();
		}
		return userId;
	}
}
