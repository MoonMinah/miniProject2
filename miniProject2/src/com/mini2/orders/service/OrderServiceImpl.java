package com.mini2.orders.service;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import com.mini2.category.model.CategoryModel;
import com.mini2.jdbcUtil.JdbcUtil;
import com.mini2.orders.dao.OrdersDao;

public class OrderServiceImpl implements OrderService{

  private OrdersDao dao = new OrdersDao();
  

  @Override
  public List<CategoryModel> categoryAll() {
    // TODO Auto-generated method stub
    Connection conn = null;
    PreparedStatement pstmt = null;
    ResultSet rs = null;
    ArrayList<CategoryModel> list = null;
    try {
      //db 연결
      conn = JdbcUtil.connection();
      
      list = (ArrayList<CategoryModel>) dao.categorySelect();
    } catch (Exception e) {
      // TODO: handle exception
    }
    finally {
      JdbcUtil.close(conn, pstmt, rs);
    }
    return list;
   
  }

}
