package com.epam.preprod.bohdanov.utils.captcha.manager;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class CaptchaManagerFactory {
    private Map<String, CaptchaManager> managers;

    public CaptchaManagerFactory() {
        managers = new HashMap<String, CaptchaManager>();
        managers.put("hidden", new CaptchaManager() {

            public void setKey(HttpServletRequest request, HttpServletResponse response, String key) {
                request.setAttribute("captchaKey", key);
            }

            public String getKey(HttpServletRequest request, HttpServletResponse response) {
                return request.getParameter("captchaKey");
            }
        });
        managers.put("session", new CaptchaManager() {
            public void setKey(HttpServletRequest request, HttpServletResponse response, String key) {
                HttpSession session = request.getSession();
                session.setAttribute("captchaKey", key);
            }

            public String getKey(HttpServletRequest request, HttpServletResponse response) {
                HttpSession session = request.getSession();
                return (String) session.getAttribute("captchaKey");
            }
        });
        managers.put("cookie", new CaptchaManager() {
            public void setKey(HttpServletRequest request, HttpServletResponse response, String key) {
                Cookie cookie = new Cookie("captchaKey", key);
                response.addCookie(cookie);
            }

            public String getKey(HttpServletRequest request, HttpServletResponse response) {
                Cookie[] cookies = request.getCookies();
                for (Cookie cookie : cookies) {
                    if (cookie.getName().compareTo("captchaKey") == 0) {
                        return cookie.getValue();
                    }
                }
                return null;
            }
        });
    }

    public CaptchaManager getCaptchaManger(String param) {
        return managers.get(param);
    }
}
