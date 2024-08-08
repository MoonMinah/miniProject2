package com.mini2.payments.controller;

import com.mini2.payments.model.PaymentsModel;
import com.mini2.payments.service.PaymentsServiceImpl;
import com.mini2.event.UsersPointDao;
import com.mini2.menuitems.model.MenuitemsModel;
import com.mini2.orders.service.OrderServiceImpl;
import java.util.List;
import java.util.Scanner;

public class PayController {

  public void pay(int orderId, int userId, int paymentMethod, int orderAmount,
      List<MenuitemsModel> menuItems, List<Integer> quantities) {
    PaymentsServiceImpl paymentService = new PaymentsServiceImpl();
    UsersPointDao usersPointDao = new UsersPointDao();

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

      if (payment != null) {
        System.out.println(
            "\n==================================================================================================");
        System.out.println("\n\t\t\t\t🛒 [주문 정보] 🛒");

        if (payment != null) {
          System.out.printf("\t주문 번호: %d%n", orderId);
          System.out.printf("\t주문 날짜: %s%n", payment.getPaymentDate());
          System.out.println("\t\t-------------------------------------------------------");
          System.out.println("\t\t제품명                               수량             금액");
          System.out.println("\t\t-------------------------------------------------------");

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
          int totalMoney = payment.getTotalAmount();

          System.out.println("\t\t--------------------------------------------------------");
          System.out.printf("\t\t                                              금액: %d%n",
              totalMoney);
          System.out.println("\t\t--------------------------------------------------------");

          Scanner sc = new Scanner(System.in);

          int point = usersPointDao.getUserPoints(userId); // 유저의 보유한 포인트
          int usePointAmount = 0; // 사용하는 포인트 양
          while (true) {
            try {
              System.out.println("\t1. 포인트 결제진행");
              System.out.println("\t2. 현금 결제진행");
              System.out.print("\t옵션을 선택해주세요 => ");
              String pointcheck = sc.nextLine();

              switch (pointcheck) {
                case "1":
                  System.out.printf("\t현재 보유하고 있는 포인트는 %d원 입니다.\n", point);
                  System.out.println("\t포인트 사용은 최소 1000원부터 가능합니다.");
                  System.out.printf("\t사용하시겠습니까? (y/n) => ");
                  String usePoint = sc.nextLine();

                  if (usePoint.equalsIgnoreCase("y")) { // 포인트를 사용하는 경우
                    if (point >= 1000) { // 보유 포인트가 1000원이 넘어가는 경우
                      boolean validInput = false;
                      while (!validInput) {
                        System.out.print("\t사용하실 금액을 적어주세요 => ");
                        usePointAmount = sc.nextInt();
                        sc.nextLine(); // 남아있는 개행 문자를 소비

                        if (usePointAmount < 1000) {
                          System.out.println("\t1000원 미만은 사용하실 수 없습니다."); // 1000포인트 미만일 경우 예외처리
                        } else if (usePointAmount > point) {
                          System.out.println("\t보유 포인트보다 많은 금액은 사용할 수 없습니다."); // 보유 포인트 초과시 예외처리
                        } else {
                          totalMoney -= usePointAmount;
                          validInput = true; // 유효한 입력이 들어왔으므로 반복 종료
                        }
                      }
                    } else { // 보유 포인트가 1000 미만일 경우
                      System.out.println("\t⚠️ 보유 포인트가 1000원 미만입니다.");
                    }
                  }

                  break;
                case "2":
                  break;
                default:
                  System.out.println("\t올바른 옵션을 선택해 주세요.");
                  continue; // 다시 옵션을 선택하도록 루프 계속
              }

              System.out.println("\n\t1. 결제하기");
              System.out.println("\t2. 결제취소");
              System.out.print("\t옵션을 선택해주세요 => ");

              String menu = sc.nextLine();

              switch (menu) {
                case "1":
                  boolean isUpdated =
                      paymentService.updatePaymentStatus(payment.getPaymentId(), totalMoney);
                  if (isUpdated) {
                    usersPointDao.deductUserPoints(userId, usePointAmount); // 사용한 만큼 포인트 차감
                    OrderServiceImpl orderServiceImpl = new OrderServiceImpl();
                    boolean isOrderStatusUpdate = orderServiceImpl.updateOrderStatus(orderId); // 구매
                                                                                               // 확정
                    if (isOrderStatusUpdate) {
                      System.out.println("\t💵 구매확정 되었습니다!");
                    } else {
                      System.out.println("\t주문 취소에 실패했습니다.");
                    }

                    System.out.println("\t💵 결제가 완료되었습니다.");
                  } else {
                    System.out.println("\t결제에 실패했습니다.");
                  }
                  return; // while 루프를 벗어나기 위해 return
                case "2":
                  boolean isDeleted = paymentService.deletePayment(payment.getPaymentId());
                  if (isDeleted) {
                    System.out.println("\t결제가 취소되었습니다.");
                    OrderServiceImpl orderServiceImpl = new OrderServiceImpl();
                    // 외래키 참조로 인한 메뉴 상세 테이블 삭제
                    boolean isMenuDeleted = orderServiceImpl.deleteMenuDetail(orderId);
                    boolean isOrderDeleted = orderServiceImpl.deleteOrder(orderId); // 주문 테이블 삭제
                    if (isMenuDeleted && isOrderDeleted) {
                      System.out.println("\t주문이 취소되었습니다.");
                    } else {
                      System.out.println("\t주문 취소에 실패했습니다.");
                    }
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
}