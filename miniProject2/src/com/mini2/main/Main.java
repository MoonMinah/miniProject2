// Main.java
package com.mini2.main;

import com.mini2.users.service.UsersService;
import com.mini2.orders.controller.OrdersController;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        ASCIIArt asciiArt = new ASCIIArt();
        UsersService usersService = new UsersService();
        OrdersController ordersController = new OrdersController();
        boolean isRunning = true;
        Scanner scan = new Scanner(System.in);
        
        asciiArt.ASCIIArt();

        while (isRunning) {
            if (usersService.isUserLoggedIn()) {
                // Main menu for logged-in users
                System.out.println("\n==================================================================================================");
                System.out.println("\t\t\t\t\t☕️ [카페 오더 앱] 메인 메뉴 ☕️");
                System.out.println("|\t1. 주문\t\t\t\t\t\t\t\t\t\t\t|");
                System.out.println("|\t2. 회원 정보 관리\t\t\t\t\t\t\t\t\t\t|");
                System.out.println("|\t3. 종료\t\t\t\t\t\t\t\t\t\t\t|");
                System.out.print("\t원하시는 번호를 선택하세요 => ");
                
                String choice = scan.nextLine();

                switch (choice) {
                    case "1":
                        ordersController.processOrder();
                        break;
                    case "2":
                        usersService.manageUserInfo();
                        break;
                    case "3":
                        System.out.println("\t종료합니다. 이용해 주셔서 감사합니다!");
                        isRunning = false;
                        break;
                    default:
                        System.out.println("\t⚠️ 유효하지 않은 선택입니다. 다시 선택해 주세요.");
                        break;
                }
            } else {
                // Login/registration menu for not logged-in users
                System.out.println("\n==================================================================================================");
                System.out.println("\t\t\t\t\t☕️ [카페 오더 앱] ☕️");
                System.out.println("|\t1. 회원가입\t\t\t\t\t\t\t\t\t\t|");
                System.out.println("|\t2. 로그인\t\t\t\t\t\t\t\t\t\t\t|");
                System.out.println("|\t3. 종료\t\t\t\t\t\t\t\t\t\t\t|");
                System.out.print("\t원하시는 번호를 선택하세요 => ");

                String choice = scan.nextLine();

                switch (choice) {
                    case "1":
                        usersService.registerUser();
                        break;
                    case "2":
                        usersService.loginUser();
                        break;
                    case "3":
                        System.out.println("\t종료합니다. 이용해 주셔서 감사합니다!");
                        isRunning = false;
                        break;
                    default:
                        System.out.println("\t⚠️ 유효하지 않은 선택입니다. 다시 선택해 주세요.");
                        break;
                }
            }
        }
    }
}
