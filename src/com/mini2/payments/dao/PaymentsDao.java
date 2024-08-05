package com.mini2.payments.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import com.mini2.payments.model.PaymentsModel;
import com.mini2.jdbcUtil.JdbcUtil;

public class PaymentsDao {
	
    // 사용자 ID에 따라 결제 정보를 가져오는 메서드
    public List<PaymentsModel> getPaymentsByUserId(int userId) {
        List<PaymentsModel> list = new ArrayList<>();
        String sql = "SELECT * FROM payments WHERE user_id = ?";

//    public List<PaymentsModel> getAllPayments() {
//        List<PaymentsModel> list = new ArrayList<>();
//        String sql = "SELECT * FROM payments";

        try (Connection conn = JdbcUtil.connection();
                PreparedStatement pstmt = conn.prepareStatement(sql)) {

               pstmt.setInt(1, userId);
               try (ResultSet rs = pstmt.executeQuery()) {
                   while (rs.next()) {
                       PaymentsModel payment = new PaymentsModel();
                       payment.setPaymentId(rs.getInt("payment_id"));
                       payment.setOrderId(rs.getInt("order_id"));
                       payment.setPaymentMethod(rs.getInt("payment_method"));
                       payment.setPaymentDate(rs.getTimestamp("payment_date"));
                       payment.setOrderAmount(rs.getInt("order_amount"));
                       payment.setTotalAmount(rs.getInt("total_amount"));
                       payment.setPaymentStatus(rs.getBoolean("pay_status"));
                       list.add(payment);
                   }
               }

           } catch (SQLException e) {
               e.printStackTrace();
           }

           return list;
       }

	public List<PaymentsModel> getAllPayments() {
		// TODO Auto-generated method stub
		return null;
	}
  }