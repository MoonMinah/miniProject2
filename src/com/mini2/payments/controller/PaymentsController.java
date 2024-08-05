package com.mini2.payments.controller;

import java.util.List;
import java.util.Scanner;
import com.mini2.payments.model.PaymentsModel;
import com.mini2.payments.service.PaymentsServiceImpl;
import com.mini2.reviews.controller.ReviewsController;

public class PaymentsController {
    public static void main(String[] args) {
        // 로그인한 사용자 ID를 여기에 설정 (예: 1번 사용자)
        int loggedInUserId = 1;

        PaymentsServiceImpl paymentService = new PaymentsServiceImpl();
        List<PaymentsModel> payments = paymentService.getPaymentsByUserId(loggedInUserId);

        System.out.println("[결제 목록]");

        if (payments != null && !payments.isEmpty()) {
            Scanner sc = new Scanner(System.in);

            // 결제 항목에 번호 매기기
            for (int i = 0; i < payments.size(); i++) {
                System.out.println("======================================================");
                PaymentsModel payment = payments.get(i);
                System.out.printf("%d. 결제 번호: %d%n", i + 1, payment.getPaymentId());
                System.out.printf("   결제 날짜: %s%n", payment.getPaymentDate());
                System.out.println("-------------------------------------------------------");
                System.out.println("제품명                               수량             금액");
                System.out.println("-------------------------------------------------------");

                // 여기에 각 결제 항목에 대한 제품 정보를 추가해야 합니다.
                // 예를 들어, 해당 결제 ID에 대한 제품 목록을 가져와서 출력합니다.
                // 현재는 예시로 제품명을 출력합니다.
                System.out.println("예시 제품명                           1               1000");
                System.out.println("예시 제품명2                          2               2000");
                System.out.println("-------------------------------------------------------");
                System.out.printf("                                              금액: %d%n", payment.getTotalAmount());
                System.out.println("-------------------------------------------------------");
                System.out.println(i + 1 + "번 리뷰하기");
                System.out.println();
                System.out.println();
            }

            System.out.print("리뷰를 작성할 결제 번호를 입력하세요 (0을 입력하면 종료): ");
            int choice = sc.nextInt();
            sc.nextLine();

            while (choice != 0 && choice <= payments.size()) {
                PaymentsModel selectedPayment = payments.get(choice - 1);
                System.out.printf("선택한 결제 ID: %d%n", selectedPayment.getPaymentId());

                // 리뷰 작성 처리
//                ReviewsController reviewsController = new ReviewsController();
//                reviewsController.handleReviewCreation(selectedPayment.getPaymentId());

                // 다시 번호를 입력받기
                System.out.print("다른 결제 항목에 리뷰를 작성하시겠습니까? (0을 입력하면 종료): ");
                choice = sc.nextInt();
                sc.nextLine();
            }

            sc.close();
        } else {
            System.out.println("결제 정보를 불러올 수 없습니다.");
        }
    }
}