package com.epam.preprod.bohdanov.utils.dispatcher;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.epam.preprod.bohdanov.model.bean.OrderItemBean;
import com.epam.preprod.bohdanov.model.bean.pages.CheckoutPageBean;
import com.epam.preprod.bohdanov.model.entity.Order;
import com.epam.preprod.bohdanov.model.entity.OrderItem;

public class OrderDispatcher implements Dispatcher<CheckoutPageBean> {

    @Override
    public Object toEntity(CheckoutPageBean sourse) {
        Order order = new Order();
        order.setAddress(sourse.getAddress());
        order.setPaymentMethod(sourse.getPaymentMethod());
        List<OrderItem> items = new ArrayList<>();
        for (OrderItemBean bean : sourse.getProducts()) {
            OrderItem orderItem = new OrderItem();
            orderItem.setPrice(bean.getPrice());
            orderItem.setProductId(bean.getProduct().getId());
            orderItem.setQuantity(bean.getQuantity());
            items.add(orderItem);
        }
        order.setItems(items);
        return order;
    }

    @Override
    public CheckoutPageBean fromRequest(HttpServletRequest request) {
        throw new UnsupportedOperationException();
    }

}
