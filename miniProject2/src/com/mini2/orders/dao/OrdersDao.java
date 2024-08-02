package com.mini2.orders.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import com.mini2.category.model.CategoryModel;
import com.mini2.jdbcUtil.JdbcUtil;


//db와 데이터 주고 받음
public class OrdersDao {
  
  Connection conn = null;
  PreparedStatement pstmt = null;
  ResultSet rs = null;
  
  public List<CategoryModel> categorySelect() {
    
    //카테고리 목록
    List<CategoryModel> list = new ArrayList<CategoryModel>();
    
    try {
      conn = JdbcUtil.connection();
      
      String sql = " SELECT * FROM category ";
      
      pstmt = conn.prepareStatement(sql);
      
      //ResultSet을 통해 데이터 읽기
      rs = pstmt.executeQuery();
      
      
      
      while (rs.next()) {
      CategoryModel model = new CategoryModel();
      model.setCategoryId(rs.getInt("category_id"));
      model.setCategoryName(rs.getString("category_name"));
      list.add(model);
      }
      
  

    } catch (SQLException e) {
      // TODO: handle exception
      e.printStackTrace();
    }
    return list;
    
    
  }
  
  

}
