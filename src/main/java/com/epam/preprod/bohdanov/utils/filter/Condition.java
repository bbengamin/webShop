package com.epam.preprod.bohdanov.utils.filter;

public class Condition {
    public static final String ORDER_DEFAULT = "ASC";
    public static final String SORT_DEFAULT = "name";
    public static final int PAGE_DAFAULT = 1;
    public static final int LIMIT_DEFAULT = 5;
    public static final String NAME = SORT_DEFAULT;
    
    public static final String CATEGORY = "category";
    public static final String MANUFACTURER = "manufacturer";
    public static final String PRICE_FROM = "priceFrom";
    public static final String PRICE_TO = "priceTo";
    public static final String LIMIT = "limit";
    public static final String SORT = "sort";
    public static final String ORDER = "order";
    public static final String PAGE = "page";

    private String name;
    private String[] category;
    private String[] manufacturer;
    private String priceFrom;
    private String priceTo;
    private Integer limit;
    private Integer page;
    private String sort;
    private String order;

    public Condition() {
        this.limit = LIMIT_DEFAULT;
        this.page = PAGE_DAFAULT;
        this.sort = SORT_DEFAULT;
        this.order = ORDER_DEFAULT;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String[] getCategory() {
        return category;
    }

    public void setCategory(String[] category) {
        this.category = category;
    }

    public String[] getManufacturer() {
        return manufacturer;
    }

    public void setManufacturer(String[] manufacturer) {
        this.manufacturer = manufacturer;
    }

    public String getPriceFrom() {
        return priceFrom;
    }

    public void setPriceFrom(String priceFrom) {
        this.priceFrom = priceFrom;
    }

    public String getPriceTo() {
        return priceTo;
    }

    public void setPriceTo(String priceTo) {
        this.priceTo = priceTo;
    }

    public Integer getLimit() {
        return limit;
    }

    public void setLimit(Integer limit) {
        this.limit = limit;
    }

    public Integer getPage() {
        return page;
    }

    public void setPage(Integer page) {
        this.page = page;
    }

    public String getSort() {
        return sort;
    }

    public void setSort(String sort) {
        this.sort = sort;
    }

    public String getOrder() {
        return order;
    }

    public void setOrder(String order) {
        this.order = order;
    }
}
