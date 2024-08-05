package com.mini2.payments.controller;

import com.mini2.payments.model.PaymentsModel;
import com.mini2.payments.service.PaymentsServiceImpl;

public class PayController {

	public void pay(int orderId, int userId, int paymentMethod, int orderAmount) {
	    PaymentsServiceImpl paymentService = new PaymentsServiceImpl();
	    
	    // 새로운 결제 항목 생성
	    PaymentsModel newPayment = new PaymentsModel();
	    newPayment.setOrderId(orderId);
	    newPayment.setUserId(userId);
	    newPayment.setPaymentMethod(paymentMethod);
	    newPayment.setOrderAmount(orderAmount);
	    newPayment.setTotalAmount(orderAmount); // 수정된 부분
	    newPayment.setPaymentStatus(false); // 초기 결제 상태는 미완료로 설정

	    boolean isPaymentCreated = paymentService.createPayment(newPayment);

	    if (isPaymentCreated) {
	        PaymentsModel payment = paymentService.getPaymentByOrderId(orderId);

	        System.out.println("[결제 정보]");
	        System.out.println("======================================================");

	        if (payment != null) {
	            System.out.printf("결제 번호: %d%n", payment.getPaymentId());
	            System.out.printf("결제 날짜: %s%n", payment.getPaymentDate());
	            System.out.println("-------------------------------------------------------");
	            System.out.println("제품명                               수량             금액");
	            System.out.println("-------------------------------------------------------");

	            // 예시로 제품명과 수량, 금액 출력
	            System.out.println("예시 제품명                           1               1000");
	            System.out.println("예시 제품명2                          2               2000");
	            System.out.println("-------------------------------------------------------");
	            System.out.printf("                                              금액: %d%n", payment.getTotalAmount());
	            System.out.println("-------------------------------------------------------");
	        } else {
	            System.out.println("결제 정보를 불러올 수 없습니다.");
	        }
	    } else {
	        System.out.println("결제 생성에 실패했습니다.");
	    }
	}
}