package com.mini2.payments.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import com.mini2.payments.model.PaymentsModel;
import com.mini2.jdbcUtil.JdbcUtil;
import com.mini2.menuDetail.model.MenuDetailModel;
import com.mini2.menuitems.model.MenuitemsModel;

public class PaymentsDao {
    // 사용자 ID에 따라 결제 정보를 가져오는 메서드
    public List<PaymentsModel> getPaymentsByUserId(int userId) {
        List<PaymentsModel> list = new ArrayList<>();
        String sql = "SELECT * FROM payments WHERE user_id = ?";

        try (Connection conn = JdbcUtil.connection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, userId);
            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    PaymentsModel payment = new PaymentsModel();
                    payment.setPaymentId(rs.getInt("payment_id"));
                    payment.setOrderId(rs.getInt("order_id"));
                    payment.setUserId(rs.getInt("user_id")); // 추가
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

    // 주문 ID에 따라 메뉴 상세 정보를 가져오는 메서드
    public List<MenuDetailModel> getMenuDetailsByOrderId(int orderId) {
        List<MenuDetailModel> list = new ArrayList<>();
        String sql = "SELECT * FROM menu_detail WHERE order_id = ?";

        try (Connection conn = JdbcUtil.connection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, orderId);
            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    MenuDetailModel detail = new MenuDetailModel();
                    detail.setOrder_id(rs.getInt("order_id"));
                    detail.setItem_id(rs.getInt("item_id"));
                    detail.setQuantity(rs.getInt("quantity"));
                    list.add(detail);
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return list;
    }

    // 아이템 ID에 따라 메뉴 항목 정보를 가져오는 메서드
    public MenuitemsModel getMenuItemById(int itemId) {
        MenuitemsModel item = null;
        String sql = "SELECT * FROM menuitems WHERE item_id = ?";

        try (Connection conn = JdbcUtil.connection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, itemId);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    item = new MenuitemsModel();
                    item.setItemId(rs.getInt("item_id"));
                    item.setCategoryId(rs.getInt("category_id"));
                    item.setMenuName(rs.getString("menu_name"));
                    item.setDescription(rs.getString("description"));
                    item.setPrice(rs.getInt("price"));
                    item.setAvailable(rs.getBoolean("available"));
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return item;
    }
    
    // 결제 ID에 따라 리뷰가 이미 작성되었는지 확인하는 메서드
    public boolean checkReviewExists(int paymentId) {
        String sql = "SELECT COUNT(*) FROM reviews WHERE payment_id = ?";
        try (Connection conn = JdbcUtil.connection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, paymentId);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    int count = rs.getInt(1);
                    return count > 0;
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return false;
    }
}