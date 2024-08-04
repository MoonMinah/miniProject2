package com.mini2.reviews.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;

import com.mini2.jdbcUtil.JdbcUtil;

//db와 데이터 주고 받음
public class ReviewsDao {
	public void writeReview(int rating, String comment) {
		try {
		  Connection conn = JdbcUtil.connection();
		
		  String sql = "INSERT INTO reviews (payment_id, user_id, rating, comment) VALUES (?, ?, ?, ?)";
		  
		  PreparedStatement pstmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
	
		  int payment_id = 1;
		  int user_id =1;
		  pstmt.setInt(1, payment_id);
		  pstmt.setInt(2, user_id);
		  pstmt.setInt(3, rating);
		  pstmt.setString(4, comment);
		  //pstmt.setTimestamp(3, reviewDate);
		  
		  pstmt.executeUpdate();
		  
		  
		 } catch (SQLException e) {
		      // TODO: handle exception
		      e.printStackTrace();
		    }
	}
	
	
//	Connection conn = null;
//	  PreparedStatement pstmt = null;
//	  ResultSet rs = null;
//	  
//	  public List<ReviewsModel> reviewSelect() {
//	    
//	    //카테고리 목록
//	    List<ReviewsModel> list = new ArrayList<ReviewsModel>();
//	    
//	    try {
//	      conn = JdbcUtil.connection();
//	      
//	      String sql = " SELECT * FROM reviews ";
//	      
//	      pstmt = conn.prepareStatement(sql);
//	      
//	      //ResultSet을 통해 데이터 읽기
//	      rs = pstmt.executeQuery();
//	      
//	      
//	      
//	      while (rs.next()) {
//	    	  ReviewsModel model = new ReviewsModel();
//	      model.setReviewId(rs.getInt("review_id"));
//	      model.setPaymentId(rs.getInt("payment_id"));
//	      model.setRating(rs.getInt("rating"));
//	      model.setComment(rs.getString("comment"));
//	      model.setReviewDate(rs.getTimestamp("review_date"));
//	      list.add(model);
//	      }
//	      
//	  
//
//	    } catch (SQLException e) {
//	      // TODO: handle exception
//	      e.printStackTrace();
//	    }
//	    return list;
//	    
//	    
//	  }
}
