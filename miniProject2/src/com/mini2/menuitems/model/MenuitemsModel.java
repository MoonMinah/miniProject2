package com.mini2.menuitems.model;


import lombok.Data;

@Data
public class MenuitemsModel {
  
  private int itemId; //아이템 아이디
  private int categoryId; //카테고리 아이디
  private String menuName; //메뉴이름
  private String description; //설명
  private int price; //가격
  private boolean available; //이용가능 여부

}
