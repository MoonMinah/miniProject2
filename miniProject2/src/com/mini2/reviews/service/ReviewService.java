package com.mini2.reviews.service;

import java.util.List;

import com.mini2.reviews.model.ReviewsModel;

public interface ReviewService {
	public void writeReviews(int paymentId, int rating, String comment);

	public List<ReviewsModel> readReviews();

	public void deleteReviews(int num);

	public void updateReviews(int id, int rating, String comment);
}
