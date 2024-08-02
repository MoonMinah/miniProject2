package com.mini2.main;

import com.mini2.users.service.UsersService;

public class Main {
	public static void main(String[] args) {
		ASCIIArt asciiArt = new ASCIIArt();
		UsersService userservice = new UsersService();
		
		//처음 화면 창 둘리
		asciiArt.ASCIIArt();

		//회원가입, 로그인, 종료 창
		userservice.start();
		
		
	}
}
