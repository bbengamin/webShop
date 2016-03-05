package com.epam.preprod.bohdanov.utils;

import java.sql.Connection;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.sql.DataSource;

import org.apache.log4j.Logger;

import com.epam.preprod.bohdanov.exception.DataException;

public class DBUtils {
    private static final String CLOSE_CONNECTION_ERROR = "Cant close connection";

    private static final String ROLLBACK_ERROR = "Can't rollback";

    private static final String NEW_CONNECTION_ERROR = "Can't get a new connention";

    private final static SimpleDateFormat dateFormater = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    private static final Logger LOG = Logger.getLogger(DBUtils.class);

    private DataSource dataSource;

    public DBUtils(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    public Connection getConnection() {
        Connection connection = null;
        try {
            connection = dataSource.getConnection();
        } catch (SQLException e) {
            LOG.error(NEW_CONNECTION_ERROR, e);
            throw new DataException(e);
        }
        return connection;
    }

    public void rollback(Connection connection) {
        try {
            connection.rollback();
        } catch (SQLException e) {
            LOG.error(ROLLBACK_ERROR, e);
            throw new DataException(e);
        }
    }

    public void closeConnection(Connection connection) {
        try {
            connection.close();
        } catch (SQLException e) {
            LOG.error(CLOSE_CONNECTION_ERROR, e);
            throw new DataException(e);
        }
    }

    public static Date parseDate(String dateString) throws ParseException {
        Date date = null;
        if (dateString != null) {
            date = dateFormater.parse(dateString);
        }
        return date;
    }

    public static String dateToString(Date date) {
        String dateString = "";
        if (date != null) {
            dateString = dateFormater.format(date);
        }
        return dateString;
    }
}
