package com.mini2.payments.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import com.mini2.payments.model.PaymentsModel;
import com.mini2.jdbcUtil.JdbcUtil;

public class PayDao {

    // orderId에 따라 결제 정보를 가져오는 메서드 (단일 결과 반환)
    public PaymentsModel getPaymentByOrderId(int orderId) {
        String sql = "SELECT * FROM payments WHERE order_id = ?";
        PaymentsModel payment = null;

        try (Connection conn = JdbcUtil.connection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, orderId);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    payment = new PaymentsModel();
                    payment.setPaymentId(rs.getInt("payment_id"));
                    payment.setOrderId(rs.getInt("order_id"));
                    payment.setPaymentMethod(rs.getInt("payment_method"));
                    payment.setPaymentDate(rs.getTimestamp("payment_date"));
                    payment.setAmount(rs.getInt("amount"));
                    payment.setPaymentStatus(rs.getBoolean("pay_status"));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return payment;
    }
}