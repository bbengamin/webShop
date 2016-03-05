package com.epam.preprod.bohdanov.controller.servlet;

import java.io.IOException;
import java.util.Locale;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;

import com.epam.preprod.bohdanov.controller.Path;
import com.epam.preprod.bohdanov.model.bean.CartBean;
import com.epam.preprod.bohdanov.model.bean.pages.CartPageBean;
import com.epam.preprod.bohdanov.service.ItemService;
import com.epam.preprod.bohdanov.utils.PriceUtils;

@WebServlet("/cart")
public class Cart extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private static final Logger LOG = Logger.getLogger(Cart.class);
    private ItemService itemService;

    public void init(ServletConfig servletConfig) throws ServletException {
        LOG.trace("Cart servlet start");
        ServletContext context = servletConfig.getServletContext();
        itemService = (ItemService) context.getAttribute("itemService");
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        PriceUtils priceUtils = new PriceUtils();
        HttpSession session = request.getSession();

        CartBean cart = CartBean.getInstance(session);
        CartPageBean pageBean = new CartPageBean();
        pageBean.setCartTotal(priceUtils.getLocalizedBigDecimalValue(cart.getTotal(), Locale.getDefault()));
        pageBean.setProducts(cart.getProducts());
        pageBean.setCategories(itemService.getAllCategories());
        pageBean.setManufacturers(itemService.getAllManufacturers());

        request.setAttribute("pageBean", pageBean);

        String forward = Path.CART_JSP;
        RequestDispatcher rd = request.getRequestDispatcher(forward);
        LOG.trace("Forward to : " + forward);
        rd.forward(request, response);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        doGet(request, response);
    }

}
