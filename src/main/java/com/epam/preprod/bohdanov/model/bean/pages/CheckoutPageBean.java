package com.epam.preprod.bohdanov.model.bean.pages;

import java.math.BigDecimal;
import java.util.List;

import com.epam.preprod.bohdanov.model.bean.OrderItemBean;
import com.epam.preprod.bohdanov.model.entity.Category;
import com.epam.preprod.bohdanov.model.entity.Manufacturer;

public class CheckoutPageBean {
    public static final String ADDRESS_PARAM_NAME = "address";
    public static final String PAYMENT_METHOD_PARAM_NAME = "payment_method";

    private List<OrderItemBean> products;
    private String Address;
    private String paymentMethod;
    private List<Manufacturer> manufacturers;
    private List<Category> categories;

    public CheckoutPageBean(List<OrderItemBean> products) {
        this.products = products;
    }

    public String getAddress() {
        return Address;
    }

    public void setAddress(String address) {
        Address = address;
    }

    public String getPaymentMethod() {
        return paymentMethod;
    }

    public void setPaymentMethod(String paymentMethod) {
        this.paymentMethod = paymentMethod;
    }

    public List<OrderItemBean> getProducts() {
        return products;
    }

    public BigDecimal getTotal() {
        BigDecimal total = new BigDecimal(0);
        for (OrderItemBean orderItemBean : products) {
            BigDecimal quantity = new BigDecimal(orderItemBean.getQuantity());
            total = total.add(orderItemBean.getPrice().multiply(quantity));
        }
        return total;
    }

    public List<Manufacturer> getManufacturers() {
        return manufacturers;
    }

    public void setManufacturers(List<Manufacturer> manufacturers) {
        this.manufacturers = manufacturers;
    }

    public List<Category> getCategories() {
        return categories;
    }

    public void setCategories(List<Category> categories) {
        this.categories = categories;
    }
}
