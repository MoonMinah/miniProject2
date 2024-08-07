package com.mini2.orders.service;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import com.mini2.category.model.CategoryModel;
import com.mini2.jdbcUtil.JdbcUtil;
import com.mini2.menuDetail.dao.MenuDetailDao;
import com.mini2.menuitems.model.MenuitemsModel;
import com.mini2.orders.dao.OrdersDao;

public class OrderServiceImpl implements OrderService{

  private OrdersDao dao = new OrdersDao();
  private MenuDetailDao menuDetailDao = new MenuDetailDao();
  

  @Override
  public List<CategoryModel> categoryMenu() {
    // TODO Auto-generated method stub
    Connection conn = null;
    PreparedStatement pstmt = null;
    ResultSet rs = null;
    ArrayList<CategoryModel> list = null;
    try {
      //db 연결
      conn = JdbcUtil.connection();
      
      list = (ArrayList<CategoryModel>) dao.categoryMenu();
    } catch (Exception e) {
      // TODO: handle exception
    }
    finally {
      JdbcUtil.close(conn, pstmt, rs);
    }
    return list;
   
  }


  @Override
  public int placeOrder(int userId, List<MenuitemsModel> menuItems, List<Integer> quantities) throws SQLException {
    // TODO Auto-generated method stub
    Connection conn = null;
    int orderId = 0;
    try {
      conn = JdbcUtil.connection();
      conn.setAutoCommit(false); // 트랜잭션 시작

      int totalAmount = 0;

      // 주문 테이블에 데이터 삽입
      orderId = OrdersDao.insertOrder(conn, userId, totalAmount);

      // 메뉴 상세 테이블에 데이터 삽입
      for (int i = 0; i < menuItems.size(); i++) {
        MenuitemsModel menuItem = menuItems.get(i);
        int quantity = quantities.get(i);
        dao.insertMenuDetail(conn, orderId, menuItem.getItemId(), quantity);
        totalAmount += menuItem.getPrice() * quantity; // 총 금액 계산
      }

      // 주문 테이블의 총 금액 업데이트
      dao.updateOrderTotalAmount(conn, orderId, totalAmount);

      conn.commit(); // 트랜잭션 커밋
    } catch (SQLException e) {
      if (conn != null) {
        conn.rollback(); // 오류 발생 시 롤백
      }
      throw e;
    } finally {
      JdbcUtil.close(conn);
    }
    return orderId;
  }

  public List<MenuitemsModel> getMenuByCategory(String category) {
    Connection conn = null;
    List<MenuitemsModel> menuList = null;
    try {
      conn = JdbcUtil.connection();
      menuList = menuDetailDao.menuSelect(conn, category);
    } catch (SQLException e) {
      e.printStackTrace();
    } finally {
      JdbcUtil.close(conn);
    }
    return menuList;
  }

  @Override
  public boolean deleteMenuDetail(int orderId) {
    // TODO Auto-generated method stub
    return dao.deleteMenuDetail(orderId);
  }
  
  @Override
  public boolean deleteOrder(int orderId) {
    // TODO Auto-generated method stub
      return dao.deleteOrder(orderId);
  
  }


  @Override
  public boolean updateOrderStatus(int orderId) {
    // TODO Auto-generated method stub
    return dao.updateOrderStatus(orderId);
  }


 
}
