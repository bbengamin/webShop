package com.epam.preprod.bohdanov.utils.dispatcher;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map.Entry;

import javax.servlet.http.HttpServletRequest;

import com.epam.preprod.bohdanov.model.bean.CartBean;
import com.epam.preprod.bohdanov.model.bean.OrderItemBean;
import com.epam.preprod.bohdanov.model.entity.Product;

public class CartDispatcher implements Dispatcher<CartBean> {

    @Override
    public Object toEntity(CartBean sourse) {
        List<OrderItemBean> list = new ArrayList<>();

        for (Entry<Product, Integer> cartItem : sourse.getProducts().entrySet()) {
            BigDecimal price = cartItem.getKey().getPrice();
            Product product = cartItem.getKey();
            Integer quantity = cartItem.getValue();
            OrderItemBean orderItem = new OrderItemBean(product, quantity, price);
            list.add(orderItem);
        }

        return list;
    }

    @Override
    public CartBean fromRequest(HttpServletRequest request) {
        throw new UnsupportedOperationException();
    }

}
