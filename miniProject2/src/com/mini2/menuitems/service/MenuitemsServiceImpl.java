package com.mini2.menuitems.service;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import com.mini2.jdbcUtil.JdbcUtil;
import com.mini2.menuitems.dao.MenuitemsDao;
import com.mini2.menuitems.model.MenuitemsModel;

public class MenuitemsServiceImpl implements MenuitemsService{
  
  MenuitemsDao dao = new MenuitemsDao();

  @Override
  public List<MenuitemsModel> selectCategory(String category) {
    // TODO Auto-generated method stub
    
    // TODO Auto-generated method stub
    Connection conn = null;
    PreparedStatement pstmt = null;
    ResultSet rs = null;
    ArrayList<MenuitemsModel> list = null;
    //int menuid = scan.nextInt();
    try {
      //db 연결
      conn = JdbcUtil.connection();
      
      list = (ArrayList<MenuitemsModel>) dao.menuSelect(category);
    } catch (Exception e) {
      // TODO: handle exception
    }
    finally {
      JdbcUtil.close(conn, pstmt, rs);
    }
    return list;
   
  }
  

}
