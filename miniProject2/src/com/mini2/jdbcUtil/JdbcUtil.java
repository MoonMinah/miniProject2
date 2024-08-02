package com.mini2.jdbcUtil;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

/**
 * 모든 기능에 db 연결하는 부분이 들어가므로 유지보수와 효율적으로 작업하기 위해 기능 구현.
 */

//db연결 기능.
public class JdbcUtil {
  
  public static Connection connection() {
    
    String url = "jdbc:mysql://localhost:3306/project2";
    String name = "root";
    String pw = "0000";
    Connection conn = null;
    
    try {
    //jdbc Driver 등록
      Class.forName("com.mysql.cj.jdbc.Driver");
      
      //연결하기
      conn = DriverManager.getConnection(url, name, pw);
    } catch (Exception e) {
      // TODO: handle exception
      e.printStackTrace();
    }
    
    
    return conn;
    
  }
  
  
  
  //Connection 자원 해제
  public static void close(Connection conn) {
    try {
      if(conn != null) {
        conn.close();
      }
    } catch (Exception e) {
      // TODO: handle exception
      e.printStackTrace();
    }
  }

    //Connection 및 PreparedStatement 자원 해제
    public static void close(Connection conn, PreparedStatement ptsmt) {
      try {
        if(ptsmt != null) {
          ptsmt.close();
        }
        if(conn != null) {
          conn.close();
        }
      } catch (Exception e) {
        // TODO: handle exception
        e.printStackTrace();
      }
    }
    
  //Connection, PreparedStatement, ResultSet 자원 해제
    public static void close(Connection conn, PreparedStatement pstmt, ResultSet rs) {
      try {
        if(rs != null) {
          rs.close();
        }
        
        if(pstmt != null) {
          pstmt.close();
        }
        
        if(conn != null) {
          conn.close();
        }
      } catch (Exception e) {
        // TODO: handle exception
        e.printStackTrace();
      }
    }

}
