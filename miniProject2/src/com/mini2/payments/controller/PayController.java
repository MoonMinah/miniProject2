package com.mini2.payments.controller;

import com.mini2.payments.model.PaymentsModel;
import com.mini2.payments.service.PaymentsServiceImpl;

public class PayController {
    public void pay() {
        // 조회할 orderId를 설정 (예: 8)
        int orderId = 1;

        PaymentsServiceImpl paymentService = new PaymentsServiceImpl();
        PaymentsModel payment = paymentService.getPaymentByOrderId(orderId);

        System.out.println("[결제 정보]");
        System.out.println("-------------------------------------------------------");

        if (payment != null) {
            System.out.println("======================================================");
            System.out.printf("결제 번호: %d%n", payment.getPaymentId());
            System.out.printf("결제 날짜: %s%n", payment.getPaymentDate());
            System.out.println("-------------------------------------------------------");
            System.out.println("제품명                               수량             금액");
            System.out.println("-------------------------------------------------------");

            // 여기에 각 결제 항목에 대한 실제 제품 정보를 추가해야 합니다.
            // 예를 들어, 해당 결제 ID에 대한 제품 목록을 가져와서 출력합니다.
            // 현재는 예시로 제품명을 출력합니다.
            System.out.println("예시 제품명                           1               1000");
            System.out.println("예시 제품명2                          2               2000");
            System.out.println("-------------------------------------------------------");
            System.out.printf("                                              금액: %d%n", payment.getAmount());
            System.out.println("-------------------------------------------------------");
        } else {
            System.out.println("결제 정보를 불러올 수 없습니다.");
        }
    }

    public static void main(String[] args) {
        PayController controller = new PayController();
        controller.pay();
    }
}