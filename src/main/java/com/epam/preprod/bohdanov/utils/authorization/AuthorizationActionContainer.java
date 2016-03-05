package com.epam.preprod.bohdanov.utils.authorization;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import com.epam.preprod.bohdanov.controller.Path;

public class AuthorizationActionContainer {
    private static final Logger LOG = Logger.getLogger(AuthorizationActionContainer.class);
    public final static String OK = "ok";
    public final static String REDIRECT = "redirect";
    public final static String ERROR = "error";
    private Map<String, AuthorizationAction> actions;

    public AuthorizationActionContainer() {
        actions = new HashMap<>();
        actions.put(OK, new AuthorizationAction() {

            @Override
            public void doAction(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
                    throws IOException, ServletException {
                LOG.info("Authorization filter say OK");
                chain.doFilter(request, response);
            }
        });
        actions.put(REDIRECT, new AuthorizationAction() {

            @Override
            public void doAction(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
                    throws IOException {
                response.sendRedirect(Path.LOGIN_SERVLET + "?action=" + request.getRequestURI());
                LOG.info("Redirect to login page");
                return;
            }
        });
        actions.put(ERROR, new AuthorizationAction() {

            @Override
            public void doAction(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
                    throws IOException {
                response.sendError(HttpServletResponse.SC_FORBIDDEN);
                LOG.error("Access denied");
                return;
            }
        });
    }

    public AuthorizationAction getAction(String action) {
        return actions.get(action);
    }
}
