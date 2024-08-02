package com.mini2.payments.model;

import java.security.Timestamp;
import lombok.Data;

@Data
public class PaymentsModel {
    private int paymentId;
    private int orderId;
    private int paymentMethod;
    private Timestamp paymentDate;
    private int amount;
	  private boolean paymentStatus;
}
