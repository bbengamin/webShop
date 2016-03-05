package com.epam.preprod.bohdanov.dao.interfaces;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import com.epam.preprod.bohdanov.model.entity.Manufacturer;

public interface ManufacturerDAO {
    public List<Manufacturer> getAllManufacturers(Connection connection) throws SQLException;
}
