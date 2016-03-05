package com.epam.preprod.bohdanov.model.bean.pages;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.epam.preprod.bohdanov.model.constants.Sorts;
import com.epam.preprod.bohdanov.model.entity.Category;
import com.epam.preprod.bohdanov.model.entity.Manufacturer;
import com.epam.preprod.bohdanov.model.entity.Product;

public class RoutePageBean {
    private List<Product> products;
    private int totalCountOfProducts;
    private List<Category> categories;
    private List<Manufacturer> manufacturers;
    private Map<String, String> pagination;
    private Map<Integer, String> limits;
    private Map<Sorts, String> sorts;

    public RoutePageBean() {
        products = new ArrayList<>();
        categories = new ArrayList<>();
        manufacturers = new ArrayList<>();
        totalCountOfProducts = 0;
        pagination = new HashMap<>();
        limits = new HashMap<>();
        sorts = new HashMap<>();
    }

    public List<Product> getProducts() {
        return products;
    }

    public void setProducts(List<Product> products) {
        this.products = products;
    }

    public int getTotalCountOfProducts() {
        return totalCountOfProducts;
    }

    public void setTotalCountOfProducts(int totalCountOfProducts) {
        this.totalCountOfProducts = totalCountOfProducts;
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

    public Map<String, String> getPagination() {
        return pagination;
    }

    public void setPagination(Map<String, String> pagination) {
        this.pagination = pagination;
    }

    public Map<Integer, String> getLimits() {
        return limits;
    }

    public void setLimits(Map<Integer, String> limits) {
        this.limits = limits;
    }

    public Map<Sorts, String> getSorts() {
        return sorts;
    }

    public void setSorts(Map<Sorts, String> sorts) {
        this.sorts = sorts;
    }
}
