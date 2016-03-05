package com.epam.preprod.bohdanov.controller.filter;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import com.epam.preprod.bohdanov.utils.localization.manager.LocaleManager;

public class LocalizationFilter implements Filter {
    public static final Locale DEFAULT_LOCALE = new Locale("en", "US");
    private static final Logger LOG = Logger.getLogger(LocalizationFilter.class);
    private Map<String, Locale> locales;
    private Map<String, String> localesForTag;
    private LocaleManager localeManager;

    public void init(FilterConfig fConfig) throws ServletException {
        List<Locale> availableLocales = Arrays.asList(Locale.getAvailableLocales());
        localeManager = (LocaleManager) fConfig.getServletContext().getAttribute("localeManager");
        locales = new HashMap<>();
        localesForTag = new HashMap<>();

        Enumeration<String> iterator = fConfig.getInitParameterNames();
        while (iterator.hasMoreElements()) {
            String paramName = iterator.nextElement();
            String paramValue = fConfig.getInitParameter(paramName);
            Locale locale = new Locale(paramValue);
            if (availableLocales.contains(locale)) {
                locales.put(paramValue, locale);
                localesForTag.put(paramValue, paramName);
            }
        }
        LOG.info("Avalible locales: " + locales.toString());
    }

    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse res = (HttpServletResponse) response;
        HttpServletRequestWrapper wrapper = getWrapper(req, res);

        req.setAttribute("localization", localesForTag);
        req.getSession().setAttribute("javax.servlet.jsp.jstl.fmt.locale.session", wrapper.getLocale());

        rewriteLocale(req, res);
        chain.doFilter(wrapper, response);
    }

    public void destroy() {
    }

    private HttpServletRequestWrapper getWrapper(HttpServletRequest request, HttpServletResponse response) {
        return new HttpServletRequestWrapper(request) {

            @Override
            public Locale getLocale() {
                if (request.getParameter("lang") != null) {
                    Locale locale = getLocaleFromAvalible(request.getParameter("lang"));
                    localeManager.setLocale(request, response, locale);
                    return locale;
                }
                String localeParam = localeManager.getLocale(request);
                Locale locale = getLocaleFromAvalible(localeParam);
                localeManager.setLocale(request, response, locale);
                return locale;
            }

            @Override
            public Enumeration<Locale> getLocales() {
                List<Locale> temp = new ArrayList<Locale>();

                Enumeration<Locale> iterator = request.getLocales();
                while (iterator.hasMoreElements()) {
                    Locale locale = (Locale) iterator.nextElement();
                    if (locales.containsValue(locale)) {
                        temp.add(locale);
                    }
                }
                return Collections.enumeration(temp);
            }

            private Locale getLocaleFromAvalible(String language) {
                if (locales.containsKey(language)) {
                    return locales.get(language);
                }
                Enumeration<Locale> iterator = request.getLocales();
                while (iterator.hasMoreElements()) {
                    Locale locale = (Locale) iterator.nextElement();
                    if (locales.containsKey(locale.getLanguage())) {
                        return locale;
                    }
                }
                return DEFAULT_LOCALE;
            }

        };
    }

    private void rewriteLocale(HttpServletRequest request, HttpServletResponse response) {
        if (localeManager.getLocale(request) != null) {
            localeManager.setLocale(request, response, new Locale(localeManager.getLocale(request)));
        }
    }
}
