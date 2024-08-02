package com.mini2.menuDetail.model;

import lombok.Data;

@Data
//메뉴 상세
public class MenuDetailModel {
    private int menu_detail_id;
    private int item_id;
    private int order_id;
    private int quantity;
}
