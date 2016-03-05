package com.epam.preprod.bohdanov.model.entity;

import java.util.Map;

public class OrderStatus {
    private Map<Integer, String> status;

    public OrderStatus(Map<Integer, String> status) {
        this.status = status;
    }

    public String getStatus(Integer id) {
        return status.get(id);
    }
}
