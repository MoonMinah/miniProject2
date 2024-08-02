package com.mini2.orders.model;

import java.sql.Timestamp;
import lombok.Data;

@Data
public class OrdersModel {
  
  private int orderId; //주문아이디
  private int userId; //유저아이디
  private Timestamp orderDate; //주문날짜
  private double totalAmount; //총 금액
  private boolean status; //주문 상태
  
  
  

}
