package com.mini2.payments.model;

import java.sql.Timestamp;
import lombok.Data;

@Data
public class PaymentsModel {
    private int paymentId;
    private int orderId;
    private int userId;
    private int paymentMethod;
    private Timestamp paymentDate;
    private int orderAmount;
    private int totalAmount;
    private boolean paymentStatus;
}