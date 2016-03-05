package com.epam.preprod.bohdanov.dao.interfaces;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Map;

public interface StatusDAO {
    public Map<Integer, String> getAllStates(Connection connection) throws SQLException;
}
