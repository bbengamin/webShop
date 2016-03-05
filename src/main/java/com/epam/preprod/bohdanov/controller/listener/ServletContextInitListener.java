package com.epam.preprod.bohdanov.controller.listener;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.sql.DataSource;

import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;

import com.epam.preprod.bohdanov.dao.impl.CategoryDAOMySQL;
import com.epam.preprod.bohdanov.dao.impl.ManufacturerDAOMySQL;
import com.epam.preprod.bohdanov.dao.impl.OrderDAOMySQL;
import com.epam.preprod.bohdanov.dao.impl.OrderItemDAOMySQL;
import com.epam.preprod.bohdanov.dao.impl.ProductDAOMySQL;
import com.epam.preprod.bohdanov.dao.impl.UserDAOMySQL;
import com.epam.preprod.bohdanov.dao.interfaces.CategoryDAO;
import com.epam.preprod.bohdanov.dao.interfaces.ManufacturerDAO;
import com.epam.preprod.bohdanov.dao.interfaces.OrderDAO;
import com.epam.preprod.bohdanov.dao.interfaces.OrderItemDAO;
import com.epam.preprod.bohdanov.dao.interfaces.ProductDAO;
import com.epam.preprod.bohdanov.dao.interfaces.UserDAO;
import com.epam.preprod.bohdanov.service.ItemService;
import com.epam.preprod.bohdanov.service.OrderService;
import com.epam.preprod.bohdanov.service.UserService;
import com.epam.preprod.bohdanov.service.transaction.JdbcTransactionManager;
import com.epam.preprod.bohdanov.service.transaction.TransactionManager;
import com.epam.preprod.bohdanov.utils.DBUtils;
import com.epam.preprod.bohdanov.utils.captcha.CaptchaContainer;
import com.epam.preprod.bohdanov.utils.captcha.manager.CaptchaManager;
import com.epam.preprod.bohdanov.utils.captcha.manager.CaptchaManagerFactory;
import com.epam.preprod.bohdanov.utils.localization.manager.LocaleManager;
import com.epam.preprod.bohdanov.utils.localization.manager.LocaleManagerFactory;

public class ServletContextInitListener implements ServletContextListener {
    private static final Logger LOG = Logger.getLogger(ServletContextInitListener.class);

    public void contextDestroyed(ServletContextEvent arg0) {
    }

    public void contextInitialized(ServletContextEvent arg0) {
        ServletContext context = arg0.getServletContext();
        initLog4J(context);
        long captchaMaxAge = Long.parseLong(context.getInitParameter("CaptchaMaxAge"));
        String initParamCaptcha = context.getInitParameter("Captcha");
        String initParamLocale = context.getInitParameter("LocaleManager");
        int cookieMaxAge = Integer.parseInt(context.getInitParameter("CookieMaxAge"));

        DataSource dataSource = null;
        try {
            Context initContext = new InitialContext();
            Context envContext = (Context) initContext.lookup("java:/comp/env");
            dataSource = (DataSource) envContext.lookup("jdbc/webShop");
        } catch (NamingException e) {
            LOG.error("Cant get DataSource", e);
        }

        UserDAO userDAO = new UserDAOMySQL();
        ProductDAO productDAO = new ProductDAOMySQL();
        CategoryDAO categotyDAO = new CategoryDAOMySQL();
        ManufacturerDAO manufacturerDAO = new ManufacturerDAOMySQL();
        OrderDAO orderDAO = new OrderDAOMySQL();
        OrderItemDAO orderItemDAO = new OrderItemDAOMySQL();
        TransactionManager transactionManager = new JdbcTransactionManager(new DBUtils(dataSource));
        OrderService orderService = new OrderService(transactionManager, orderDAO, orderItemDAO);
        UserService userService = new UserService(userDAO, transactionManager);
        ItemService itemService = new ItemService(transactionManager, productDAO, categotyDAO, manufacturerDAO);
        CaptchaContainer captchaContainer = new CaptchaContainer(captchaMaxAge);
        CaptchaManagerFactory captchaFactory = new CaptchaManagerFactory();
        CaptchaManager captchaManager = captchaFactory.getCaptchaManger(initParamCaptcha);
        LocaleManagerFactory localeFactory = new LocaleManagerFactory();
        LocaleManager localeManager = localeFactory.getLocaleManager(initParamLocale);

        context.setAttribute("cookieMaxAge", cookieMaxAge);
        context.setAttribute("captchaContainer", captchaContainer);
        context.setAttribute("captchaManager", captchaManager);
        context.setAttribute("localeManager", localeManager);
        context.setAttribute("userService", userService);
        context.setAttribute("itemService", itemService);
        context.setAttribute("orderService", orderService);
        context.setAttribute("ImagePath", context.getInitParameter("ImagePath"));

    }

    /**
     * Initializes log4j framework.
     * 
     * @param servletContext
     */
    private void initLog4J(ServletContext servletContext) {
        log("Log4J initialization started");
        try {
            PropertyConfigurator.configure(servletContext.getRealPath("/WEB-INF/log4j.properties"));
            LOG.debug("Log4j has been initialized");
        } catch (Exception ex) {
            log("Cannot configure Log4j");
            ex.printStackTrace();
        }
        log("Log4J initialization finished");
    }

    private void log(String msg) {
        System.out.println("[ContextListener] " + msg);
    }
}
