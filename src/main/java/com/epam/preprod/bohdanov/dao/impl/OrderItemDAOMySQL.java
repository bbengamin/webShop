package com.epam.preprod.bohdanov.dao.impl;

import static com.epam.preprod.bohdanov.dao.Fields.ORDER_ITEM_ID;
import static com.epam.preprod.bohdanov.dao.Fields.ORDER_ITEM_ORDER_ID;
import static com.epam.preprod.bohdanov.dao.Fields.ORDER_ITEM_PRICE;
import static com.epam.preprod.bohdanov.dao.Fields.ORDER_ITEM_PROODUCT_ID;
import static com.epam.preprod.bohdanov.dao.Fields.ORDER_ITEM_QUANTITY;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.epam.preprod.bohdanov.dao.interfaces.OrderItemDAO;
import com.epam.preprod.bohdanov.model.entity.OrderItem;
import com.epam.preprod.bohdanov.utils.builder.SQLSelectBuilder;
import com.mysql.jdbc.Statement;

public class OrderItemDAOMySQL implements OrderItemDAO {
    private final String CREATE_ORDER_ITEM = "INSERT INTO `order_item` (`product_id`, `quantity`, `price`, `order_id`) VALUES (?, ?, ?, ?)";

    @Override
    public boolean createOrderItem(OrderItem item, Connection connection) throws SQLException {
        PreparedStatement prst = connection.prepareStatement(CREATE_ORDER_ITEM, Statement.RETURN_GENERATED_KEYS);

        prst.setInt(1, item.getProductId());
        prst.setInt(2, item.getQuantity());
        prst.setBigDecimal(3, item.getPrice());
        prst.setInt(4, item.getOrderId());

        if (prst.executeUpdate() > 0) {
            ResultSet rs = prst.getGeneratedKeys();
            if (rs.next()) {
                item.setId(rs.getInt(1));
                rs.close();
                return true;
            }
            rs.close();
        }
        return false;
    }

    @Override
    public List<OrderItem> getOrderItemsByOrderId(Integer orderId, Connection connection) throws SQLException {
        SQLSelectBuilder builder = new SQLSelectBuilder("*", "order_item");
        builder.addEquals(ORDER_ITEM_ID, String.valueOf(orderId));
        PreparedStatement prst = connection.prepareStatement(builder.toString());
        prst.setInt(1, orderId);

        List<OrderItem> items = new ArrayList<>();
        ResultSet rs = prst.executeQuery();
        while (rs.next()) {
            items.add(dispatchOrderItem(rs));
        }

        return null;
    }

    private OrderItem dispatchOrderItem(ResultSet rs) throws SQLException {
        OrderItem orderItem = new OrderItem();
        orderItem.setId(rs.getInt(ORDER_ITEM_ID));
        orderItem.setOrderId(rs.getInt(ORDER_ITEM_ORDER_ID));
        orderItem.setProductId(rs.getInt(ORDER_ITEM_PROODUCT_ID));
        orderItem.setQuantity(rs.getInt(ORDER_ITEM_QUANTITY));
        orderItem.setPrice(rs.getBigDecimal(ORDER_ITEM_PRICE));
        return orderItem;
    }
}
