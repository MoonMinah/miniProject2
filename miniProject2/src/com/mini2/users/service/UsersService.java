// UsersService.java
package com.mini2.users.service;

import com.mini2.users.controller.UsersController;
import com.mini2.event.UserPoints;
import com.mini2.payments.controller.PaymentsController;

import java.util.Scanner;

public class UsersService {
    private UsersController usersController = UsersController.getInstance();
    private UserPoints userPoints = new UserPoints();
    private PaymentsController paymentController = new PaymentsController();
    private Scanner sc = new Scanner(System.in);

    public boolean isUserLoggedIn() {
        return usersController.getSession().containsKey("user_id");
    }

    public void registerUser() {
        usersController.registerUser();
    }

    public void loginUser() {
        usersController.loginUser();
    }

    public void manageUserInfo() {
        boolean inUserInfoMenu = true;

        while (inUserInfoMenu && isUserLoggedIn()) {
            System.out.println("\n\t\t\t\t\t📒 [내 정보 보기] 📒");
            System.out.println("\n==================================================================================================");
            System.out.println("|\t1. 회원 정보 수정\t\t\t\t\t\t\t\t\t\t|");
            System.out.println("|\t2. 내 결제 목록\t\t\t\t\t\t\t\t\t\t|");
            System.out.println("|\t3. 내 리뷰 목록\t\t\t\t\t\t\t\t\t\t|");
            System.out.println("|\t4. 보유 포인트 현황\t\t\t\t\t\t\t\t\t\t|");
            System.out.println("|\t5. 로그아웃\t\t\t\t\t\t\t\t\t\t|");
            System.out.println("|\t6. 회원 탈퇴\t\t\t\t\t\t\t\t\t\t|");
            System.out.println("|\t7. 뒤로가기\t\t\t\t\t\t\t\t\t\t|");
            System.out.print("\t원하시는 번호를 선택하세요 => ");
            
            int choice = sc.nextInt();
            sc.nextLine(); // Clear input buffer

            switch (choice) {
                case 1:
                    usersController.updateUserInfo();
                    break;
                case 2:
                	paymentController.paymentList();
                	break;
                case 3:
                	break;
                case 4:
                    Integer userId = usersController.getSession().get("user_id");
                    userPoints.showUserPoints(userId);
                    break;
                case 5:
                    System.out.println("\t로그아웃 되었습니다.");
                    usersController.getSession().clear();
                    inUserInfoMenu = false;
                    break;
                case 6:
                    System.out.println("\t회원 탈퇴가 완료되었습니다. 로그아웃합니다.");
                    usersController.deleteUser();
                    inUserInfoMenu = false;
                    break;
                case 7:
                    inUserInfoMenu = false;
                    break;
                default:
                    System.out.println("\t⚠️ 유효하지 않은 선택입니다. 다시 선택해 주세요.");
                    break;
            }
        }
    }
}
