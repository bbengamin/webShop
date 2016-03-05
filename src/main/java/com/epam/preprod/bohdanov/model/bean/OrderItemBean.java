package com.epam.preprod.bohdanov.model.bean;

import java.math.BigDecimal;

import com.epam.preprod.bohdanov.model.entity.Product;

public class OrderItemBean {
    private Product product;
    private Integer quantity;
    private BigDecimal price;

    public OrderItemBean(Product product, Integer quantity, BigDecimal price) {
        this.product = product;
        this.quantity = quantity;
        this.price = price;
    }

    public Product getProduct() {
        return product;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public BigDecimal getPrice() {
        return price;
    }
}
