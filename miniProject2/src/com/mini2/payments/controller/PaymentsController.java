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
        System.out.println("-------------------------------------------------------");

        if (payments != null && !payments.isEmpty()) {
            Scanner sc = new Scanner(System.in);
//            ReviewsController reviewsController = new ReviewsController();

            // 결제 항목에 번호 매기기
            for (int i = 0; i < payments.size(); i++) {
                PaymentsModel payment = payments.get(i);
                System.out.printf("%d. 결제 ID: %d, 주문 ID: %d, 결제 방법: %d, 결제 날짜: %s, 금액: %d, 결제 상태: %s%n", 
                                  i + 1, payment.getPaymentId(), payment.getOrderId(), payment.getPaymentMethod(), 
                                  payment.getPaymentDate(), payment.getAmount(), 
                                  payment.isPaymentStatus() ? "완료" : "미완료");
                System.out.println(i+1 +"번 리뷰하기");
                System.out.println("-------------------------------------------------------");
            }

            System.out.print("리뷰를 작성할 결제 항목의 번호를 입력하세요 (0을 입력하면 종료): ");
            int choice = sc.nextInt();
            sc.nextLine();  // Consume the newline

            while (choice != 0 && choice <= payments.size()) {
                PaymentsModel selectedPayment = payments.get(choice - 1);
                System.out.printf("선택한 결제 ID: %d%n", selectedPayment.getPaymentId());
                
                // 리뷰 작성 처리
//                reviewsController.handleReviewCreation(selectedPayment.getPaymentId());

                // 다시 번호를 입력받기
                System.out.print("다른 결제 항목에 리뷰를 작성하시겠습니까? (0을 입력하면 종료): ");
                choice = sc.nextInt();
                sc.nextLine(); 
            }

//            reviewsController.close();
            sc.close();
        } else {
            System.out.println("결제 정보를 불러올 수 없습니다.");
        }
    }
}