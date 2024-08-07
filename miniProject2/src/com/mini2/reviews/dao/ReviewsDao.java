package com.mini2.reviews.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.mini2.jdbcUtil.JdbcUtil;
import com.mini2.reviews.model.ReviewsModel;
import com.mini2.users.controller.UsersController;

//db와 데이터 주고 받음
public class ReviewsDao {

	Connection conn = null;
	PreparedStatement pstmt = null;
	ResultSet rs = null;

	// 리뷰 등록
	public void writeReview(int paymentId, int rating, String comment) {
		// user_id 받아오기
		UsersController userController = UsersController.getInstance();
		Map<String, Integer> session = userController.getSession();
		Integer userIdtemp = session.get("user_id"); // Integer 객체로 받아오기
		if (userIdtemp == null) {
			System.out.println("\t사용자가 로그인되지 않았습니다.");
			return; // 또는 예외 처리
		}

		// 유저 아이디, 결제 아이디 저장
		int user_id = userIdtemp.intValue();
		int payment_id = paymentId;

		try {
			// db연결
			Connection conn = JdbcUtil.connection();

			String sql = "INSERT INTO reviews (payment_id, user_id, rating, comment) VALUES (?, ?, ?, ?)";

			PreparedStatement pstmt = conn.prepareStatement(sql);

			pstmt.setInt(1, payment_id);
			pstmt.setInt(2, user_id);
			pstmt.setInt(3, rating);
			pstmt.setString(4, comment);

			int rows = pstmt.executeUpdate();
			if (rows > 0) {

				System.out.println("\t📖 리뷰가 등록되었습니다.");

			}

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			JdbcUtil.close(conn, pstmt);
		}
	}

	// 리뷰 조회
	public List<ReviewsModel> readReview() {
		UsersController userController = UsersController.getInstance();
		Map<String, Integer> session = userController.getSession();
		Integer userIdtemp = session.get("user_id"); // Integer 객체로 받아오기

		if (userIdtemp == null) {
			System.out.println("\t사용자가 로그인되지 않았습니다.");
			return new ArrayList<>(); // 또는 예외 처리
		}
		int user_id = userIdtemp.intValue();
		List<ReviewsModel> reviewsModelList = new ArrayList<ReviewsModel>();

		try {

			// reviewsModelList.clear();
			conn = JdbcUtil.connection();

			String sql = "SELECT review_id, rating, comment, review_date FROM project2.reviews WHERE user_id = ? ";

			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, user_id); // 쿼리 실행 전에 파라미터 설정

			rs = pstmt.executeQuery();
			while (rs.next()) {
				ReviewsModel rm = new ReviewsModel();
				rm.setReviewId(rs.getInt("review_id"));
				rm.setRating(rs.getInt("rating"));
				rm.setComment(rs.getString("comment"));
				rm.setReviewDate(rs.getTimestamp("review_date"));

				reviewsModelList.add(rm);

			}

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			JdbcUtil.close(conn, pstmt, rs);
		}
		return reviewsModelList;
	}

	// 리뷰 삭제
	public void deleteReview(int id) {
		try {
			conn = JdbcUtil.connection();

			String sql = "DELETE FROM reviews WHERE review_id = ? "; // 해당 리뷰 지우기
			pstmt = conn.prepareStatement(sql);

			pstmt.setInt(1, id);
			int rows = pstmt.executeUpdate();

			if (rows > 0) {

				System.out.println("\t📖 리뷰가 삭제되었습니다.");

			}

		} catch (SQLException e) {
			// TODO: handle exception
			e.printStackTrace();
		} finally {
			JdbcUtil.close(conn, pstmt);
		}
	}

	// 리뷰 수정
	public void updateReview(int id, int rating, String comment) {
		try {
			conn = JdbcUtil.connection();

			String sql = "UPDATE reviews SET rating=?, comment=?, review_date = now() WHERE review_id = ? ";

			pstmt = conn.prepareStatement(sql);

			pstmt.setInt(1, rating);
			pstmt.setString(2, comment);
			pstmt.setInt(3, id);

			int rows = pstmt.executeUpdate();
			if (rows > 0) {
				System.out.println("\t📖 리뷰가 수정되었습니다.");

			}

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			JdbcUtil.close(conn, pstmt);
		}

	}

}
