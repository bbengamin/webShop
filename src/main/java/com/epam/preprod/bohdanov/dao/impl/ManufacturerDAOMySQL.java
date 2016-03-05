package com.epam.preprod.bohdanov.dao.impl;

import static com.epam.preprod.bohdanov.dao.Fields.MANUFACTURER_ID;
import static com.epam.preprod.bohdanov.dao.Fields.MANUFACTURER_NAME;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.epam.preprod.bohdanov.dao.interfaces.ManufacturerDAO;
import com.epam.preprod.bohdanov.model.entity.Manufacturer;

public class ManufacturerDAOMySQL implements ManufacturerDAO {
    private final String GET_ALL_MANUFACTURERS = "SELECT * FROM `manufacturer`;";

    @Override
    public List<Manufacturer> getAllManufacturers(Connection connection) throws SQLException {
        PreparedStatement prst = connection.prepareStatement(GET_ALL_MANUFACTURERS);
        ResultSet rs = prst.executeQuery();
        List<Manufacturer> list = new ArrayList<Manufacturer>();
        while (rs.next()) {
            Manufacturer manufacturer = new Manufacturer();
            manufacturer.setId(rs.getInt(MANUFACTURER_ID));
            manufacturer.setName(rs.getString(MANUFACTURER_NAME));
            list.add(manufacturer);
        }
        return list;
    }

}
