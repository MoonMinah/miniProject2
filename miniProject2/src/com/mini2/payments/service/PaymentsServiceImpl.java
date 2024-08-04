package com.mini2.payments.service;

import java.util.List;

import com.mini2.payments.dao.PayDao;
import com.mini2.payments.dao.PaymentsDao;
import com.mini2.payments.model.PaymentsModel;

public class PaymentsServiceImpl implements PaymentsService {
    private PayDao payDao = new PayDao();
    private PaymentsDao paymentsDao = new PaymentsDao();

    @Override
    public List<PaymentsModel> getAllPayments() {
        return paymentsDao.getAllPayments();
    }

    @Override
    public List<PaymentsModel> getPaymentsByUserId(int userId) {
        return paymentsDao.getPaymentsByUserId(userId);
    }
    
    @Override
    public PaymentsModel getPaymentByOrderId(int orderId) {
        return payDao.getPaymentByOrderId(orderId);
    }
}