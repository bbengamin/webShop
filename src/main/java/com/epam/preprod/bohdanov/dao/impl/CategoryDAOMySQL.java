package com.epam.preprod.bohdanov.dao.impl;

import static com.epam.preprod.bohdanov.dao.Fields.CATEGORY_ID;
import static com.epam.preprod.bohdanov.dao.Fields.CATEGORY_NAME;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.epam.preprod.bohdanov.dao.interfaces.CategoryDAO;
import com.epam.preprod.bohdanov.model.entity.Category;

public class CategoryDAOMySQL implements CategoryDAO {
    private String GET_CATEGORY_BY_ID = "SELECT * FROM category WHERE `category_id`=?";
    private final String GET_ALL_CATEGORIES = "SELECT * FROM `category`;";

    @Override
    public Category getCategory(int id, Connection connection) throws SQLException {
        PreparedStatement prst = connection.prepareStatement(GET_CATEGORY_BY_ID);
        prst.setInt(1, id);
        ResultSet rs = prst.executeQuery();
        if (rs.next()) {
            Category category = new Category();
            category.setName(rs.getString(CATEGORY_NAME));
            rs.close();
            return category;
        }
        rs.close();
        return null;
    }

    @Override
    public List<Category> getAllCategories(Connection connection) throws SQLException {
        PreparedStatement prst = connection.prepareStatement(GET_ALL_CATEGORIES);
        ResultSet rs = prst.executeQuery();
        List<Category> list = new ArrayList<Category>();
        while (rs.next()) {
            Category category = new Category();
            category.setId(rs.getInt(CATEGORY_ID));
            category.setName(rs.getString(CATEGORY_NAME));
            list.add(category);
        }
        return list;
    }
}
