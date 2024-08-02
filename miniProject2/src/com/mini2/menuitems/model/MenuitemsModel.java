package com.mini2.menuitems.model;


import lombok.Data;

@Data
public class MenuitemsModel {
  
  private int itemId;
  private int categoryId;
  private String menuName;
  private String description;
  private double price;
  private boolean available;

}
