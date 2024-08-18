package com.mini2.payments.controller;

import com.mini2.payments.model.PaymentsModel;
import com.mini2.payments.service.PaymentsServiceImpl;
import com.mini2.event.UsersPointDao;
import com.mini2.menuitems.model.MenuitemsModel;
import com.mini2.orders.service.OrderServiceImpl;

import java.util.List;
import java.util.Scanner;

public class PayController {

    private PaymentsServiceImpl paymentService = new PaymentsServiceImpl();
    private UsersPointDao usersPointDao = new UsersPointDao();
    private Scanner sc = new Scanner(System.in);

    public void pay(int orderId, int userId, int paymentMethod, int orderAmount,
                    List<MenuitemsModel> menuItems, List<Integer> quantities) {

        PaymentsModel newPayment = createNewPayment(orderId, userId, paymentMethod, orderAmount);
        if (!paymentService.createPayment(newPayment)) {
            System.out.println("\t결제 생성에 실패했습니다.");
            return;
        }

        PaymentsModel payment = paymentService.getPaymentByOrderId(orderId);
        if (payment == null) {
            System.out.println("\t결제 정보를 불러올 수 없습니다.");
            return;
        }

        displayOrderSummary(payment, menuItems, quantities);

        int point = usersPointDao.getUserPoints(userId); // 유저의 보유한 포인트
        int usePointAmount = 0; // 사용하는 포인트 양
        while (true) {
            String pointCheckOption = getPointCheckOption();
            if ("1".equals(pointCheckOption)) {
                usePointAmount = handlePointPayment(point);
            } else if ("2".equals(pointCheckOption)) {
                // 현금 결제 선택 시 특별한 로직 없음
            } else {
                System.out.println("\t올바른 옵션을 선택해 주세요.");
                continue;
            }

            if (processPayment(payment, totalAmount(payment, usePointAmount), usePointAmount, userId, orderId)) {
                return;
            }
        }
    }

    private PaymentsModel createNewPayment(int orderId, int userId, int paymentMethod, int orderAmount) {
        PaymentsModel payment = new PaymentsModel();
        payment.setOrderId(orderId);
        payment.setUserId(userId);
        payment.setPaymentMethod(paymentMethod);
        payment.setOrderAmount(orderAmount);
        payment.setTotalAmount(orderAmount);
        payment.setPaymentStatus(false);
        return payment;
    }

    private void displayOrderSummary(PaymentsModel payment, List<MenuitemsModel> menuItems, List<Integer> quantities) {
        System.out.println("\n==================================================================================================");
        System.out.println("\n\t\t\t\t🛒 [주문 정보] 🛒");
        System.out.printf("\t주문 번호: %d%n", payment.getOrderId());
        System.out.printf("\t주문 날짜: %s%n", payment.getPaymentDate());
        System.out.println("\t\t-------------------------------------------------------");
        System.out.println("\t\t제품명                               수량             금액");
        System.out.println("\t\t-------------------------------------------------------");

        int totalAmount = 0;
        for (int i = 0; i < menuItems.size(); i++) {
            MenuitemsModel item = menuItems.get(i);
            int quantity = quantities.get(i);
            int itemTotal = item.getPrice() * quantity;
            totalAmount += itemTotal;
            System.out.printf("\t\t%-26s %8d %17d%n", item.getMenuName(), quantity, itemTotal);
        }

        payment.setTotalAmount(totalAmount);
        System.out.println("\t\t--------------------------------------------------------");
        System.out.printf("\t\t                                              금액: %d%n", totalAmount);
        System.out.println("\t\t--------------------------------------------------------");
    }

    private String getPointCheckOption() {
        System.out.println("\t1. 포인트 결제진행");
        System.out.println("\t2. 현금 결제진행");
        System.out.print("\t옵션을 선택해주세요 => ");
        return sc.nextLine();
    }

    private int handlePointPayment(int point) {
        int usePointAmount = 0;
        System.out.printf("\t현재 보유하고 있는 포인트는 %d원 입니다.\n", point);
        System.out.println("\t포인트 사용은 최소 1000원부터 가능합니다.");
        System.out.printf("\t사용하시겠습니까? (y/n) => ");
        String usePoint = sc.nextLine();

        if (usePoint.equalsIgnoreCase("y") && point >= 1000) {
            usePointAmount = getValidPointAmount(point);
        } else if (point < 1000) {
            System.out.println("\t⚠️ 보유 포인트가 1000원 미만입니다.");
        }
        return usePointAmount;
    }

    private int getValidPointAmount(int point) {
        int usePointAmount;
        while (true) {
            System.out.print("\t사용하실 금액을 적어주세요 => ");
            usePointAmount = sc.nextInt();
            sc.nextLine(); // 남아있는 개행 문자를 소비

            if (usePointAmount < 1000) {
                System.out.println("\t1000원 미만은 사용하실 수 없습니다.");
            } else if (usePointAmount > point) {
                System.out.println("\t보유 포인트보다 많은 금액은 사용할 수 없습니다.");
            } else {
                break;
            }
        }
        return usePointAmount;
    }

    private boolean processPayment(PaymentsModel payment, int totalMoney, int usePointAmount, int userId, int orderId) {
        System.out.println("\n\t1. 결제하기");
        System.out.println("\t2. 결제취소");
        System.out.print("\t옵션을 선택해주세요 => ");

        String menu = sc.nextLine();
        switch (menu) {
            case "1":
                return handlePaymentConfirmation(payment, totalMoney, usePointAmount, userId, orderId);
            case "2":
                return handlePaymentCancellation(payment, orderId);
            default:
                System.out.println("\n\t1 ~ 2번 중 선택해 주세요.");
                return false;
        }
    }

    private boolean handlePaymentConfirmation(PaymentsModel payment, int totalMoney, int usePointAmount, int userId, int orderId) {
        boolean isUpdated = paymentService.updatePaymentStatus(payment.getPaymentId(), totalMoney);
        if (isUpdated) {
            usersPointDao.deductUserPoints(userId, usePointAmount);
            OrderServiceImpl orderServiceImpl = new OrderServiceImpl();
            boolean isOrderStatusUpdate = orderServiceImpl.updateOrderStatus(orderId);
            if (isOrderStatusUpdate) {
                System.out.println("\t💵 구매확정 되었습니다!");
            } else {
                System.out.println("\t주문 취소에 실패했습니다.");
            }
            System.out.println("\t💵 결제가 완료되었습니다.");
            return true;
        } else {
            System.out.println("\t결제에 실패했습니다.");
            return false;
        }
    }

    private boolean handlePaymentCancellation(PaymentsModel payment, int orderId) {
        boolean isDeleted = paymentService.deletePayment(payment.getPaymentId());
        if (isDeleted) {
            System.out.println("\t결제가 취소되었습니다.");
            OrderServiceImpl orderServiceImpl = new OrderServiceImpl();
            boolean isMenuDeleted = orderServiceImpl.deleteMenuDetail(orderId);
            boolean isOrderDeleted = orderServiceImpl.deleteOrder(orderId);
            if (isMenuDeleted && isOrderDeleted) {
                System.out.println("\t주문이 취소되었습니다.");
            } else {
                System.out.println("\t주문 취소에 실패했습니다.");
            }
            return true;
        } else {
            System.out.println("\t결제 취소에 실패했습니다.");
            return false;
        }
    }

    private int totalAmount(PaymentsModel payment, int usePointAmount) {
        return payment.getTotalAmount() - usePointAmount;
    }
}