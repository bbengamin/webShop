package com.epam.preprod.bohdanov.dao.impl;

import static com.epam.preprod.bohdanov.dao.Fields.USER_BANNED_TO;
import static com.epam.preprod.bohdanov.dao.Fields.USER_DELIVERY;
import static com.epam.preprod.bohdanov.dao.Fields.USER_EMAIL;
import static com.epam.preprod.bohdanov.dao.Fields.USER_FACULTY;
import static com.epam.preprod.bohdanov.dao.Fields.USER_FAIL_LOGIN_COUNT;
import static com.epam.preprod.bohdanov.dao.Fields.USER_FIRST_NAME;
import static com.epam.preprod.bohdanov.dao.Fields.USER_ID;
import static com.epam.preprod.bohdanov.dao.Fields.USER_IMAGE;
import static com.epam.preprod.bohdanov.dao.Fields.USER_LAST_LOGON;
import static com.epam.preprod.bohdanov.dao.Fields.USER_LAST_NAME;
import static com.epam.preprod.bohdanov.dao.Fields.USER_LOGIN;
import static com.epam.preprod.bohdanov.dao.Fields.USER_PASSWORD;
import static com.epam.preprod.bohdanov.dao.Fields.USER_PHONE;
import static com.epam.preprod.bohdanov.dao.Fields.USER_ROLE_ID;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;

import com.epam.preprod.bohdanov.dao.interfaces.UserDAO;
import com.epam.preprod.bohdanov.exception.DataException;
import com.epam.preprod.bohdanov.model.entity.Faculty;
import com.epam.preprod.bohdanov.model.entity.User;
import com.epam.preprod.bohdanov.utils.DBUtils;
import com.mysql.jdbc.Statement;

public class UserDAOMySQL implements UserDAO {
    private final SimpleDateFormat dateFormater = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    private final String CREATE_USER = "INSERT INTO `shop`.`user` (`first_name`, `last_name`, `email`, `phone`, `login`, `password`, `faculty`, `delivery`, `image`) VALUES  (?, ?, ?, ?, ?, ?, ?, ?, ?);";
    private final String GET_USER_BY_EMAIL = "SELECT * FROM `user` where `email`=?;";
    private final String UPDATE_USER_FAIL_LOGON_COUNT = "UPDATE `shop`.`user` SET `failLoginCount`=? WHERE `user_id`=?";
    private final String UPDATE_USER_LAST_LOGON = "UPDATE `shop`.`user` SET `lastLogon`=? WHERE `user_id`=?";
    private final String UPDATE_USER_BANNED_TO = "UPDATE `shop`.`user` SET `bannedTo`=? WHERE `user_id`=?";

    public boolean createUser(User user, Connection connection) throws SQLException {
        PreparedStatement prst = connection.prepareStatement(CREATE_USER, Statement.RETURN_GENERATED_KEYS);
        prst.setString(1, user.getFirstName());
        prst.setString(2, user.getLastName());
        prst.setString(3, user.getEmail());
        prst.setString(4, user.getPhone());
        prst.setString(5, user.getLogin());
        prst.setString(6, user.getPassword());
        prst.setInt(7, user.getFaculty().getValue());
        prst.setBoolean(8, user.isDelivery());
        prst.setString(9, user.getImage());
        if (prst.executeUpdate() > 0) {
            ResultSet rs = prst.getGeneratedKeys();
            if (rs.next()) {
                user.setId(rs.getInt(1));
                rs.close();
                return true;
            }
            rs.close();
        }
        return false;
    }

    public User getUserByEmail(String email, Connection connection) throws SQLException {
        PreparedStatement prst = connection.prepareStatement(GET_USER_BY_EMAIL);
        prst.setString(1, email);
        ResultSet rs = prst.executeQuery();
        if (rs.next()) {
            User user = null;
            try {
                user = dispatchUser(rs);
            } catch (ParseException e) {
                new DataException(e);
            }
            rs.close();
            return user;
        }
        return null;
    }

    private User dispatchUser(ResultSet rs) throws SQLException, ParseException {
        User user = new User();
        user.setId(rs.getInt(USER_ID));
        user.setFirstName(rs.getString(USER_FIRST_NAME));
        user.setLastName(rs.getString(USER_LAST_NAME));
        user.setEmail(rs.getString(USER_EMAIL));
        user.setPhone(rs.getString(USER_PHONE));
        user.setLogin(rs.getString(USER_LOGIN));
        user.setPassword(rs.getString(USER_PASSWORD));
        user.setFaculty(Faculty.getFacultyByValue(rs.getInt(USER_FACULTY)));
        user.setDelivery(rs.getBoolean(USER_DELIVERY));
        user.setImage(rs.getString(USER_IMAGE));
        user.setRoleId(rs.getInt(USER_ROLE_ID));
        user.setFailLoginCount(rs.getInt(USER_FAIL_LOGIN_COUNT));
        user.setBannedTo(DBUtils.parseDate(rs.getString(USER_BANNED_TO)));
        user.setLastLogon(DBUtils.parseDate(rs.getString(USER_LAST_LOGON)));

        return user;
    }

    @Override
    public void updateFailLogonCount(User user, Connection connection) throws SQLException {
        PreparedStatement prst = connection.prepareStatement(UPDATE_USER_FAIL_LOGON_COUNT);
        prst.setInt(1, user.getFailLoginCount());
        prst.setInt(2, user.getId());
        prst.executeUpdate();
    }

    @Override
    public void updateBannedTo(User user, Connection connection) throws SQLException {
        PreparedStatement prst = connection.prepareStatement(UPDATE_USER_BANNED_TO);
        String currentTime = dateFormater.format(user.getBannedTo());
        prst.setString(1, currentTime);
        prst.setInt(2, user.getId());
        prst.executeUpdate();
    }

    @Override
    public void updateLastLogin(User user, Connection connection) throws SQLException {
        PreparedStatement prst = connection.prepareStatement(UPDATE_USER_LAST_LOGON);
        String currentTime = dateFormater.format(user.getLastLogon());
        prst.setString(1, currentTime);
        prst.setInt(2, user.getId());
        prst.executeUpdate();
    }
}
