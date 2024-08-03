package com.mini2.payments.model;

import java.sql.Timestamp;
import lombok.Data;

@Data
public class PaymentsModel {
    private int paymentId; // 결제 ID
    private int orderId; // 주문 ID
    private int paymentMethod; // 결제 방법
    private Timestamp paymentDate; // 결제 날짜
    private int amount; // 결제 금액
    private boolean paymentStatus; // 결제 상태
}
