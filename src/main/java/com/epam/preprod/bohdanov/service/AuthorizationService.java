package com.epam.preprod.bohdanov.service;

import static com.epam.preprod.bohdanov.utils.authorization.AuthorizationActionContainer.ERROR;
import static com.epam.preprod.bohdanov.utils.authorization.AuthorizationActionContainer.OK;
import static com.epam.preprod.bohdanov.utils.authorization.AuthorizationActionContainer.REDIRECT;

import java.util.List;
import java.util.Map;

import com.epam.preprod.bohdanov.model.entity.User;

public class AuthorizationService {
    private Map<String, List<String>> rules;

    public AuthorizationService(Map<String, List<String>> rules) {
        this.rules = rules;
    }

    public String checkAccess(String url, User user) {
        if (rules.containsKey(url)) {
            if (user == null) {
                return REDIRECT;
            }
            List<String> roles = rules.get(url);
            String userRole = String.valueOf(user.getRoleId());
            if (!roles.contains(userRole)) {
                return ERROR;
            }
        }
        return OK;
    }
}
