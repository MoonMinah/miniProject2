package com.mini2.menuDetail.model;

import lombok.Data;

@Data
//메뉴 상세
public class MenuDetailModel {
    private int menu_detail_id; //메뉴상세 아이디
    private int item_id; // 아이템 아이디
    private int order_id; // 주문 아이디
    private int quantity; // 수량
}
