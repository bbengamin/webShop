package com.epam.preprod.bohdanov.utils.captcha.manager;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public interface CaptchaManager {
    public String getKey(HttpServletRequest request, HttpServletResponse response);

    public void setKey(HttpServletRequest request, HttpServletResponse response, String key);
}
