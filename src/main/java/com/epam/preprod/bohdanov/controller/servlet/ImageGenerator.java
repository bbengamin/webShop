package com.epam.preprod.bohdanov.controller.servlet;

import java.io.IOException;

import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.epam.preprod.bohdanov.utils.captcha.CaptchaContainer;
import com.epam.preprod.bohdanov.utils.captcha.CaptchaContainer.UserCaptcha;
import com.epam.preprod.bohdanov.utils.captcha.manager.CaptchaManager;

public class ImageGenerator extends HttpServlet {
    private static final long serialVersionUID = 1L;

    private CaptchaContainer captchaContainer;
    private CaptchaManager captchaManager;

    public void init(ServletConfig servletConfig) throws ServletException {
        ServletContext context = servletConfig.getServletContext();

        captchaContainer = (CaptchaContainer) context.getAttribute("captchaContainer");
        captchaManager = (CaptchaManager) context.getAttribute("captchaManager");
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        UserCaptcha userCaptcha = captchaContainer.getCaptcha(captchaManager.getKey(request, response));
        response.setContentType("image/jpeg");
        if (userCaptcha != null) {
            userCaptcha.outCaptchaImage(response.getOutputStream());
        }
        HttpSession session = request.getSession();
        session.removeAttribute("errors");
        session.removeAttribute("registerForm");
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        doGet(request, response);
    }

}
