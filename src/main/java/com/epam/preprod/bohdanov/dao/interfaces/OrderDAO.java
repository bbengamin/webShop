package com.epam.preprod.bohdanov.dao.interfaces;

import java.sql.Connection;
import java.sql.SQLException;

import com.epam.preprod.bohdanov.model.entity.Order;

public interface OrderDAO {
    public boolean createOrder(Order order, Connection connection) throws SQLException;

    public Order getOrderById(Integer orderId, Connection connection) throws SQLException;

}
