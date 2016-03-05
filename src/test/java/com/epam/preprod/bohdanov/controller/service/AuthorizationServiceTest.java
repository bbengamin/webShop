package com.epam.preprod.bohdanov.controller.service;

import static com.epam.preprod.bohdanov.utils.authorization.AuthorizationActionContainer.ERROR;
import static com.epam.preprod.bohdanov.utils.authorization.AuthorizationActionContainer.OK;
import static com.epam.preprod.bohdanov.utils.authorization.AuthorizationActionContainer.REDIRECT;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Before;
import org.junit.Test;

import com.epam.preprod.bohdanov.model.entity.User;
import com.epam.preprod.bohdanov.service.AuthorizationService;

import junit.framework.Assert;

public class AuthorizationServiceTest {
    private AuthorizationService service;
    private static final String ADMIN = "1";
    private static final String USER = "2";
    private static final String ADMIN_PAGE = "/admin";
    private static final String USER_PAGE = "/user";
    private static final String NOT_MAPPING_PAGE = "/custom";

    @Before
    public void setUp() {
        Map<String, List<String>> rules = new HashMap<>();
        rules.put(ADMIN_PAGE, Arrays.asList(ADMIN));
        rules.put(USER_PAGE, Arrays.asList(ADMIN, USER));
        service = new AuthorizationService(rules);
    }

    @Test
    public void checkAccessToAdminPageByUser() {
        String expected = ERROR;
        User user = new User();
        user.setRoleId(Integer.parseInt(USER));

        String actual = service.checkAccess(ADMIN_PAGE, user);

        Assert.assertEquals(expected, actual);
    }

    @Test
    public void checkAccessToAdminPageByAdmin() {
        String expected = OK;
        User user = new User();
        user.setRoleId(Integer.parseInt(ADMIN));

        String actual = service.checkAccess(ADMIN_PAGE, user);

        Assert.assertEquals(expected, actual);
    }

    @Test
    public void checkAccessToUserPageByAdmin() {
        String expected = OK;
        User user = new User();
        user.setRoleId(Integer.parseInt(ADMIN));

        String actual = service.checkAccess(USER_PAGE, user);

        Assert.assertEquals(expected, actual);
    }

    @Test
    public void checkAccessToUserPageByUser() {
        String expected = OK;
        User user = new User();
        user.setRoleId(Integer.parseInt(USER));

        String actual = service.checkAccess(USER_PAGE, user);

        Assert.assertEquals(expected, actual);
    }

    @Test
    public void checkAccessToUserPageByUnauthorizedUser() {
        String expected = REDIRECT;
        User user = null;
        String actual = service.checkAccess(USER_PAGE, user);

        Assert.assertEquals(expected, actual);
    }
    @Test
    public void checkAccessToAdminPageByUnauthorizedUser() {
        String expected = REDIRECT;
        User user = null;
        String actual = service.checkAccess(ADMIN_PAGE, user);

        Assert.assertEquals(expected, actual);
    }
    @Test
    public void checkAccessToNotMappingPageByUnauthorizedUser() {
        String expected = OK;
        User user = null;
        String actual = service.checkAccess(NOT_MAPPING_PAGE, user);

        Assert.assertEquals(expected, actual);
    }
}
