package com.mini2.reviews.controller;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import com.mini2.main.Main;
import com.mini2.reviews.model.ReviewsModel;
import com.mini2.reviews.service.ReviewServiceImpl;
import com.mini2.users.service.UsersService;

//화면 출력 담당
public class ReviewsController {
	List<ReviewsModel> reviewsModelList = new ArrayList();
	private static Scanner sc = new Scanner(System.in);
	static ReviewServiceImpl rsi = new ReviewServiceImpl();

	// 리뷰 작성 페이지
	public void wirteReview(int paymentId) {
		int rating;
		String comment;

		// 평점 유효성 검사
		while (true) {
			System.out.print("\t평점을 입력하세요 (1-5): ");
			rating = sc.nextInt();
			sc.nextLine();

			if (rating >= 1 && rating <= 5) {
				break; // 올바른 범위의 숫자가 입력되면 반복 종료
			} else {
				System.out.println("\t잘못 입력하셨습니다. 1-5 사이의 숫자를 입력하세요.");
			}

		}

		// 평가 내용 유효성 검사
		while (true) {
			System.out.print("\t내용을 입력하세요 (5자 이내): ");
			comment = sc.nextLine();

			if (comment.length() <= 50) {
				break; // 입력된 내용이 50자 이내일 경우 반복 종료
			} else {
				System.out.println("\t잘못 입력하셨습니다. 5자 이내로 입력하세요.");
			}
		}

		// ReviewServiceImpl 클래스로 이동
		// 입력 받은 평점, 평가 내용 매개변수로 전달
		rsi.writeReviews(paymentId, rating, comment);

	}

	// 리뷰 조회 페이지
	public void readReview() {

		System.out.println("\n\t\t\t\t  [리뷰 목록]  ");
		System.out.println("\t\t======================================================");

		System.out.println("\t\t리뷰 번호\t\t작성 날짜\t\t평점\t작성 내용");
		System.out.println("\t\t------------------------------------------------------");
//		List<ReviewsModel> reviewsModelList = new ArrayList();

		reviewsModelList = rsi.readReviews();

		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");


		// ReviewsModel rm : reviewsModelList
		for (int i = 0; i < reviewsModelList.size(); i++) {
			ReviewsModel rm = reviewsModelList.get(i);
			String date = sdf.format(rm.getReviewDate());
			System.out.println(
					"\t\t" + rm.getReviewId() + "\t\t" + date + "\t" + rm.getRating() + "\t" + rm.getComment());
		}

		System.out.println("\t\t------------------------------------------------------");
		boolean validInput = false;
		while (!validInput) {
			System.out.println("\n\t1. 리뷰 수정하기\n\t2. 리뷰 삭제하기\n\t3. 뒤로가기");
			System.out.print("\t원하시는 번호를 선택하세요 => ");
			int num = sc.nextInt();
			sc.nextLine();
			switch (num) {
			case 1:
				updateteReview();
				validInput = true;
				break;
			case 2:
				deleteReview();
				validInput = true;
				break;
			case 3:
				validInput = true;
				break;
			default:
				System.out.println("\t잘못 입력 하셨습니다. 다시 입력해주세요.");
			}

		}

	}

	public void deleteReview() {

		System.out.print("\t삭제를 원하는 리뷰 번호를 입력하세요 => ");
		int id = sc.nextInt();
		sc.nextLine();

		System.out.print("\t리뷰를 삭제 하시겠습니까? (y/n) => ");

		String yesOrNo = sc.nextLine();
		if (yesOrNo.equals("y")) {
			rsi.deleteReviews(id);

		} else if (yesOrNo.equals("n")) {
			return;
		} else {
			System.out.println("\t잘못 입력하셨습니다.");
		}
	}


	public void updateteReview() {
		System.out.print("\t수정을 원하는 리뷰 번호를 입력하세요: ");
		int id = sc.nextInt();
		sc.nextLine();

		

			for (int i = 0; i < reviewsModelList.size(); i++) {
				ReviewsModel rm = reviewsModelList.get(i);
				if (id != reviewsModelList.get(i).getReviewId()) {
					System.out.println("존재하지 않는 리뷰입니다");

				} else {

					break;

				}

			}
		

		int rating;
		String comment;
		while (true) {
			System.out.print("\t평점을 입력하세요 (1-5): ");
			rating = sc.nextInt();
			sc.nextLine();

			if (rating >= 1 && rating <= 5) {
				break; // 올바른 범위의 숫자가 입력되면 반복 종료
			} else {
				System.out.println("\t잘못 입력하셨습니다. 1-5 사이의 숫자를 입력하세요.");
			}

		}

		while (true) {
			System.out.print("\t내용을 입력하세요 (5자 이내): ");
			comment = sc.nextLine();

			if (comment.length() <= 5) {
				break; // 입력된 내용이 50자 이내일 경우 반복 종료
			} else {
				System.out.println("\t5자 이내로 입력하세요.");
			}
		}

		rsi.updateReviews(id, rating, comment);
		return;

	}
}
