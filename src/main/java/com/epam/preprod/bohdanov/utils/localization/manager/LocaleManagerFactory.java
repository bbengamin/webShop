package com.epam.preprod.bohdanov.utils.localization.manager;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class LocaleManagerFactory {
    private Map<String, LocaleManager> managers;

    public LocaleManagerFactory() {
        managers = new HashMap<>();
        managers.put("session", new LocaleManager() {
            @Override
            public String getLocale(HttpServletRequest request) {
                HttpSession session = request.getSession();
                return (String) session.getAttribute("locale");
            }

            @Override
            public void setLocale(HttpServletRequest request, HttpServletResponse response, Locale locale) {
                HttpSession session = request.getSession();
                session.setAttribute("locale", locale.getLanguage());
            }
        });
        managers.put("cookie", new LocaleManager() {
            @Override
            public String getLocale(HttpServletRequest request) {
                Cookie[] cookies = request.getCookies();
                if (cookies != null) {
                    for (Cookie cookie : cookies) {
                        if (cookie.getName().equals("locale")) {
                            return cookie.getValue();
                        }
                    }
                }
                return null;
            }

            @Override
            public void setLocale(HttpServletRequest request, HttpServletResponse response, Locale locale) {
                Integer magAge = (Integer) request.getServletContext().getAttribute("cookieMaxAge");
                Cookie cookie = new Cookie("locale", locale.getLanguage());
                cookie.setPath("/");
                cookie.setMaxAge(magAge);
                response.addCookie(cookie);
            }
        });
    }

    public LocaleManager getLocaleManager(String param) {
        return managers.get(param);
    }

}
