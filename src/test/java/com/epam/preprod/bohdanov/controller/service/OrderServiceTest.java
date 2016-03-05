package com.epam.preprod.bohdanov.controller.service;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

import com.epam.preprod.bohdanov.dao.interfaces.OrderDAO;
import com.epam.preprod.bohdanov.dao.interfaces.OrderItemDAO;
import com.epam.preprod.bohdanov.model.entity.Order;
import com.epam.preprod.bohdanov.model.entity.OrderItem;
import com.epam.preprod.bohdanov.model.entity.Status;
import com.epam.preprod.bohdanov.service.OrderService;
import com.epam.preprod.bohdanov.service.transaction.JdbcTransactionManager;
import com.epam.preprod.bohdanov.service.transaction.TransactionManager;
import com.epam.preprod.bohdanov.utils.DBUtils;

import junit.framework.Assert;

@RunWith(MockitoJUnitRunner.class)
public class OrderServiceTest {
    private OrderService service;

    @Mock
    private DBUtils dbUtils;
    @Mock
    private Connection connection;
    @Mock
    private OrderItemDAO orderItemDAO;
    @Mock
    private OrderDAO orderDAO;
    @Mock
    private Order order;
    @Mock
    private OrderItem orderItem;
    private List<OrderItem> items;

    @Before
    public void setUp() {
        items = Arrays.asList(orderItem);
        Mockito.when(dbUtils.getConnection()).thenReturn(connection);
        TransactionManager transactionManager = new JdbcTransactionManager(dbUtils);
        Mockito.when(order.getItems()).thenReturn(items);
        service = new OrderService(transactionManager, orderDAO, orderItemDAO);
    }

    @Test
    public void testNewOrderSuccess() throws SQLException {
        Mockito.when(orderDAO.createOrder(order, connection)).thenReturn(true);
        Mockito.when(orderItemDAO.createOrderItem(orderItem, connection)).thenReturn(true);
        Assert.assertEquals(Status.OK, service.newOrder(order));
    }
    @Test
    public void testNewOrderBadOrder() throws SQLException {
        Mockito.when(orderDAO.createOrder(order, connection)).thenReturn(false);
        Assert.assertEquals(Status.FAIL, service.newOrder(order));
    }
    @Test
    public void testNewOrderBadOrderItems() throws SQLException {
        Mockito.when(orderDAO.createOrder(order, connection)).thenReturn(true);
        Mockito.when(orderItemDAO.createOrderItem(orderItem, connection)).thenReturn(false);
        Assert.assertEquals(Status.FAIL, service.newOrder(order));
    }
}
