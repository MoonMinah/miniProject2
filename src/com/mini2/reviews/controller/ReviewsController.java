package com.mini2.reviews.controller;

import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;

import com.mini2.reviews.dao.ReviewsDao;
import com.mini2.reviews.service.ReivewsService;

public class ReviewsController {
	private ReviewsDao reviewsDao;
	private ReivewsService reviewsService;
	
	public ReviewsController(ReviewsDao reviewsDao, ReivewsService reviewsService) {
        this.reviewsDao = reviewsDao;
        this.reviewsService = reviewsService;
    }
	
	
	// 리뷰 작성 메서드
    public void writeReview() {
    
            int rating = reviewsService.getRating();
            String comment = reviewsService.getComment();        
            //Timestamp reviewDate = new Timestamp(System.currentTimeMillis());

            reviewsDao.writeReview(rating, comment);
   
    }
	
	  
}
