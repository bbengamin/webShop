package com.epam.preprod.bohdanov.dao.interfaces;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import com.epam.preprod.bohdanov.model.entity.Category;

public interface CategoryDAO {
    public Category getCategory(int id, Connection connection) throws SQLException;

    public List<Category> getAllCategories(Connection connection) throws SQLException;
}
