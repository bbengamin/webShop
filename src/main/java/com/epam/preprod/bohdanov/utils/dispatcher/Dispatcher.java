package com.epam.preprod.bohdanov.utils.dispatcher;

import javax.servlet.http.HttpServletRequest;

public interface Dispatcher<T> {
    public Object toEntity(T sourse);

    public T fromRequest(HttpServletRequest request);
}
