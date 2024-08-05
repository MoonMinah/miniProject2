package com.mini2.payments.service;

import java.util.List;
import com.mini2.payments.model.PaymentsModel;

public interface PaymentsService {
    List<PaymentsModel> getAllPayments();
    List<PaymentsModel> getPaymentsByUserId(int userId);
    PaymentsModel getPaymentByOrderId(int orderId);
    boolean createPayment(PaymentsModel payment);
    boolean updatePaymentStatus(int paymentId, int totalAmount);
    boolean deletePayment(int paymentId); // 이 메서드 추가
}