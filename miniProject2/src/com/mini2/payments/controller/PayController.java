package com.mini2.payments.controller;

import com.mini2.payments.model.PaymentsModel;
import com.mini2.payments.service.PaymentsServiceImpl;
import com.mini2.menuitems.model.MenuitemsModel;
import java.util.List;

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

				// 실제 제품명과 수량, 금액 출력
				for (int i = 0; i < menuItems.size(); i++) {
					MenuitemsModel item = menuItems.get(i);
					int quantity = quantities.get(i);
					int price = item.getPrice();
					System.out.printf("%-35s %10d %15d%n", item.getMenuName(), quantity, price * quantity);
				}

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