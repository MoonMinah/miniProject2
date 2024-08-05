package com.mini2.menuitems.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import com.mini2.category.model.CategoryModel;
import com.mini2.jdbcUtil.JdbcUtil;
import com.mini2.menuitems.model.MenuitemsModel;

public class MenuitemsDao {
  
  Connection conn = null;
  PreparedStatement pstmt = null;
  ResultSet rs = null;
  
  public List<MenuitemsModel> menuSelect(String category) {
    
    //메뉴 목록
    List<MenuitemsModel> list = new ArrayList<MenuitemsModel>();
    
    try {
      conn = JdbcUtil.connection();
      
      //String sql = " SELECT * FROM category ";
      String sql = " select m.item_id, m.menu_name, description, m.price " +
                  " from menuitems m " +
                  "inner join category c on m.category_id = c.category_id " +
                  "where c.category_name = ?";
      pstmt = conn.prepareStatement(sql);
      //int menuId = Scanner.nextInt();
      //MenuitemsModel menuitemsModel = new MenuitemsModel();
      //pstmt.setInt(1, model.getCategoryId());
      pstmt.setString(1, category);
      //ResultSet을 통해 데이터 읽기
      rs = pstmt.executeQuery();
      
      
      
      while (rs.next()) {
      MenuitemsModel model = new MenuitemsModel();
      model.setItemId(rs.getInt("item_id"));
      model.setMenuName(rs.getString("menu_name"));
      model.setDescription(rs.getString("description"));
      model.setPrice(rs.getInt("price"));
      list.add(model);
      }
      
      
  

    } catch (SQLException e) {
      // TODO: handle exception
      e.printStackTrace();
    }
    return list;
    
    
  }

}
