package com.mini2.reviews.model;

import java.sql.Timestamp;

import lombok.Data;

@Data
public class ReviewsModel {
//	private int reviewId; //리뷰 아이디
//	private int paymentId; //결제 아이디
//	private int orderId; //주문 아이디
//	private int userId; //유저 아이디
	private int rating; //평점
	private String comment; //리뷰 내용
	private Timestamp reviewDate; //리뷰 날짜
	
}
