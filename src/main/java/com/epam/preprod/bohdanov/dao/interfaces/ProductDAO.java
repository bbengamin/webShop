package com.epam.preprod.bohdanov.dao.interfaces;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import com.epam.preprod.bohdanov.model.entity.Product;
import com.epam.preprod.bohdanov.utils.filter.Condition;

public interface ProductDAO {
    public List<Product> getFiltredProductList(Condition condition, Connection connection) throws SQLException;

    public Integer getTotalCountOfProducts(Condition condition, Connection connection) throws SQLException;

    public Product getProductById(String id, Connection connection) throws SQLException;
}
