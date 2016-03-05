package com.epam.preprod.bohdanov.model.bean;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import javax.servlet.http.HttpSession;

import org.apache.commons.lang3.StringUtils;

import com.epam.preprod.bohdanov.model.entity.Product;

public class CartBean {
    private Map<Product, Integer> products;

    public CartBean() {
        products = new HashMap<Product, Integer>();
    }

    public void addProduct(Product product) {
        Integer count = products.get(product);
        if (count == null) {
            count = 0;
        }
        products.put(product, count + 1);
    }

    public void updateProductCount(String id, Integer quantity) {
        products.put(getProductById(id), quantity);
    }

    public void removeProduct(String id) {
        products.remove(getProductById(id));
    }

    public Set<Product> getProductSet() {
        return products.keySet();
    }

    public Map<Product, Integer> getProducts() {
        return products;
    }

    public Integer getTotalCountOfProducts() {
        int count = 0;
        for (Integer itemCount : products.values()) {
            count += itemCount;
        }
        return count;
    }

    public Integer getTotalCountOfProductById(String id) {
        Product product = getProductById(id);
        if (product != null) {
            return products.get(product);
        }
        return 0;
    }

    public BigDecimal getTotal() {
        BigDecimal total = new BigDecimal(0);
        for (Entry<Product, Integer> item : products.entrySet()) {
            BigDecimal count = new BigDecimal(item.getValue());
            total = total.add(item.getKey().getPrice().multiply(count));
        }
        return total;
    }

    public BigDecimal getTotalOfProductById(String id) {
        BigDecimal total = new BigDecimal(0);
        Product product = getProductById(id);
        BigDecimal count = new BigDecimal(products.get(product));
        total = product.getPrice().multiply(count);
        return total;
    }

    public static CartBean getInstance(HttpSession session) {
        CartBean cart = (CartBean) session.getAttribute("cart");
        if (cart == null) {
            cart = new CartBean();
            session.setAttribute("cart", cart);
        }
        return cart;
    }

    private Product getProductById(String id) {
        if (StringUtils.isNotBlank(id)) {
            int productId = Integer.parseInt(id);
            for (Product product : products.keySet()) {
                if (product.getId() == productId) {
                    return product;
                }
            }
        }
        return null;
    }
}
