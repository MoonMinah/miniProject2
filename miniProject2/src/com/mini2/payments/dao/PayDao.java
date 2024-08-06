package com.mini2.payments.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;

import com.mini2.payments.model.PaymentsModel;
import com.mini2.jdbcUtil.JdbcUtil;
import com.mini2.orders.model.OrdersModel;

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
                    payment.setUserId(rs.getInt("user_id"));
                    payment.setPaymentMethod(rs.getInt("payment_method"));
                    payment.setPaymentDate(rs.getTimestamp("payment_date"));
                    payment.setOrderAmount(rs.getInt("order_amount"));
                    payment.setTotalAmount(rs.getInt("total_amount"));
                    payment.setPaymentStatus(rs.getBoolean("pay_status"));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return payment;
    }

    // 결제 정보를 삽입하는 메서드
    public boolean insertPayment(PaymentsModel payment) {

        String sql = "INSERT INTO payments (order_id, user_id, payment_method, order_amount, total_amount, pay_status, payment_date) VALUES (?, ?, ?, ?, ?, ?, ?)";

      


        try (Connection conn = JdbcUtil.connection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, payment.getOrderId());

            pstmt.setInt(2, payment.getUserId());
            pstmt.setInt(3, payment.getPaymentMethod());
            pstmt.setInt(4, payment.getOrderAmount());
            pstmt.setInt(5, payment.getTotalAmount()); // total_amount
            pstmt.setBoolean(6, payment.isPaymentStatus());
            pstmt.setTimestamp(7, new Timestamp(System.currentTimeMillis())); // payment_date

            int rowsAffected = pstmt.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Error: " + e.getMessage());


        }
        return false;
    }

    // 결제 상태 업데이트 메서드
    public boolean updatePaymentStatus(int paymentId, int totalAmount) {
        String sql = "UPDATE payments SET total_amount = ?, pay_status = ? WHERE payment_id = ?";

        try (Connection conn = JdbcUtil.connection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, totalAmount);
            pstmt.setBoolean(2, true); // 결제 상태를 true로 변경
            pstmt.setInt(3, paymentId);

            int rowsAffected = pstmt.executeUpdate();
            System.out.println("Rows affected: " + rowsAffected);
            return rowsAffected > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Error: " + e.getMessage());
        }
        return false;
    }
    
    // 결제 정보를 삭제하는 메서드
    public boolean deletePaymentById(int paymentId) {
        String sql = "DELETE FROM payments WHERE payment_id = ?";

        try (Connection conn = JdbcUtil.connection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, paymentId);

            int rowsAffected = pstmt.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Error: " + e.getMessage());
        }
        return false;
    }
}