package com.mini2.payments.service;

import com.mini2.payments.dao.PaymentsDAO;
import com.mini2.payments.model.PaymentsModel;

public class PaymentsService {
    private PaymentsDAO paymentsDAO;

    public PaymentsService(PaymentsDAO paymentsDAO) {
        this.paymentsDAO = paymentsDAO;
    }

    public PaymentsModel getPayment(int id) {
        return paymentsDAO.get(id);
    }

}