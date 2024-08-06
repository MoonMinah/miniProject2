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
		PaymentsServiceImpl paymentsServiceImpl = new PaymentsServiceImpl();
		UsersController userController = UsersController.getInstance();
		Loading loading = new Loading();
		List<CategoryModel> categoryList = orderService.categoryAll();

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
				if (categoryList != null && !categoryList.isEmpty()) {
					for (CategoryModel category : categoryList) {
						System.out.printf("\t%d. %s \t| ", category.getCategoryId(), category.getCategoryName());
					}
				} else {
					System.out.println("\t⚠️ 카테고리를 불러올 수 없습니다.");
					return;
				}

				System.out.println();
				System.out.println(
						"==================================================================================================");
				System.out.print("\t카테고리를 입력해주세요 => ");
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
					return;
				}
				System.out.println(
						"==================================================================================================");
				System.out.print("\t메뉴를 입력해주세요 => ");
				String menuName = scan.nextLine();
				System.out.print("\t수량을 입력해주세요 => ");
				int quantity = scan.nextInt();
				scan.nextLine(); // 개행 문자 처리

				MenuitemsModel selectedItem = menuList.stream().filter(item -> item.getMenuName().equals(menuName))
						.findFirst().orElse(null);

				if (selectedItem != null) {
					menuItems.add(selectedItem);
					quantities.add(quantity);
				} else {
					System.out.println("\t⚠️잘못된 메뉴 선택입니다.");
					return;
				}
				System.out.println("\t담으신 메뉴 : " + selectedItem.getMenuName() + ", 수량: " + quantity);
				System.out.print("\t추가 주문하시겠습니까? (y/n) => ");
				String addOrder = scan.nextLine();
				if (addOrder.equalsIgnoreCase("n")) {
					addMoreOrders = false;
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