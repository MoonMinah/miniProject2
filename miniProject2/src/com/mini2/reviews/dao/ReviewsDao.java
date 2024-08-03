package com.mini2.reviews.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.mini2.jdbcUtil.JdbcUtil;
import com.mini2.reviews.model.ReviewsModel;

//db와 데이터 주고 받음
public class ReviewsDao {
	Connection conn = null;
	  PreparedStatement pstmt = null;
	  ResultSet rs = null;
	  
	  public List<ReviewsModel> reviewSelect() {
	    
	    //카테고리 목록
	    List<ReviewsModel> list = new ArrayList<ReviewsModel>();
	    
	    try {
	      conn = JdbcUtil.connection();
	      
	      String sql = " SELECT * FROM reviews ";
	      
	      pstmt = conn.prepareStatement(sql);
	      
	      //ResultSet을 통해 데이터 읽기
	      rs = pstmt.executeQuery();
	      
	      
	      
	      while (rs.next()) {
	    	  ReviewsModel model = new ReviewsModel();
	      model.setReviewId(rs.getInt("review_id"));
	      model.setPaymentId(rs.getInt("payment_id"));
	      model.setRating(rs.getInt("rating"));
	      model.setComment(rs.getString("comment"));
	      model.setReviewDate(rs.getTimestamp("review_date"));
	      list.add(model);
	      }
	      
	  

	    } catch (SQLException e) {
	      // TODO: handle exception
	      e.printStackTrace();
	    }
	    return list;
	    
	    
	  }
}
