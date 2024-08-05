package com.mini2.users.controller;

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.mini2.users.model.UserInsert;
import com.mini2.users.model.UserLogin;
import com.mini2.users.model.UserUpdate;
import com.mini2.users.model.UserDelete;

//사용자 관련 작업을 처리하는 컨트롤러
public class UsersController {
	private static UsersController instance = new UsersController();
	private UserUpdate updater = new UserUpdate();
	private UserLogin userlogin = new UserLogin();
	private UserDelete userDelete = new UserDelete();
	private Scanner sc = new Scanner(System.in);
	private Map<String, Integer> session = new HashMap<>();

	private UsersController() {
	}

	// 싱글톤 패턴
	public static UsersController getInstance() {
		return instance;
	}

	// 사용자 등록을 처리
	public void registerUser() {
		boolean check = true;
		String password = null, phoneNumber = null, email = null;
		System.out.println(
				"\n==================================================================================================");
		System.out.println("|\t회원가입을 위해 다음 정보를 입력하세요.\t\t\t\t\t\t\t\t|");
		System.out.print("\t이름 => ");
		String userName = sc.nextLine();

		// 이메일 검증 정규식
		String regexrEmail = "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$";
		while (check) {
			System.out.print("\t이메일 => ");
			email = sc.nextLine();

			Pattern pattern = Pattern.compile(regexrEmail);
			Matcher matcher = pattern.matcher(email);

			if (!matcher.matches()) {
				System.out.println("\t⚠️ 올바르지 않은 이메일 양식입니다.\n");
			} else {
				check = false;
			}
		}
		check = true;

		// 비밀번호검증 -> 최소 8글자, 특수문자 포함
		String regexrPassword = "[!@#$%^&*]";
		while (check) {
			System.out.println("\t※주의! 비밀번호는 최소 8글자와 특수문자가 포함되어야 합니다.");
			System.out.print("\t비밀번호 => ");
			password = sc.nextLine();

			if (password.length() < 8) {
				System.out.println("\t⚠️ 비밀번호는 8글자 이상이어야 합니다.\n");
			} else {
				Pattern pattern = Pattern.compile(regexrPassword);
				Matcher matcher = pattern.matcher(password);
				if (!matcher.find()) {
					System.out.println("\t⚠️비밀번호는 최소 하나의 특수문자가 포함되어야 합니다.\n");
				} else {
					check = false; // 비밀번호 검증을 통과한 경우 루프 종료
				}
			}
		}

		// 전화번호 검증 정규식
		String regexrPhoneNumber = "^\\d{3}-\\d{3,4}-\\d{4}$";
		while (check) {
			System.out.print("\t전화번호 => ");
			phoneNumber = sc.nextLine();

			Pattern pattern = Pattern.compile(regexrPhoneNumber);
			Matcher matcher = pattern.matcher(phoneNumber);

			if (!matcher.matches()) {
				System.out.println("\t⚠️ 올바르지 않은 전화번호 양식입니다.\n");
			} else {
				check = false;
			}
		}

		System.out.print("\t주소 => ");
		String address = sc.nextLine();

		System.out.print("\t추천인 이메일 (선택사항) => ");
		String referrerEmail = sc.nextLine();

		// UserInsert 객체를 생성하여 회원가입 처리
		new UserInsert(userName, email, password, phoneNumber, address, referrerEmail);

	}

	// 로그인된 사용자의 정보를 업데이트
	public void updateUserInfo() {
		boolean check = true;
		String newPassword = null, newPhoneNumber = null, newEmail = null;
		Integer userId = session.get("user_id");
		if (userId == null) {
			System.out.println("\t⚠️ 로그인되지 않았습니다. 로그인 후 시도하세요.\n");
			return;
		}

		System.out.println("\t원하시는 수정을 입력해주세요. 수정을 원치 않는 경우 빈칸으로 작성해주세요.");
		System.out.print("\t이름 => ");
		String newUserName = sc.nextLine();

		// 이메일 검증 정규식
		String regexrEmail = "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$";
		while (check) {
			System.out.print("\t이메일 => ");
			newEmail = sc.nextLine();
			
			if (newEmail.isEmpty()) { // 빈칸일 경우 정규식 검증 패스
				break;
			}

			Pattern pattern = Pattern.compile(regexrEmail);
			Matcher matcher = pattern.matcher(newEmail);

			if (!matcher.matches()) {
				System.out.println("\t⚠️ 올바르지 않은 이메일 양식입니다.\n");
			} else {
				check = false;
			}
		}
		check = true;

		// 비밀번호 검증 -> 최소 8글자, 특수문자 포함
		String regexrPassword = "[!@#$%^&*]";
		while (check) {
			System.out.println("\t※주의! 비밀번호는 최소 8글자와 특수문자가 포함되어야 합니다.");
			System.out.print("\t비밀번호 => ");
			newPassword = sc.nextLine();

			if (newPassword.isEmpty()) { // 빈칸일 경우 정규식 검증 패스
				break;
			}

			if (newPassword.length() < 8) {
				System.out.println("\t⚠️ 비밀번호는 8글자 이상이어야 합니다.\n");
			} else {
				Pattern pattern = Pattern.compile(regexrPassword);
				Matcher matcher = pattern.matcher(newPassword);
				if (!matcher.find()) {
					System.out.println("\t⚠️비밀번호는 최소 하나의 특수문자가 포함되어야 합니다.\n");
				} else {
					check = false; // 비밀번호 검증을 통과한 경우 루프 종료
				}
			}
		}
		check = true;

		// 전화번호 검증 정규식
		String regexrPhoneNumber = "^\\d{3}-\\d{3,4}-\\d{4}$";
		while (check) {
			System.out.print("\t전화번호 => ");
			newPhoneNumber = sc.nextLine();

			if (newPhoneNumber.isEmpty()) {	//빈칸일 경우 정규식 검증 패스
				break;
			}
			
			Pattern pattern = Pattern.compile(regexrPhoneNumber);
			Matcher matcher = pattern.matcher(newPhoneNumber);

			if (!matcher.matches()) {
				System.out.println("\t⚠️ 올바르지 않은 전화번호 양식입니다.\n");
			} else {
				check = false;
			}
		}

		System.out.print("\t주소 => ");
		String newAddress = sc.nextLine();

		// 빈칸이 아닌 경우에만 해당 정보 수정 작업 진행
		if (!newUserName.isEmpty()) {
			updater.updateUserName(userId, newUserName);
		}
		if (!newEmail.isEmpty()) {
			updater.updateEmail(userId, newEmail);
		}
		if (!newPassword.isEmpty()) {
			updater.updatePassword(userId, newPassword);
		}
		if (!newPhoneNumber.isEmpty()) {
			updater.updatePhoneNumber(userId, newPhoneNumber);
		}
		if (!newAddress.isEmpty()) {
			updater.updateAddress(userId, newAddress);
		}
		updater.close();
		System.out.println("|\t정보 수정이 완료되었습니다.\t\t\t\t\t\t\t\t\t|");
	}

	// 사용자를 로그인 처리
	public void loginUser() {
		System.out.print("\t이메일 => ");
		String email = sc.nextLine();

		System.out.print("\t비밀번호 => ");
		String password = sc.nextLine();

		Integer userId = userlogin.UserLogin(email, password);
		if (userId != null) {
			session.put("user_id", userId);
		} else {
			System.out.println("\t⚠️ 로그인 실패. 이메일 또는 비밀번호를 확인하세요.\n");
		}
	}

	// 로그인된 사용자의 계정을 삭제
	public void deleteUser() {
		Integer userId = session.get("user_id");
		if (userId == null) {
			System.out.println("\t⚠️ 로그인되지 않았습니다. 로그인 후 시도하세요.\n");
			return;
		}

		System.out.print("\t⚠️정말로 탈퇴하시겠습니까? (예/아니오) => ");
		String confirmation = sc.nextLine();
		if (confirmation.equalsIgnoreCase("예")) {
			userDelete.delete(userId);
			session.clear();
			System.out.println("|\t회원 탈퇴가 완료되었습니다.\t\t\t\t\t\t\t\t\t\t|");
		} else {
			System.out.println("|\t회원 탈퇴가 취소되었습니다.\t\t\t\t\t\t\t\t\t\t|");
		}
	}

	// 현재 세션 정보 반납
	public Map<String, Integer> getSession() {
		return session;
	}
}