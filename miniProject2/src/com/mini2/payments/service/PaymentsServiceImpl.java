package com.mini2.payments.service;

import java.util.ArrayList;
import java.util.List;

import com.mini2.menuDetail.model.MenuDetailModel;
import com.mini2.menuitems.model.MenuitemsModel;
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

    @Override
    public boolean createPayment(PaymentsModel payment) {
        return payDao.insertPayment(payment);
    }

    @Override
    public boolean updatePaymentStatus(int paymentId, int totalAmount) {
        return payDao.updatePaymentStatus(paymentId, totalAmount);
    }

    @Override
    public boolean deletePayment(int paymentId) {

        return payDao.deletePaymentById(paymentId);
    }
    
    @Override
    public List<MenuDetailModel> getMenuDetailsByOrderId(int orderId) {
    	List<MenuDetailModel> menuDetails = paymentsDao.getMenuDetailsByOrderId(orderId);
        return menuDetails != null ? menuDetails : new ArrayList<>();
    }

    @Override
    public MenuitemsModel getMenuItemById(int itemId) {
        return paymentsDao.getMenuItemById(itemId);
    }
}