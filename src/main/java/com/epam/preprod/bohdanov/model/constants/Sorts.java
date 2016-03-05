package com.epam.preprod.bohdanov.model.constants;

public enum Sorts {
    NAME_ASK("name", "Имя по возростанию", "ASC"), NAME_DESC("name", "Имя по убываниию", "DESC"), PRICE_ASK("price",
            "Цена по возростанию", "ASC"), PRICE_DESC("price", "Цена по убываниию", "DESC");
    private String name;
    private String order;
    private String text;

    private Sorts(String name, String text, String order) {
        this.name = name;
        this.text = text;
        this.order = order;
    }

    public String getName() {
        return this.name;
    }

    public String getText() {
        return this.text;
    }

    public String getOrder() {
        return this.order;
    }
}
