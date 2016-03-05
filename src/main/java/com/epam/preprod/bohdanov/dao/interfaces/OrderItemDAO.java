package com.epam.preprod.bohdanov.dao.interfaces;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import com.epam.preprod.bohdanov.model.entity.OrderItem;

public interface OrderItemDAO {
    public boolean createOrderItem(OrderItem item, Connection connection) throws SQLException;

    public List<OrderItem> getOrderItemsByOrderId(Integer orderId, Connection connection) throws SQLException;
}
