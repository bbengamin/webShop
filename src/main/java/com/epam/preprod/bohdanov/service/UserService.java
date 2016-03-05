package com.epam.preprod.bohdanov.service;

import static com.epam.preprod.bohdanov.model.entity.Status.FAIL;
import static com.epam.preprod.bohdanov.model.entity.Status.OK;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Calendar;
import java.util.Date;

import com.epam.preprod.bohdanov.dao.interfaces.UserDAO;
import com.epam.preprod.bohdanov.model.entity.Status;
import com.epam.preprod.bohdanov.model.entity.User;
import com.epam.preprod.bohdanov.service.transaction.TransactionManager;
import com.epam.preprod.bohdanov.service.transaction.TransactionOperation;

public class UserService {
    private static final int MAX_FAIL_LOGON_COUNT = 5;
    private static final int BANNED_TIME_IN_MINUTES = 5;
    private TransactionManager transactionManager;
    private UserDAO userDAO;

    public UserService(UserDAO userDAO, TransactionManager transactionManager) {
        this.userDAO = userDAO;
        this.transactionManager = transactionManager;
    }

    public Status newUser(final User user) {
        return transactionManager.<Status> execute(new TransactionOperation<Status>() {
            public Status execute(Connection connection) throws SQLException {
                Status res = FAIL;
                if (userDAO.getUserByEmail(user.getEmail(), connection) != null) {
                    return FAIL;
                }
                if (userDAO.createUser(user, connection)) {
                    res = OK;
                } else {
                    res = FAIL;
                }
                return res;
            }
        });
    }

    public User login(final String email, final String password) {
        return transactionManager.<User> execute(new TransactionOperation<User>() {
            public User execute(Connection connection) throws SQLException {
                User user = userDAO.getUserByEmail(email, connection);
                if (user == null) {
                    return null;
                }
                if (isBanned(user)) {
                    return null;
                }
                if (user.getPassword().compareTo(password) != 0) {
                    incrementFailLogonCount(user);
                    updateBannedTo(user, connection);
                    userDAO.updateFailLogonCount(user, connection);
                    return null;
                }

                user.setFailLoginCount(0);
                user.setLastLogon(new Date());
                userDAO.updateFailLogonCount(user, connection);
                userDAO.updateLastLogin(user, connection);
                return user;
            }
        });
    }

    private boolean isBanned(User user) {
        if (user.getBannedTo() == null) {
            return false;
        }
        Date currentDate = new Date();
        return user.getBannedTo().after(currentDate);
    }

    private void incrementFailLogonCount(User user) {
        int failLogonCount = user.getFailLoginCount() + 1;
        if (failLogonCount > MAX_FAIL_LOGON_COUNT) {
            Calendar currentDate = Calendar.getInstance();
            currentDate.add(Calendar.MINUTE, BANNED_TIME_IN_MINUTES);
            user.setBannedTo(currentDate.getTime());
            user.setFailLoginCount(0);
        } else {
            user.setFailLoginCount(failLogonCount);
        }
    }

    private void updateBannedTo(User user, Connection connection) throws SQLException {
        if (user.getFailLoginCount() > MAX_FAIL_LOGON_COUNT) {
            userDAO.updateBannedTo(user, connection);
        }
    }

}
