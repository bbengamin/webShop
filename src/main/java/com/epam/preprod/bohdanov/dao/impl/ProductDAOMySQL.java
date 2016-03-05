package com.epam.preprod.bohdanov.dao.impl;

import static com.epam.preprod.bohdanov.dao.Fields.PRODUCT_CATEGORY_ID;
import static com.epam.preprod.bohdanov.dao.Fields.PRODUCT_DESCRIPTION;
import static com.epam.preprod.bohdanov.dao.Fields.PRODUCT_ID;
import static com.epam.preprod.bohdanov.dao.Fields.PRODUCT_IMAGE;
import static com.epam.preprod.bohdanov.dao.Fields.PRODUCT_MANUFACTURER_ID;
import static com.epam.preprod.bohdanov.dao.Fields.PRODUCT_NAME;
import static com.epam.preprod.bohdanov.dao.Fields.PRODUCT_PRICE;
import static com.epam.preprod.bohdanov.dao.Fields.PRODUCT_TOTAL;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.epam.preprod.bohdanov.dao.interfaces.ProductDAO;
import com.epam.preprod.bohdanov.model.entity.Product;
import com.epam.preprod.bohdanov.utils.builder.SQLSelectBuilder;
import com.epam.preprod.bohdanov.utils.filter.Condition;

public class ProductDAOMySQL implements ProductDAO {

    @Override
    public List<Product> getFiltredProductList(Condition condition, Connection connection) throws SQLException {
        SQLSelectBuilder builder = new SQLSelectBuilder("*", "product");
        fillBuilderFromCondition(builder, condition);
        fillOrderAndLimitrFromCondition(builder, condition);
        PreparedStatement prst = connection.prepareStatement(builder.toString());
        fillStatementFromCondition(prst, builder.getParameters());
        ResultSet rs = prst.executeQuery();

        List<Product> products = new ArrayList<>();
        while (rs.next()) {
            products.add(dispatchProduct(rs));
        }

        return products;
    }

    @Override
    public Integer getTotalCountOfProducts(Condition condition, Connection connection) throws SQLException {
        int total = 0;
        SQLSelectBuilder builder = new SQLSelectBuilder("count(*) as total", "product");
        fillBuilderFromCondition(builder, condition);
        PreparedStatement prst = connection.prepareStatement(builder.toString());
        fillStatementFromCondition(prst, builder.getParameters());

        ResultSet rs = prst.executeQuery();
        if (rs.next()) {
            total = rs.getInt(PRODUCT_TOTAL);
        }
        rs.close();
        return total;
    }

    @Override
    public Product getProductById(String id, Connection connection) throws SQLException {
        SQLSelectBuilder builder = new SQLSelectBuilder("*", "product");
        builder.addEquals(PRODUCT_ID, id);
        PreparedStatement prst = connection.prepareStatement(builder.toString());
        prst.setString(1, id);
        ResultSet rs = prst.executeQuery();
        Product product = null;
        if (rs.next()) {
            product = dispatchProduct(rs);
        }
        rs.close();
        return product;
    }

    private void fillBuilderFromCondition(SQLSelectBuilder builder, Condition condition) {
        builder.addLike(PRODUCT_NAME, condition.getName());
        builder.addIn(PRODUCT_CATEGORY_ID, condition.getCategory());
        builder.addIn(PRODUCT_MANUFACTURER_ID, condition.getManufacturer());
        builder.addOver(PRODUCT_PRICE, condition.getPriceFrom());
        builder.addLess(PRODUCT_PRICE, condition.getPriceTo());
    }

    private void fillOrderAndLimitrFromCondition(SQLSelectBuilder builder, Condition condition) {
        builder.addOrder(condition.getSort(), condition.getOrder());
        builder.addLimit(condition.getPage(), condition.getLimit());
    }

    private void fillStatementFromCondition(PreparedStatement prst, List<Object> parameters) throws SQLException {
        int index = 1;
        for (Object param : parameters) {
            prst.setObject(index, param);
            index++;
        }
    }

    private Product dispatchProduct(ResultSet rs) throws SQLException {
        Product product = new Product();
        product.setId(rs.getInt(PRODUCT_ID));
        product.setName(rs.getString(PRODUCT_NAME));
        product.setPrice(rs.getBigDecimal(PRODUCT_PRICE));
        product.setDescription(rs.getString(PRODUCT_DESCRIPTION));
        product.setImage(rs.getString(PRODUCT_IMAGE));
        product.setManufacturerId(rs.getInt(PRODUCT_MANUFACTURER_ID));
        product.setCategoryId(rs.getInt(PRODUCT_CATEGORY_ID));
        return product;
    }

}
