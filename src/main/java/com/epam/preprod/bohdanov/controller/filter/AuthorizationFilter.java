package com.epam.preprod.bohdanov.controller.filter;

import java.io.File;
import java.io.IOException;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.xml.parsers.ParserConfigurationException;

import org.apache.log4j.Logger;
import org.xml.sax.SAXException;

import com.epam.preprod.bohdanov.model.entity.User;
import com.epam.preprod.bohdanov.service.AuthorizationService;
import com.epam.preprod.bohdanov.utils.authorization.AuthorizationAction;
import com.epam.preprod.bohdanov.utils.authorization.AuthorizationActionContainer;
import com.epam.preprod.bohdanov.utils.xml.DOMParser;

public class AuthorizationFilter implements Filter {
    private static final Logger LOG = Logger.getLogger(AuthorizationFilter.class);
    private static final String SEPARATOR = File.separator;

    private AuthorizationService authorizationService;

    private AuthorizationActionContainer actionContainer;

    public void init(FilterConfig fConfig) throws ServletException {
        Map<String, List<String>> rules = loadRules(fConfig);
        authorizationService = new AuthorizationService(rules);
        actionContainer = new AuthorizationActionContainer();
        LOG.trace("Authorization filter start");
    }

    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        LOG.trace("Authorization filter here");
        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse res = (HttpServletResponse) response;

        HttpSession session = req.getSession();
        User user = (User) session.getAttribute("user");
        String actionName = authorizationService.checkAccess(req.getRequestURI(), user);
        AuthorizationAction action = actionContainer.getAction(actionName);
        action.doAction(req, res, chain);
    }

    public void destroy() {
    }

    private Map<String, List<String>> loadRules(FilterConfig fConfig) {
        Map<String, List<String>> rules = Collections.emptyMap();
        String appPath = fConfig.getServletContext().getRealPath("");
        String xmlPath = fConfig.getInitParameter("XMLPath");
        String xmlFileName = fConfig.getInitParameter("XMLFileName");
        DOMParser parser = new DOMParser(appPath + xmlPath + SEPARATOR + xmlFileName);
        System.out.println(appPath + xmlPath + SEPARATOR + xmlFileName);
        try {
            parser.parse(true);
            rules = parser.getRules();
        } catch (ParserConfigurationException | SAXException | IOException e) {
            LOG.error("Cant parse XML file", e);
        }
        return rules;
    }
}
