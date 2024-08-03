package com.mini2.users.service;

import java.util.Scanner;

import com.mini2.event.UserPoints;
import com.mini2.users.controller.UsersController;

public class UsersService {
	UsersController usersController = new UsersController();
	UserPoints userPoints = new UserPoints();
	private boolean isRunning = true;
	private Scanner sc = new Scanner(System.in);
	
	public void start() {
		while (isRunning) {
			System.out.println("\n==================================================================================================");
			System.out.println("\t\t\t\t\t☕️ [카페 오더 앱] ☕️");
	        System.out.println("|\t1. 회원가입\t\t\t\t\t\t\t\t\t\t|");
	        System.out.println("|\t2. 로그인\t\t\t\t\t\t\t\t\t\t\t|");
	        System.out.println("|\t3. 종료\t\t\t\t\t\t\t\t\t\t\t|");
	        System.out.print("\t원하시는 번호를 선택하세요 => ");
			int choice = sc.nextInt();
			sc.nextLine(); // 입력 버퍼 비우기

			switch (choice) {
			case 1:
				usersController.registerUser();
				break;
			case 2:
				usersController.loginUser();
				// 메인화면 만들어서 후에는 메인화면으로 이동하게 만들기
				showMyInfo(); // 로그인 후 메뉴 표시
				break;
			case 3:
				System.out.println("\t종료합니다. 이용해 주셔서 감사합니다!");
				isRunning = false;
				break;
			default:
				System.out.println("\t⚠️ 유효하지 않은 선택입니다. 다시 선택해 주세요.");
				break;
			}
		}
	}

	//로그인 이후 메뉴
	private void showMyInfo() {
		boolean loggedIn = usersController.getSession().containsKey("user_id");
		while (loggedIn) {
			System.out.println("\n\t\t\t\t\t📒 [내 정보 보기] 📒");
			System.out.println("\n==================================================================================================");
	        System.out.println("|\t1. 회원 정보 수정\t\t\t\t\t\t\t\t\t\t|");
	        System.out.println("|\t2. 회원 탈퇴\t\t\t\t\t\t\t\t\t\t|");
	        System.out.println("|\t3. 보유 포인트 현황\t\t\t\t\t\t\t\t\t\t|");
	        System.out.println("|\t4. 로그아웃\t\t\t\t\t\t\t\t\t\t|");
	        System.out.println("|\t5. 종료\t\t\t\t\t\t\t\t\t\t\t|");
	        System.out.print("\t원하시는 번호를 선택하세요: ");
			int choice = sc.nextInt();
			sc.nextLine(); // 입력 버퍼 비우기

			switch (choice) {
			case 1:
				usersController.updateUserInfo();
				break;
			case 2:
				System.out.println("\t회원 탈퇴가 완료되었습니다. 로그아웃합니다.");
				usersController.deleteUser();
				loggedIn = false; // 탈퇴 후 로그아웃
				break;
			case 3:
				Integer userId = usersController.getSession().get("user_id");
				userPoints.UserPoints(userId);
				break;
			case 4:
				System.out.println("\t로그아웃 되었습니다.");
				usersController.getSession().clear(); // 로그아웃
				loggedIn = false;
				break;
			case 5:
				System.out.println("\t종료합니다. 이용해 주셔서 감사합니다!");
				isRunning = false;
				loggedIn = false;
				break;
			default:
				System.out.println("\t⚠️ 유효하지 않은 선택입니다. 다시 선택해 주세요.");
				break;
			}
		}
	}
}
