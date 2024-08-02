package com.mini2.orders.controller;

import java.util.List;
import com.mini2.category.model.CategoryModel;
import com.mini2.orders.service.OrderServiceImpl;

public class OrdersController {
  

  public static void main(String[] args) {
    
    OrderServiceImpl impl = new OrderServiceImpl();
    List<CategoryModel> categories = impl.categoryAll();
    
    System.out.println();
    System.out.println("[메뉴 목록]");
    System.out.println("-------------------------------------------------------");
    
    if (categories != null && !categories.isEmpty()) {
      //System.out.println("카테고리 목록:");
      for (CategoryModel category : categories) {
          System.out.printf("%d.%s \t|", category.getCategoryId(), category.getCategoryName());
      }
      System.out.println();
  } else {
      System.out.println("카테고리를 불러올 수 없습니다.");
  }
    System.out.println("-------------------------------------------------------");
    System.out.printf("%55s\n", "|");
    System.out.printf("%55s\n", "|");
    System.out.printf("%55s\n", "|");
    System.out.printf("%55s\n", "|");
    System.out.printf("%55s\n", "|");
    System.out.printf("%55s\n", "|");
    System.out.printf("%55s\n", "|");
    System.out.printf("%55s\n", "|");
   
    System.out.println("-------------------------------------------------------");
    
    
    System.out.println("카테고리를 입력해주세요.");
    System.out.println("1. 커피");
    System.out.println("메뉴를 입력해주세요.");
    System.out.println("아이스 아메리카노");
    System.out.println("수량을 입력해주세요.");
    System.out.println("2");
    System.out.println("추가 주문하시겠습니까? (Y/N)");
  }
  
  
}
