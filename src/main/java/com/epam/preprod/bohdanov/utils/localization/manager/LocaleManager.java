package com.epam.preprod.bohdanov.utils.localization.manager;

import java.util.Locale;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public interface LocaleManager {
    public String getLocale(HttpServletRequest request);

    public void setLocale(HttpServletRequest request, HttpServletResponse response, Locale locale);
}
