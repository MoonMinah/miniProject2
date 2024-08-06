package com.mini2.payments.controller;

import java.util.List;
import java.util.Map;
import java.util.Scanner;
import com.mini2.payments.model.PaymentsModel;
import com.mini2.payments.service.PaymentsServiceImpl;
import com.mini2.reviews.controller.ReviewsController;
import com.mini2.users.controller.UsersController;

public class PaymentsController {
	public void paymentList() {
		UsersController userController = UsersController.getInstance();

		Map<String, Integer> session = userController.getSession();
		Integer loggedInUserId = session.get("user_id"); // Integer 객체로 받아오기
		if (loggedInUserId == null) {
			System.out.println("\t사용자가 로그인되지 않았습니다.");
			return; // 또는 예외 처리
		}

		PaymentsServiceImpl paymentService = new PaymentsServiceImpl();
		List<PaymentsModel> payments = paymentService.getPaymentsByUserId(loggedInUserId);

		System.out.println("\n\t\t\t\t🛒 [결제 목록] 🛒");

		if (payments != null && !payments.isEmpty()) {
			Scanner sc = new Scanner(System.in);

			// 결제 항목에 번호 매기기
			for (int i = 0; i < payments.size(); i++) {
				System.out.println("\t\t======================================================");
				PaymentsModel payment = payments.get(i);
				System.out.printf("\t\t%d. 결제 번호: %d%n", i + 1, payment.getPaymentId());
				System.out.printf("\t\t   결제 날짜: %s%n", payment.getPaymentDate());
				System.out.println("\t\t-------------------------------------------------------");
				System.out.println("\t\t제품명                               수량             금액");
				System.out.println("\t\t-------------------------------------------------------");

				// 해당 결제 ID에 대한 제품 목록을 가져와서 출력합니다.
				System.out.println("\t\t예시 제품명                           1               1000");
				System.out.println("\t\t예시 제품명2                          2               2000");
				System.out.println("\t\t-------------------------------------------------------");
				System.out.printf("\t\t                                              금액: %d%n",
						payment.getTotalAmount());
				System.out.println("\t\t-------------------------------------------------------");
				System.out.println("\t\t - " + (i + 1) + "번 리뷰하기");
				System.out.println();
				System.out.println();
			}

			System.out.print("\t리뷰를 작성할 리뷰번호를 입력하세요 (0을 입력시 정보창으로 돌아갑니다) => ");
			int choice = sc.nextInt();
			sc.nextLine();

			if (choice == 0) {
				return;
			} else {
				if (choice <= payments.size()) {
					PaymentsModel selectedPayment = payments.get(choice - 1);
					int paymentId = selectedPayment.getPaymentId();
					System.out.printf("\t선택한 결제 ID: %d%n", paymentId);
					System.out.println();

					// 리뷰 작성 처리
					ReviewsController reviewController = new ReviewsController();
					reviewController.wirteReview(paymentId);
				}
			}
		} else {
			System.out.println("\t⚠️ 결제 정보를 불러올 수 없습니다.");
		}
	}

}