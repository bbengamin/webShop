package com.epam.preprod.bohdanov.service.transaction;

public interface TransactionManager {
    <T> T execute(TransactionOperation<T> op);
}
