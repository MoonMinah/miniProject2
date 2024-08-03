package com.mini2.reviews.service;

import java.util.Scanner;

public class ReivewsService {
	 private Scanner sc = new Scanner(System.in);
	
	 public static void main(String[] args) {
		 
		
	 }
	 

	    public int getRating() {
	        System.out.print("평점을 입력하세요 (1-5): ");
	        return sc.nextInt();
	    }

	    public String getComment() {
	        System.out.print("평가 내용을 입력하세요: ");
	        return sc.nextLine();
	    }	    
}
