package com.epam.preprod.bohdanov.model.bean.pages;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.epam.preprod.bohdanov.model.entity.Category;
import com.epam.preprod.bohdanov.model.entity.Manufacturer;
import com.epam.preprod.bohdanov.model.entity.Product;

public class CartPageBean {
    private List<Category> categories;
    private List<Manufacturer> manufacturers;
    private Map<Product, Integer> products;
    private String cartTotal;

    public CartPageBean() {
        products = new HashMap<>();
        categories = new ArrayList<>();
        manufacturers = new ArrayList<>();
        cartTotal = "0";
    }

    public List<Category> getCategories() {
        return categories;
    }

    public void setCategories(List<Category> categories) {
        this.categories = categories;
    }

    public List<Manufacturer> getManufacturers() {
        return manufacturers;
    }

    public void setManufacturers(List<Manufacturer> manufacturers) {
        this.manufacturers = manufacturers;
    }

    public Map<Product, Integer> getProducts() {
        return products;
    }

    public void setProducts(Map<Product, Integer> products) {
        this.products = products;
    }

    public String getCartTotal() {
        return cartTotal;
    }

    public void setCartTotal(String cartTotal) {
        this.cartTotal = cartTotal;
    }
}
