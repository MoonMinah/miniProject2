package com.mini2.payments.controller;

import com.mini2.payments.model.PaymentsModel;
import com.mini2.payments.service.PaymentsServiceImpl;

public class PayController {
    public static void main(String[] args) {
        // 조회할 orderId를 설정 (예: 1)
        int orderId = 1;
        
        PaymentsServiceImpl paymentService = new PaymentsServiceImpl();
        PaymentsModel payment = paymentService.getPaymentByOrderId(orderId);

        System.out.println("[결제 정보]");
        System.out.println("-------------------------------------------------------");

        if (payment != null) {
            System.out.printf("결제 ID: %d, 주문 ID: %d, 결제 방법: %d, 결제 날짜: %s, 금액: %d, 결제 상태: %s%n", 
                              payment.getPaymentId(), payment.getOrderId(), payment.getPaymentMethod(), 
                              payment.getPaymentDate(), payment.getAmount(), 
                              payment.isPaymentStatus() ? "완료" : "미완료");
        } else {
            System.out.println("결제 정보를 불러올 수 없습니다.");
        }
    }
}