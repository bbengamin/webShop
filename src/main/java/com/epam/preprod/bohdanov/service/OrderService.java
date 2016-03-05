package com.epam.preprod.bohdanov.service;

import static com.epam.preprod.bohdanov.model.entity.Status.FAIL;
import static com.epam.preprod.bohdanov.model.entity.Status.OK;

import java.sql.Connection;
import java.sql.SQLException;

import com.epam.preprod.bohdanov.dao.interfaces.OrderDAO;
import com.epam.preprod.bohdanov.dao.interfaces.OrderItemDAO;
import com.epam.preprod.bohdanov.model.entity.Order;
import com.epam.preprod.bohdanov.model.entity.OrderItem;
import com.epam.preprod.bohdanov.model.entity.Status;
import com.epam.preprod.bohdanov.service.transaction.TransactionManager;
import com.epam.preprod.bohdanov.service.transaction.TransactionOperation;

public class OrderService {
    private TransactionManager transactionManager;
    private OrderDAO orderDAO;
    private OrderItemDAO orderItemDAO;

    public OrderService(TransactionManager transactionManager, OrderDAO orderDAO, OrderItemDAO orderItemDAO) {
        this.transactionManager = transactionManager;
        this.orderDAO = orderDAO;
        this.orderItemDAO = orderItemDAO;
    }

    public Status newOrder(final Order order) {
        return transactionManager.<Status> execute(new TransactionOperation<Status>() {
            public Status execute(Connection connection) throws SQLException {
                Status res = FAIL;

                if (orderDAO.createOrder(order, connection)) {
                    for (OrderItem item : order.getItems()) {
                        item.setOrderId(order.getId());
                        if (!orderItemDAO.createOrderItem(item, connection)) {
                            return FAIL;
                        }
                    }
                    res = OK;
                }

                return res;
            }
        });
    }

}
