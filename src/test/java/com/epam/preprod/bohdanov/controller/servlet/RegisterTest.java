package com.epam.preprod.bohdanov.controller.servlet;

import static com.epam.preprod.bohdanov.model.constants.RegisterFormBeanConstants.CAPTCHA;
import static com.epam.preprod.bohdanov.model.constants.RegisterFormBeanConstants.DELIVERY;
import static com.epam.preprod.bohdanov.model.constants.RegisterFormBeanConstants.EMAIL;
import static com.epam.preprod.bohdanov.model.constants.RegisterFormBeanConstants.FACULTY;
import static com.epam.preprod.bohdanov.model.constants.RegisterFormBeanConstants.FIRST_NAME;
import static com.epam.preprod.bohdanov.model.constants.RegisterFormBeanConstants.LAST_NAME;
import static com.epam.preprod.bohdanov.model.constants.RegisterFormBeanConstants.LOGIN;
import static com.epam.preprod.bohdanov.model.constants.RegisterFormBeanConstants.PASSWORD;
import static com.epam.preprod.bohdanov.model.constants.RegisterFormBeanConstants.PASSWORD_CONFIRM;
import static com.epam.preprod.bohdanov.model.constants.RegisterFormBeanConstants.PHONE;
import static org.mockito.Mockito.only;
import static org.mockito.Mockito.verify;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import com.epam.preprod.bohdanov.service.UserService;
import com.epam.preprod.bohdanov.model.entity.Status;
import com.epam.preprod.bohdanov.utils.captcha.CaptchaContainer;
import com.epam.preprod.bohdanov.utils.captcha.CaptchaContainer.UserCaptcha;
import com.epam.preprod.bohdanov.utils.captcha.manager.CaptchaManager;

public class RegisterTest {
    private final Register servlet = new Register();
    private HttpServletRequest request;
    private HttpServletResponse response;
    private ServletConfig config;
    private ServletContext context;
    private HttpSession session;
    private CaptchaManager captchaManager;
    private CaptchaContainer captchaContainer;

    private final String GOOD_FIRST_NAME = "firstName";
    private final String GOOD_LAST_NAME = "lastName";
    private final String GOOD_EMAIL = "email@email.com";
    private final String GOOD_PHONE = "1234567890";
    private final String GOOD_LOGIN = "goodlogin";
    private final String GOOD_PASSWORD = "password";
    private final String GOOD_PASSWORD_CONFIRM = "password";
    private final String GOOD_FACULTY = "2";
    private final String GOOD_DELIVERY = "true";
    private final String GOOD_CAPTCHA = "1111";
    private final String CAPTCHA_KEY = "1111";

    @Before
    public void setUp() throws ServletException, IllegalAccessException {
        request = Mockito.mock(HttpServletRequest.class);
        response = Mockito.mock(HttpServletResponse.class);
        config = Mockito.mock(ServletConfig.class);
        context = Mockito.mock(ServletContext.class);
        session = Mockito.mock(HttpSession.class);

        captchaManager = Mockito.mock(CaptchaManager.class);
        captchaContainer = Mockito.mock(CaptchaContainer.class);
        Mockito.when(captchaManager.getKey(request, response)).thenReturn(CAPTCHA_KEY);

        UserCaptcha userCaptcha = Mockito.mock(UserCaptcha.class);
        Mockito.when(userCaptcha.isAlive()).thenReturn(true);
        Mockito.when(userCaptcha.getValue()).thenReturn(Integer.valueOf(GOOD_CAPTCHA));

        Mockito.when(captchaContainer.getCaptcha(Mockito.anyString())).thenReturn(userCaptcha);

        UserService service = Mockito.mock(UserService.class);
        Mockito.when(service.newUser(Mockito.anyObject())).thenReturn(Status.OK);
        Mockito.when(context.getAttribute("captchaContainer")).thenReturn(captchaContainer);
        Mockito.when(context.getAttribute("userService")).thenReturn(service);
        Mockito.when(context.getAttribute("captchaManager")).thenReturn(captchaManager);
        Mockito.when(config.getServletContext()).thenReturn(context);
        Mockito.when(request.getSession()).thenReturn(session);

        servlet.init(config);
    }

    @Test
    public void testDoGet() throws ServletException, IOException {
        RequestDispatcher dispatcher = Mockito.mock(RequestDispatcher.class);
        Mockito.when(request.getRequestDispatcher(Mockito.anyString())).thenReturn(dispatcher);
        servlet.doGet(request, response);
        verify(dispatcher, only()).forward(request, response);
    }

    @Test
    public void testDoPostSuccess() throws ServletException, IOException, IllegalAccessException {
        servlet.init(config);
        Mockito.when(request.getParameter(FIRST_NAME)).thenReturn(GOOD_FIRST_NAME);
        Mockito.when(request.getParameter(LAST_NAME)).thenReturn(GOOD_LAST_NAME);
        Mockito.when(request.getParameter(EMAIL)).thenReturn(GOOD_EMAIL);
        Mockito.when(request.getParameter(PHONE)).thenReturn(GOOD_PHONE);
        Mockito.when(request.getParameter(LOGIN)).thenReturn(GOOD_LOGIN);
        Mockito.when(request.getParameter(PASSWORD)).thenReturn(GOOD_PASSWORD);
        Mockito.when(request.getParameter(PASSWORD_CONFIRM)).thenReturn(GOOD_PASSWORD_CONFIRM);
        Mockito.when(request.getParameter(FACULTY)).thenReturn(GOOD_FACULTY);
        Mockito.when(request.getParameter(DELIVERY)).thenReturn(GOOD_DELIVERY);
        Mockito.when(request.getParameter(CAPTCHA)).thenReturn(GOOD_CAPTCHA);

        servlet.doPost(request, response);
        verify(response, only()).sendRedirect("route");
    }

    @Test
    public void testDoPostBadData() throws ServletException, IOException, IllegalAccessException {
        Mockito.when(request.getParameter(FIRST_NAME)).thenReturn("f");
        Mockito.when(request.getParameter(LAST_NAME)).thenReturn("l");
        Mockito.when(request.getParameter(EMAIL)).thenReturn("@.");
        Mockito.when(request.getParameter(PHONE)).thenReturn("phone");
        Mockito.when(request.getParameter(LOGIN)).thenReturn("lll");
        Mockito.when(request.getParameter(PASSWORD)).thenReturn("qwer");
        Mockito.when(request.getParameter(PASSWORD_CONFIRM)).thenReturn("rewq");
        Mockito.when(request.getParameter(FACULTY)).thenReturn("1");
        Mockito.when(request.getParameter(DELIVERY)).thenReturn("true");
        Mockito.when(request.getParameter(CAPTCHA)).thenReturn("1111");

        servlet.doPost(request, response);
        verify(response, only()).sendRedirect("register");
        verify(session, Mockito.atLeast(2)).setAttribute(Mockito.anyString(), Mockito.anyObject());
    }

    @Test
    public void testDoPostWrongCaptcha() throws ServletException, IOException, IllegalAccessException {
        servlet.init(config);
        Mockito.when(request.getParameter(FIRST_NAME)).thenReturn(GOOD_FIRST_NAME);
        Mockito.when(request.getParameter(LAST_NAME)).thenReturn(GOOD_LAST_NAME);
        Mockito.when(request.getParameter(EMAIL)).thenReturn(GOOD_EMAIL);
        Mockito.when(request.getParameter(PHONE)).thenReturn(GOOD_PHONE);
        Mockito.when(request.getParameter(LOGIN)).thenReturn(GOOD_LOGIN);
        Mockito.when(request.getParameter(PASSWORD)).thenReturn(GOOD_PASSWORD);
        Mockito.when(request.getParameter(PASSWORD_CONFIRM)).thenReturn(GOOD_PASSWORD_CONFIRM);
        Mockito.when(request.getParameter(FACULTY)).thenReturn(GOOD_FACULTY);
        Mockito.when(request.getParameter(DELIVERY)).thenReturn(GOOD_DELIVERY);
        Mockito.when(request.getParameter(CAPTCHA)).thenReturn("bad captcha");

        servlet.doPost(request, response);
        verify(response, only()).sendRedirect("register");
        verify(session, Mockito.atLeast(2)).setAttribute(Mockito.anyString(), Mockito.anyObject());
    }

}
