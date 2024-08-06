package com.mini2.reviews.controller;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import com.mini2.main.Main;
import com.mini2.reviews.model.ReviewsModel;
import com.mini2.reviews.service.ReviewServiceImpl;
import com.mini2.users.service.UsersService;

public class ReviewsController {

	private static Scanner sc = new Scanner(System.in);
	static ReviewServiceImpl rsi = new ReviewServiceImpl();

	public void wirteReview(int paymentId) {
		System.out.print("\t평점을 입력하세요 (1-5): ");
		int rating = sc.nextInt();
		sc.nextLine();

		System.out.print("\t평가 내용을 입력하세요: ");
		String comment = sc.nextLine();

		rsi.writeReviews(paymentId, rating, comment);
	}

	public void readReview() {
		System.out.println("\t리뷰아이디\t\t작성날짜\t\t평점\t작성내용");
		System.out.println("------------------------------------------------");
		List<ReviewsModel> reviewsModelList = new ArrayList();
		reviewsModelList = rsi.readReviews();

		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		for (ReviewsModel rm : reviewsModelList) {
			String date = sdf.format(rm.getReviewDate());

			System.out.println(rm.getReviewId() + "." + "\t\t" + date + "\t" + rm.getRating() + "\t" + rm.getComment());
		}

		System.out.println("------------------------------------------------");
		System.out.println("\t1.리뷰 수정하기\n\t2.리뷰 삭제하기\n\t3.홈으로 ");
		int num = sc.nextInt();
		sc.nextLine();
		switch (num) {
		case 1:
			UpdateteReview();
			break;
		case 2:
			deleteReview();
			break;
		case 3:
			return;
		}

	}

	public void deleteReview() {
		System.out.println("삭제를 원하는 리뷰 번호를 입력하세요: ");
		int id = sc.nextInt();
		sc.nextLine();

		System.out.print("리뷰를 삭제 하시겠습니까? (y/n): ");
		String yesOrNo = sc.nextLine();
		if (yesOrNo.equals("y")) {
			rsi.deleteReviews(id);

		} else if (yesOrNo.equals("n")) {
			return;
		} else {
			System.out.println("잘못 입력하셨습니다.");
		}
	}

	public void UpdateteReview() {
		System.out.println("수정을 원하는 리뷰 번호를 입력하세요: ");
		int id = sc.nextInt();
		sc.nextLine();

		System.out.print("평점을 입력하세요 (1-5): ");
		int rating = sc.nextInt();
		sc.nextLine();

		System.out.print("평가 내용을 입력하세요: ");
		String comment = sc.nextLine();

		rsi.updateReviews(id, rating, comment);
		return;
	}

}
