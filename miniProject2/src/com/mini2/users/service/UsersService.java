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
			System.out.println("번호 선택");
			System.out.println("1. 회원가입");
			System.out.println("2. 로그인");
			System.out.println("3. 종료");
			System.out.print("번호 선택 => ");
			int choice = sc.nextInt();
			sc.nextLine(); // 입력 버퍼 비우기

			switch (choice) {
			case 1:
				usersController.registerUser();
				break;
			case 2:
				usersController.loginUser();
				showPostLoginMenu(); // 로그인 후 메뉴 표시
				break;
			case 3:
				isRunning = false;
				break;
			default:
				System.out.println("유효하지 않은 선택입니다.");
				break;
			}
		}
	}

	//로그인 이후 메뉴
	private void showPostLoginMenu() {
		boolean loggedIn = usersController.getSession().containsKey("user_id");
		while (loggedIn) {
			System.out.println("번호 선택");
			System.out.println("1. 회원 정보 수정");
			System.out.println("2. 회원 탈퇴");
			System.out.println("3. 보유 포인트 현황");
			System.out.println("4. 로그아웃");
			System.out.println("5. 종료");
			System.out.print("번호 선택 => ");
			int choice = sc.nextInt();
			sc.nextLine(); // 입력 버퍼 비우기

			switch (choice) {
			case 1:
				usersController.updateUserInfo();
				break;
			case 2:
				usersController.deleteUser();
				loggedIn = false; // 탈퇴 후 로그아웃
				break;
			case 3:
				Integer userId = usersController.getSession().get("user_id");
				userPoints.UserPoints(userId);
				break;
			case 4:
				usersController.getSession().clear(); // 로그아웃
				loggedIn = false;
				break;
			case 5:
				isRunning = false;
				loggedIn = false;
				break;
			default:
				System.out.println("유효하지 않은 선택입니다.");
				break;
			}
		}
	}

	public static void main(String[] args) {
		UsersService service = new UsersService();
		service.start();
	}
}
