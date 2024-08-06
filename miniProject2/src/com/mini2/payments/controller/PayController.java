package com.mini2.payments.controller;

import com.mini2.payments.model.PaymentsModel;
import com.mini2.payments.service.PaymentsServiceImpl;
import com.mini2.menuitems.model.MenuitemsModel;
import com.mini2.orders.controller.OrdersController;

import java.util.List;
import java.util.Scanner;

public class PayController {

    public void pay(int orderId, int userId, int paymentMethod, int orderAmount, List<MenuitemsModel> menuItems,
                    List<Integer> quantities) {
        PaymentsServiceImpl paymentService = new PaymentsServiceImpl();

        // 새로운 결제 항목 생성
        PaymentsModel newPayment = new PaymentsModel();
        newPayment.setOrderId(orderId);
        newPayment.setUserId(userId);
        newPayment.setPaymentMethod(paymentMethod);
        newPayment.setOrderAmount(orderAmount);
        newPayment.setTotalAmount(orderAmount);
        newPayment.setPaymentStatus(false); // 초기 결제 상태는 미완료로 설정

        boolean isPaymentCreated = paymentService.createPayment(newPayment);

        if (isPaymentCreated) {
            PaymentsModel payment = paymentService.getPaymentByOrderId(orderId);

            System.out.println("\n\t\t\t\t🛒 [결제 정보] 🛒");
            System.out.println("\n==================================================================================================");

            if (payment != null) {
                System.out.printf("\t결제 번호: %d%n", payment.getPaymentId());
                System.out.printf("\t결제 날짜: %s%n", payment.getPaymentDate());
                System.out.println("\t\t--------------------------------------------------------");
                System.out.println("\t\t제품명                               수량             금액");
                System.out.println("\t\t--------------------------------------------------------");

                // 총 금액 계산
                int totalAmount = 0;

                // 실제 제품명과 수량, 금액 출력
                for (int i = 0; i < menuItems.size(); i++) {
                    MenuitemsModel item = menuItems.get(i);
                    int quantity = quantities.get(i);
                    int price = item.getPrice();
                    int itemTotal = price * quantity;
                    totalAmount += itemTotal;
                    System.out.printf("\t\t%-26s %8d %17d%n", item.getMenuName(), quantity, itemTotal);
                }

                // 총 금액 설정
                payment.setTotalAmount(totalAmount);

                System.out.println("\t\t--------------------------------------------------------");
                System.out.printf("\t\t                                              금액: %d%n", payment.getTotalAmount());
                System.out.println("\t\t--------------------------------------------------------");

                Scanner sc = new Scanner(System.in);

                while (true) {
                    try {
                        System.out.println("\t1.결제하기");
                        System.out.println("\t2.결제취소");
                        System.out.print("\t정말 결제하시겠습니까? => ");
                        
                        String menu = sc.nextLine();

                        switch (menu) {
                            case "1":
                                System.out.println("\t결제하기");
                                boolean isUpdated = paymentService.updatePaymentStatus(payment.getPaymentId(), totalAmount);
                                if (isUpdated) {
                                    System.out.println("\t결제가 완료되었습니다.");
                                } else {
                                    System.out.println("\t결제에 실패했습니다.");
                                }
                                return; // while 루프를 벗어나기 위해 return
                            case "2":
                                System.out.println("\t결제취소");
                                boolean isDeleted = paymentService.deletePayment(payment.getPaymentId());
                                if (isDeleted) {
                                    System.out.println("\t결제가 취소되었습니다.");
                                } else {
                                    System.out.println("\t결제 취소에 실패했습니다.");
                                }
                                return; // while 루프를 벗어나기 위해 return
                            default:
                                System.out.println("\n\t1 ~ 2번 중 선택해 주세요.");
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            } else {
                System.out.println("\t결제 정보를 불러올 수 없습니다.");
            }
        } else {
            System.out.println("\t결제 생성에 실패했습니다.");
        }
    }
}