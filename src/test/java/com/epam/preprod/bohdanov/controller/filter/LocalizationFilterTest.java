package com.epam.preprod.bohdanov.controller.filter;

import static org.mockito.Matchers.anyObject;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Locale;

import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

import com.epam.preprod.bohdanov.utils.localization.manager.LocaleManager;

import junit.framework.Assert;

@RunWith(MockitoJUnitRunner.class)
public class LocalizationFilterTest {
    private static final Locale RU_LOCALE = new Locale("ru");
    private static final Locale EN_LOCALE = new Locale("en");
    private static final Locale KZ_LOCALE = new Locale("uk");
    private LocalizationFilter filter;
    @Mock
    private FilterConfig fConfig;
    @Mock
    private ServletContext context;
    @Mock
    private LocaleManager localeManager;
    @Mock
    private HttpServletRequest request;
    @Mock
    private HttpServletResponse response;
    @Mock
    private HttpSession session;
    @Mock
    private FilterChain chain;

    private ArgumentCaptor<HttpServletRequestWrapper> wrapper;

    @Before
    public void setUp() throws ServletException {
        wrapper = ArgumentCaptor.forClass(HttpServletRequestWrapper.class);
        List<String> initParams = new ArrayList<>();
        initParams.add("Russian");
        initParams.add("English");
        when(request.getSession()).thenReturn(session);
        when(context.getAttribute("localeManager")).thenReturn(localeManager);
        when(fConfig.getServletContext()).thenReturn(context);
        when(fConfig.getInitParameterNames()).thenReturn(Collections.enumeration(initParams));
        when(fConfig.getInitParameter("Russian")).thenReturn("ru");
        when(fConfig.getInitParameter("English")).thenReturn("en");
        filter = new LocalizationFilter();
        filter.init(fConfig);
    }

    @Test
    public void doFilterTestDefaultLocale() throws IOException, ServletException {
        when(request.getParameter(Mockito.anyString())).thenReturn(null);
        when(localeManager.getLocale(request)).thenReturn(null);
        when(request.getLocales()).thenReturn(Collections.enumeration(Collections.emptyList()));

        filter.doFilter(request, response, chain);

        verify(chain).doFilter(wrapper.capture(), anyObject());
        HttpServletRequestWrapper value = wrapper.getValue();
        Assert.assertEquals(LocalizationFilter.DEFAULT_LOCALE, value.getLocale());
    }

    @Test
    public void doFilterTestChangeLocaleToExist() throws IOException, ServletException {
        when(request.getParameter(Mockito.anyString())).thenReturn("ru");
        when(localeManager.getLocale(request)).thenReturn(null);
        when(request.getLocales()).thenReturn(Collections.enumeration(Collections.emptyList()));

        filter.doFilter(request, response, chain);

        verify(chain).doFilter(wrapper.capture(), anyObject());
        HttpServletRequestWrapper value = wrapper.getValue();
        Assert.assertEquals(RU_LOCALE, value.getLocale());
    }

    @Test
    public void doFilterTestChangeLocaleToNotExist() throws IOException, ServletException {
        when(request.getParameter(Mockito.anyString())).thenReturn("ar");
        when(localeManager.getLocale(request)).thenReturn(null);
        when(request.getLocales()).thenReturn(Collections.enumeration(Collections.emptyList()));

        filter.doFilter(request, response, chain);

        verify(chain).doFilter(wrapper.capture(), anyObject());
        HttpServletRequestWrapper value = wrapper.getValue();
        Assert.assertEquals(LocalizationFilter.DEFAULT_LOCALE, value.getLocale());
    }

    @Test
    public void doFilterTestChangeLocaleToNotExistAndNotEmptyAcceptLanguage() throws IOException, ServletException {
        List<Locale> acceptLanguage = new ArrayList<>();
        acceptLanguage.add(RU_LOCALE);
        acceptLanguage.add(EN_LOCALE);
        when(request.getParameter(Mockito.anyString())).thenReturn("ar");
        when(localeManager.getLocale(request)).thenReturn(null);
        when(request.getLocales()).thenReturn(Collections.enumeration(acceptLanguage));

        filter.doFilter(request, response, chain);

        verify(chain).doFilter(wrapper.capture(), anyObject());
        HttpServletRequestWrapper value = wrapper.getValue();
        when(request.getLocales()).thenReturn(Collections.enumeration(acceptLanguage));
        Assert.assertEquals(RU_LOCALE, value.getLocale());
    }

    @Test
    public void doFilterTestGetLocaleFromAcceptLanguage() throws IOException, ServletException {
        List<Locale> acceptLanguage = new ArrayList<>();
        acceptLanguage.add(RU_LOCALE);
        acceptLanguage.add(EN_LOCALE);
        when(request.getParameter(Mockito.anyString())).thenReturn(null);
        when(localeManager.getLocale(request)).thenReturn(null);
        when(request.getLocales()).thenReturn(Collections.enumeration(acceptLanguage));

        filter.doFilter(request, response, chain);
        verify(chain).doFilter(wrapper.capture(), anyObject());
        when(request.getLocales()).thenReturn(Collections.enumeration(acceptLanguage));
        HttpServletRequestWrapper value = wrapper.getValue();
        Assert.assertEquals(RU_LOCALE, value.getLocale());
    }

    @Test
    public void doFilterTestGetLocaleFromAcceptLanguageAndNoAnySuitable() throws IOException, ServletException {
        List<Locale> acceptLanguage = new ArrayList<>();
        acceptLanguage.add(KZ_LOCALE);
        when(request.getParameter(Mockito.anyString())).thenReturn(null);
        when(localeManager.getLocale(request)).thenReturn(null);
        when(request.getLocales()).thenReturn(Collections.enumeration(acceptLanguage));

        filter.doFilter(request, response, chain);
        verify(chain).doFilter(wrapper.capture(), anyObject());
        HttpServletRequestWrapper value = wrapper.getValue();
        Assert.assertEquals(LocalizationFilter.DEFAULT_LOCALE, value.getLocale());
    }

    @Test
    public void doFilterTestGetLocaleFromStorage() throws IOException, ServletException {
        when(request.getParameter(Mockito.anyString())).thenReturn(null);
        when(localeManager.getLocale(request)).thenReturn("en");
        when(request.getLocales()).thenReturn(Collections.enumeration(Collections.emptyList()));

        filter.doFilter(request, response, chain);
        verify(chain).doFilter(wrapper.capture(), anyObject());
        HttpServletRequestWrapper value = wrapper.getValue();
        Assert.assertEquals(EN_LOCALE, value.getLocale());
    }

    @Test
    public void doFilterTestGetLocales() throws IOException, ServletException {
        List<Locale> acceptLanguage = new ArrayList<>();
        acceptLanguage.add(RU_LOCALE);
        acceptLanguage.add(KZ_LOCALE);
        acceptLanguage.add(EN_LOCALE);
        when(localeManager.getLocale(request)).thenReturn(null);
        when(request.getLocales()).thenReturn(Collections.enumeration(acceptLanguage));

        filter.doFilter(request, response, chain);
        verify(chain).doFilter(wrapper.capture(), anyObject());
        HttpServletRequestWrapper value = wrapper.getValue();
        
        when(request.getLocales()).thenReturn(Collections.enumeration(acceptLanguage));
        Assert.assertEquals(RU_LOCALE, value.getLocales().nextElement());
    }
}
