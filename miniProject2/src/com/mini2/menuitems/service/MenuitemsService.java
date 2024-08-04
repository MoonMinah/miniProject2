package com.mini2.menuitems.service;

import java.util.List;

import com.mini2.menuitems.model.MenuitemsModel;

public interface MenuitemsService {
  
  List<MenuitemsModel> selectCategory(String category); //카테고리 별 메뉴 목록

}
