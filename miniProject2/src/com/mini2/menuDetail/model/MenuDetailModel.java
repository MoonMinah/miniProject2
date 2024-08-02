package com.mini2.menuDetail.model;

import lombok.Data;

@Data
//메뉴 상세
public class MenuDetailModel {

  private int menuDetailId; //메뉴상세 아이디
  private int itemId; //아이템 아이디
  private int orderId; //주문아이디
  private int categoryId;//카테고리 아이디
  private int quantity; //수량

  
  
}
