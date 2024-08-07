package com.mini2.payments.controller;

import java.util.List;
import java.util.Map;
import java.util.Scanner;

import com.mini2.menuDetail.model.MenuDetailModel;
import com.mini2.menuitems.model.MenuitemsModel;
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

                List<MenuDetailModel> menuDetails = paymentService.getMenuDetailsByOrderId(payment.getOrderId());
                if (menuDetails == null || menuDetails.isEmpty()) {
                    System.out.println("\t\t메뉴 상세 정보를 불러올 수 없습니다.");
                } else {
                    int totalAmount = payment.getTotalAmount(); // 총 결제 금액
                    int finalAmount = payment.getOrderAmount(); // 실제 결제 금액
                    int pointsUsed = -1 * (finalAmount - totalAmount); // 포인트 사용량

                    for (MenuDetailModel menuDetail : menuDetails) {
                        MenuitemsModel menuItem = paymentService.getMenuItemById(menuDetail.getItem_id());
                        if (menuItem != null) {
                            int itemTotal = menuItem.getPrice() * menuDetail.getQuantity();
                            System.out.printf("\t\t%s                            %d               %d%n",
                                    menuItem.getMenuName(), menuDetail.getQuantity(), itemTotal);
                        }
                    }
                    System.out.println("\t\t-------------------------------------------------------");
                    System.out.printf("\t\t                                       포인트 사용량: %d%n", pointsUsed);
                    System.out.println("\t\t-------------------------------------------------------");

                    System.out.println("\t\t-------------------------------------------------------");
                    System.out.printf("\t\t                                              금액: %d%n", totalAmount);
                    System.out.println("\t\t-------------------------------------------------------");
                }

                // 리뷰가 이미 작성된 경우 표시
                boolean reviewExists = paymentService.checkReviewExists(payment.getPaymentId());
                if (reviewExists) {
                    System.out.println("\t\t리뷰가 이미 작성된 결제입니다.");
                } else {
                    System.out.println("\t\t - " + (i + 1) + "번 리뷰하기");
                }

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

                    // 리뷰가 이미 작성된 경우 확인
                    boolean reviewExists = paymentService.checkReviewExists(paymentId);
                    if (reviewExists) {
                        System.out.println("\t⚠️ 이 결제에 대해서는 이미 리뷰가 작성되었습니다.");
                    } else {
                        // 리뷰 작성 처리
                        ReviewsController reviewController = new ReviewsController();
                        reviewController.writeReview(paymentId);
                    }
                } else {
                    System.out.println("\t⚠️ 잘못된 선택입니다. 다시 시도해 주세요.");
                }
            }
        } else {
            System.out.println("\t⚠️ 결제 정보를 불러올 수 없습니다.");
        }
    }
}