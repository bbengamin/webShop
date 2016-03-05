package com.epam.preprod.bohdanov.utils.authorization;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public interface AuthorizationAction {
    public void doAction(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
            throws IOException, ServletException;
}
