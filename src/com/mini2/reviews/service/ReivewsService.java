package com.mini2.reviews.service;

import java.util.Scanner;

import com.mini2.reviews.controller.ReviewsController;
import com.mini2.reviews.dao.ReviewsDao;

public class ReivewsService {
	private Scanner scanner;
	
	public ReivewsService() {
	    scanner = new Scanner(System.in);
	}
	

	public static void main(String[] args) {
	

		
		ReviewsDao rd = new ReviewsDao();
		rd.writeReview(1, "좋아요");
	}

	public int getRating() {
	    System.out.print("평점을 입력하세요 (1-5): ");
	    return scanner.nextInt();
	}
	
	public String getComment() {
	    System.out.print("평가 내용을 입력하세요: ");
	    scanner.nextLine();  // consume newline
	    return scanner.nextLine();
	}   
	
	
	
}
