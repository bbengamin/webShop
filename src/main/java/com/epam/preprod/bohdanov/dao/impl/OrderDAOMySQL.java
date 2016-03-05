package com.epam.preprod.bohdanov.dao.impl;

import static com.epam.preprod.bohdanov.dao.Fields.ORDER_ADDRESS;
import static com.epam.preprod.bohdanov.dao.Fields.ORDER_COMMENT;
import static com.epam.preprod.bohdanov.dao.Fields.ORDER_DATE;
import static com.epam.preprod.bohdanov.dao.Fields.ORDER_ID;
import static com.epam.preprod.bohdanov.dao.Fields.ORDER_PAYMENT_METHOD;
import static com.epam.preprod.bohdanov.dao.Fields.ORDER_STATUS_ID;
import static com.epam.preprod.bohdanov.dao.Fields.ORDER_USER_ID;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;

import com.epam.preprod.bohdanov.dao.interfaces.OrderDAO;
import com.epam.preprod.bohdanov.exception.DataException;
import com.epam.preprod.bohdanov.model.entity.Order;
import com.epam.preprod.bohdanov.utils.DBUtils;
import com.epam.preprod.bohdanov.utils.builder.SQLSelectBuilder;
import com.mysql.jdbc.Statement;

public class OrderDAOMySQL implements OrderDAO {
    private final String CREATE_NEW_ORDER = "INSERT INTO `order` (`status_id`, `comment`, `date`, `user_id`, `address`, `payment_method`) VALUES (?, ?, ?, ?, ?, ?)";

    @Override
    public boolean createOrder(Order order, Connection connection) throws SQLException {
        PreparedStatement prst = connection.prepareStatement(CREATE_NEW_ORDER, Statement.RETURN_GENERATED_KEYS);

        prst.setInt(1, order.getStatusId());
        prst.setString(2, order.getComment());
        prst.setString(3, DBUtils.dateToString(order.getDate()));
        prst.setInt(4, order.getUserId());
        prst.setString(5, order.getAddress());
        prst.setString(6, order.getPaymentMethod());

        if (prst.executeUpdate() > 0) {
            ResultSet rs = prst.getGeneratedKeys();
            if (rs.next()) {
                order.setId(rs.getInt(1));
                rs.close();
                return true;
            }
            rs.close();
        }
        return false;
    }

    @Override
    public Order getOrderById(Integer orderId, Connection connection) throws SQLException {
        SQLSelectBuilder builder = new SQLSelectBuilder("*", "order");
        builder.addEquals(ORDER_ID, String.valueOf(orderId));
        PreparedStatement prst = connection.prepareStatement(builder.toString());
        prst.setInt(1, orderId);
        Order order = null;
        ResultSet rs = prst.executeQuery();
        if (rs.next()) {
            try {
                order = dispatchOrder(rs);
            } catch (ParseException e) {
                new DataException(e);
            }
        }

        return order;
    }

    private Order dispatchOrder(ResultSet rs) throws SQLException, ParseException {
        Order order = new Order();

        order.setId(rs.getInt(ORDER_ID));
        order.setStatusId(rs.getInt(ORDER_STATUS_ID));
        order.setComment(ORDER_COMMENT);
        order.setDate(DBUtils.parseDate(rs.getString(ORDER_DATE)));
        order.setUserId(rs.getInt(ORDER_USER_ID));
        order.setAddress(rs.getString(ORDER_ADDRESS));
        order.setPaymentMethod(rs.getString(ORDER_PAYMENT_METHOD));

        return order;
    }
}
