package com.mini2.orders.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import com.mini2.category.model.CategoryModel;
import com.mini2.main.Loading;
import com.mini2.menuitems.model.MenuitemsModel;
import com.mini2.orders.service.OrderServiceImpl;
import com.mini2.payments.controller.PayController;
import com.mini2.payments.service.PaymentsServiceImpl;
import com.mini2.users.controller.UsersController;

public class OrdersController {

	public void processOrder() {
		Scanner scan = new Scanner(System.in);
		OrderServiceImpl orderService = new OrderServiceImpl();
		UsersController userController = UsersController.getInstance();
		Loading loading = new Loading();
		List<CategoryModel> categoryMenu = orderService.categoryMenu();

		Map<String, Integer> session = userController.getSession();
		Integer userId = session.get("user_id"); // Integer 객체로 받아오기
		if (userId == null) {
			System.out.println("\t사용자가 로그인되지 않았습니다.");
			return; // 또는 예외 처리
		}

		try {
			boolean addMoreOrders = true;
			List<MenuitemsModel> menuItems = new ArrayList<>();
			List<Integer> quantities = new ArrayList<>();

			while (addMoreOrders) {
				System.out.println("\n\t\t\t\t\t[카테고리 목록]");
				System.out.println(
						"==================================================================================================");
				if (categoryMenu != null && !categoryMenu.isEmpty()) {
					for (CategoryModel category : categoryMenu) {
						System.out.printf("\t%d. %s \t| ", category.getCategoryId(), category.getCategoryName());
					}
				} else {
					System.out.println("\t⚠️ 카테고리를 불러올 수 없습니다.");
					return;
				}

				System.out.println();
				System.out.println(
						"==================================================================================================");
				System.out.print("\t카테고리 이름을 입력해주세요 (번호x) => ");
				String categoryByName = scan.nextLine();
				List<MenuitemsModel> menuList = orderService.getMenuByCategory(categoryByName);

				System.out.println("|\t\t\t\t\t☕️ [메뉴 목록] ☕️\t\t\t\t\t\t|");
				System.out.println(
						"==================================================================================================");
				if (menuList != null && !menuList.isEmpty()) {
					for (MenuitemsModel menu : menuList) {
						System.out.printf("\t%d. %s \t| %d\n", menu.getItemId(), menu.getMenuName(), menu.getPrice());
					}
				} else {
					System.out.println("\t⚠️ 메뉴를 불러올 수 없습니다.");
					System.out.println("\t⚠️ 번호가 아닌 메뉴를 입력해주세요!!");
					return;
				}
				System.out.println(
						"==================================================================================================");
				System.out.print("\t메뉴를 입력해주세요 (번호x) => ");
				String menuName = scan.nextLine();
				System.out.print("\t수량을 입력해주세요 (1이상) => ");
				int quantity = scan.nextInt();
				scan.nextLine(); // 개행 문자 처리

			
				if(quantity <= 0) {
				  System.out.println("\t⚠️수량은 1 이상이여야 합니다!");
				  System.out.println("\t⚠️ 다시 처음으로 돌아갑니다!");
				  addMoreOrders = true;
				}
				
				else {
				  MenuitemsModel selectedItem = menuList.stream().filter(item -> item.getMenuName().equals(menuName))
                      .findFirst().orElse(null);

              if (selectedItem != null) {
                  menuItems.add(selectedItem);
                  quantities.add(quantity);
              } else {
                  System.out.println("\t⚠️잘못된 메뉴 선택입니다.");
                  System.out.println("\t⚠️메뉴 : " + menuName + ", 수량 : " + quantity);
                  System.out.println("\t⚠️메뉴이름 및 수량을 정확히 입력해주세요!");
                  return;
              }
              System.out.println("\t담으신 메뉴 : " + selectedItem.getMenuName() + ", 수량: " + quantity);
              System.out.print("\t추가 주문하시겠습니까? (y/n) => ");
              String addOrder = scan.nextLine();
              if (addOrder.equalsIgnoreCase("n") || addOrder.equalsIgnoreCase("아니오") || addOrder.equalsIgnoreCase("N")) {
                  addMoreOrders = false;
              }else if(addOrder.equalsIgnoreCase("y") || addOrder.equalsIgnoreCase("예") || addOrder.equalsIgnoreCase("Y")) {
                System.out.println("\t추가 주문을 선택하셨습니다!");
                System.out.println("\t추가 주문을 위해 메뉴를 선택해주세요!");
              }
              else {
                System.out.println("\t⚠️잘못 입력하셨습니다!");
                System.out.println("\t⚠️처음부터 다시 입력해주세요!");
                return;
              }
             
				}
				
				
			
			}

			// 주문 처리
			int paymentMethod = 1;
			int orderAmount = 3000; // 실제로는 총 주문 금액 계산 로직 필요
			int orderId = orderService.placeOrder(userId, menuItems, quantities);
			if (orderId != 0) {
				loading.run();
				PayController controller = new PayController();
				controller.pay(orderId, userId, paymentMethod, orderAmount, menuItems, quantities);
			} else {
				System.out.println("\t⚠️주문 처리에 실패했습니다.");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}