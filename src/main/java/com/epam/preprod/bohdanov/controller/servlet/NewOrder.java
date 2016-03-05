package com.epam.preprod.bohdanov.controller.servlet;

import static com.epam.preprod.bohdanov.model.bean.pages.CheckoutPageBean.ADDRESS_PARAM_NAME;
import static com.epam.preprod.bohdanov.model.bean.pages.CheckoutPageBean.PAYMENT_METHOD_PARAM_NAME;

import java.io.IOException;
import java.util.List;

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
import com.epam.preprod.bohdanov.model.bean.OrderItemBean;
import com.epam.preprod.bohdanov.model.bean.pages.CheckoutPageBean;
import com.epam.preprod.bohdanov.service.ItemService;
import com.epam.preprod.bohdanov.utils.dispatcher.CartDispatcher;

@WebServlet("/newOrder")
public class NewOrder extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private static final Logger LOG = Logger.getLogger(NewOrder.class);
    private ItemService itemService;

    public void init(ServletConfig servletConfig) throws ServletException {
        LOG.trace("NewOrder servlet start");
        ServletContext context = servletConfig.getServletContext();
        itemService = (ItemService) context.getAttribute("itemService");
    }

    @SuppressWarnings("unchecked")
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession();

        CartDispatcher cartDispatcher = new CartDispatcher();
        CartBean cart = CartBean.getInstance(session);
        CheckoutPageBean pageBean = new CheckoutPageBean((List<OrderItemBean>) cartDispatcher.toEntity(cart));
        pageBean.setCategories(itemService.getAllCategories());
        pageBean.setManufacturers(itemService.getAllManufacturers());

        request.setAttribute("pageBean", pageBean);
        session.setAttribute("pageBean", pageBean);

        String forward = Path.NEW_ORDER_JSP;
        RequestDispatcher rd = request.getRequestDispatcher(forward);
        LOG.trace("Forward to : " + forward);
        rd.forward(request, response);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession();

        CheckoutPageBean pageBean = (CheckoutPageBean) session.getAttribute("pageBean");
        String address = request.getParameter(ADDRESS_PARAM_NAME);
        String paymentMethod = request.getParameter(PAYMENT_METHOD_PARAM_NAME);

        pageBean.setAddress(address);
        pageBean.setPaymentMethod(paymentMethod);

        response.sendRedirect(Path.CHECKOUT_SERVLET);
    }

}
