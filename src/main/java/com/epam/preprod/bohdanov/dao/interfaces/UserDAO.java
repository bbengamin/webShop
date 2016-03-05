package com.epam.preprod.bohdanov.dao.interfaces;

import java.sql.Connection;
import java.sql.SQLException;

import com.epam.preprod.bohdanov.model.entity.User;

public interface UserDAO {
    public boolean createUser(User user, Connection connection) throws SQLException;

    public User getUserByEmail(String email, Connection connection) throws SQLException;

    public void updateFailLogonCount(User user, Connection connection) throws SQLException;

    public void updateBannedTo(User user, Connection connection) throws SQLException;

    public void updateLastLogin(User user, Connection connection) throws SQLException;
}
