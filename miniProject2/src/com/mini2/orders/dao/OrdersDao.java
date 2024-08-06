package com.mini2.orders.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import com.mini2.category.model.CategoryModel;
import com.mini2.jdbcUtil.JdbcUtil;


// db와 데이터 주고 받음
public class OrdersDao {


  Connection conn = null;
  PreparedStatement pstmt = null;
  ResultSet rs = null;

  public List<CategoryModel> categorySelect() {

    // 카테고리 목록
    List<CategoryModel> list = new ArrayList<CategoryModel>();

    try {
      conn = JdbcUtil.connection();

      String sql = " SELECT * FROM category ";

      pstmt = conn.prepareStatement(sql);

      // ResultSet을 통해 데이터 읽기
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

  // 주문 테이블 데이터 삽입.
  public static int insertOrder(Connection conn, int userId, int totalAmount) throws SQLException {
    String sql = "INSERT INTO orders (user_id, total_amount, status) VALUES (?, ?, ?)";
    try (PreparedStatement pstmt =
        conn.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS)) {
      pstmt.setInt(1, userId);
      pstmt.setInt(2, totalAmount);
      pstmt.setInt(3, 0); // 미결제
      int affectedRows = pstmt.executeUpdate();

      if (affectedRows == 0) {
        throw new SQLException("주문 실패, 열이 없음.");
      }

      try (ResultSet generatedKeys = pstmt.getGeneratedKeys()) {
        if (generatedKeys.next()) {
          return generatedKeys.getInt(1);
        } else {
          throw new SQLException("주문 실패, 주문 아이디x");
        }
      }
    }
  }

  public static void insertMenuDetail(Connection conn, int orderId, int itemId, int quantity)
      throws SQLException {
    String sql = "INSERT INTO menu_detail (item_id, order_id, quantity) VALUES (?, ?, ?)";
    try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
      pstmt.setInt(1, itemId);
      pstmt.setInt(2, orderId);
      pstmt.setInt(3, quantity);
      pstmt.executeUpdate();
    }
  }

  public static void updateOrderTotalAmount(Connection conn, int orderId, int totalAmount)
      throws SQLException {
    String sql = "UPDATE orders SET total_amount = ? WHERE order_id = ?";
    try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
      pstmt.setInt(1, totalAmount);
      pstmt.setInt(2, orderId);
      pstmt.executeUpdate();
    }
  }
  //외래키로 인한 메뉴 디테일부터 삭제 후 주문 테이블 삭제
  public boolean deleteMenuDetail(int orderId) {
    // TODO Auto-generated method stub
    String sql = "DELETE FROM menu_detail WHERE order_id = ?";
    try {
      conn = JdbcUtil.connection();
      PreparedStatement pstmt = conn.prepareStatement(sql);
      pstmt.setInt(1, orderId);
      int affectedRows = pstmt.executeUpdate();
      return affectedRows > 0;
    } catch (SQLException e) {
      e.printStackTrace();
      return false;
    }finally {
      JdbcUtil.close(conn, pstmt);
    }
  }

  public boolean deleteOrder(int orderId) {
    // TODO Auto-generated method stub
    String sql = "DELETE FROM orders WHERE order_id = ?";
    try {
      conn = JdbcUtil.connection();
      PreparedStatement pstmt = conn.prepareStatement(sql);
      pstmt.setInt(1, orderId);
      int affectedRows = pstmt.executeUpdate();
      return affectedRows > 0;
    } catch (SQLException e) {
      e.printStackTrace();
      return false;
    }finally {
      JdbcUtil.close(conn, pstmt);
    }
  }
}
