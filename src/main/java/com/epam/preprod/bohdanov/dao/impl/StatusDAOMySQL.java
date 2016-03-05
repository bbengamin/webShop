package com.epam.preprod.bohdanov.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import com.epam.preprod.bohdanov.dao.Fields;
import com.epam.preprod.bohdanov.dao.interfaces.StatusDAO;

public class StatusDAOMySQL implements StatusDAO {
    private final String GET_ALL_STATES = "SELECT * FROM `status`";

    @Override
    public Map<Integer, String> getAllStates(Connection connection) throws SQLException {
        Map<Integer, String> states = new HashMap<>();
        PreparedStatement prst = connection.prepareStatement(GET_ALL_STATES);
        ResultSet rs = prst.executeQuery();
        while (rs.next()) {
            states.put(rs.getInt(Fields.STATUS_ID), rs.getString(Fields.STATUS_NAME));
        }
        rs.close();
        return states;
    }

}
