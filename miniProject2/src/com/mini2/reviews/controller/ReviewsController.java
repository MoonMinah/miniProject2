package com.mini2.reviews.controller;

import com.mini2.reviews.dao.ReviewsDao;
import com.mini2.reviews.service.ReivewsService;

public class ReviewsController {
	private ReviewsDao reviewDao;
	private ReivewsService reviewsService;
	
	public ReviewsController(ReviewsDao reviewsDao, ReivewsService reviewsService) {
        this.reviewDao = reviewsDao;
        this.reviewsService = reviewsService;
    }
	
	
	  
}
