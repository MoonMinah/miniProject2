package com.mini2.reviews.service;

import java.util.ArrayList;
import java.util.List;

import com.mini2.reviews.dao.ReviewsDao;
import com.mini2.reviews.model.ReviewsModel;

//ReviewService 인테페이스 구현
public class ReviewServiceImpl implements ReviewService {

	ReviewsDao rd = new ReviewsDao();
	List<ReviewsModel> reviewsModelList = new ArrayList<ReviewsModel>();

	@Override
	public void writeReviews(int paymentId, int rating, String comment) {

		rd.writeReview(paymentId, rating, comment);
	}

	@Override
	public List<ReviewsModel> readReviews() {

		reviewsModelList = rd.readReview();

		return reviewsModelList;
	}

	@Override
	public void deleteReviews(int id) {
		// TODO Auto-generated method stub
		rd.deleteReview(id);

	}

	@Override
	public void updateReviews(int id, int rating, String comment) {

		rd.updateReview(id, rating, comment);
	}

}
