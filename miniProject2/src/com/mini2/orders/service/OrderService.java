package com.mini2.orders.service;

import java.sql.SQLException;
import java.util.List;
import com.mini2.category.model.CategoryModel;
import com.mini2.menuitems.model.MenuitemsModel;

public interface OrderService {
  
 List<CategoryModel> categoryMenu(); //카테고리 목록 조회
 int placeOrder(int userId, List<MenuitemsModel> menuItems, List<Integer> quantities) throws SQLException; //주문처리
 boolean deleteMenuDetail(int orderId);
 boolean deleteOrder(int orderId); //결제 취소 시, 주문도 삭제

}
