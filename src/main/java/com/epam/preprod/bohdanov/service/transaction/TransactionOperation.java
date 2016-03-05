package com.epam.preprod.bohdanov.service.transaction;

import java.sql.Connection;
import java.sql.SQLException;

public interface TransactionOperation<E> {
    E execute(Connection con) throws SQLException;
}
