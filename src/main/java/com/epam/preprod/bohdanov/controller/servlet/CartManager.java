package com.epam.preprod.bohdanov.controller.servlet;

import java.io.IOException;

import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import com.epam.preprod.bohdanov.service.ItemService;
import com.epam.preprod.bohdanov.utils.cart.CartActions;

@WebServlet("/cartManager")
public class CartManager extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private static final Logger LOG = Logger.getLogger(CartManager.class);
    private ItemService itemService;

    public void init(ServletConfig servletConfig) throws ServletException {
        LOG.trace("Route servlet start");
        ServletContext context = servletConfig.getServletContext();
        itemService = (ItemService) context.getAttribute("itemService");
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        doPost(request, response);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String action = request.getParameter("action");
        CartActions actions = new CartActions(itemService);
        actions.getAction(action).doAction(request, response);
    }
}
