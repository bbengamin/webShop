package com.epam.preprod.bohdanov.service;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import com.epam.preprod.bohdanov.dao.interfaces.CategoryDAO;
import com.epam.preprod.bohdanov.dao.interfaces.ManufacturerDAO;
import com.epam.preprod.bohdanov.dao.interfaces.ProductDAO;
import com.epam.preprod.bohdanov.model.bean.pages.RoutePageBean;
import com.epam.preprod.bohdanov.model.entity.Category;
import com.epam.preprod.bohdanov.model.entity.Manufacturer;
import com.epam.preprod.bohdanov.model.entity.Product;
import com.epam.preprod.bohdanov.service.transaction.TransactionManager;
import com.epam.preprod.bohdanov.service.transaction.TransactionOperation;
import com.epam.preprod.bohdanov.utils.filter.Condition;

public class ItemService {
    private TransactionManager transactionManager;
    private ProductDAO productDAO;
    private CategoryDAO categoryDAO;
    private ManufacturerDAO manufacturerDAO;

    public ItemService(TransactionManager transactionManager, ProductDAO productDAO, CategoryDAO categoryDAO,
            ManufacturerDAO manufacturerDAO) {
        this.transactionManager = transactionManager;
        this.productDAO = productDAO;
        this.categoryDAO = categoryDAO;
        this.manufacturerDAO = manufacturerDAO;
    }

    public RoutePageBean doFilter(final Condition condition) {
        return transactionManager.<RoutePageBean> execute(new TransactionOperation<RoutePageBean>() {
            public RoutePageBean execute(Connection connection) throws SQLException {
                RoutePageBean page = new RoutePageBean();
                page.setProducts(productDAO.getFiltredProductList(condition, connection));
                page.setTotalCountOfProducts(productDAO.getTotalCountOfProducts(condition, connection));
                page.setManufacturers(manufacturerDAO.getAllManufacturers(connection));
                page.setCategories(categoryDAO.getAllCategories(connection));
                return page;
            }
        });
    }

    public Product getProductById(final String id) {
        return transactionManager.<Product> execute(new TransactionOperation<Product>() {
            public Product execute(Connection connection) throws SQLException {
                return productDAO.getProductById(id, connection);
            }
        });
    }

    public List<Category> getAllCategories() {
        return transactionManager.<List<Category>> execute(new TransactionOperation<List<Category>>() {
            public List<Category> execute(Connection connection) throws SQLException {
                return categoryDAO.getAllCategories(connection);
            }
        });
    }

    public List<Manufacturer> getAllManufacturers() {
        return transactionManager.<List<Manufacturer>> execute(new TransactionOperation<List<Manufacturer>>() {
            public List<Manufacturer> execute(Connection connection) throws SQLException {
                return manufacturerDAO.getAllManufacturers(connection);
            }
        });
    }

}
