package com.epam.preprod.bohdanov.controller.servlet;

import java.io.IOException;
import java.util.Calendar;
import java.util.Date;

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
import com.epam.preprod.bohdanov.model.bean.pages.CheckoutPageBean;
import com.epam.preprod.bohdanov.model.entity.Order;
import com.epam.preprod.bohdanov.model.entity.Status;
import com.epam.preprod.bohdanov.model.entity.User;
import com.epam.preprod.bohdanov.service.OrderService;
import com.epam.preprod.bohdanov.utils.dispatcher.OrderDispatcher;

@WebServlet("/checkout")
public class Checkout extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private static final Logger LOG = Logger.getLogger(Checkout.class);

    private static final String ORDER_CREATION_ERROR = "Order creation error! Redirect to: ";
    private static final String NEW_ORDER_SUCCESSFUL = "New order successful created! Forward to: ";
    private static final Integer STATUS_FORMED_ID = 3;
    private static final String NEW_ORDER_COMMENT = "New order";

    private OrderService orderService;

    public void init(ServletConfig servletConfig) throws ServletException {
        LOG.trace("Checkout servlet start");
        ServletContext context = servletConfig.getServletContext();
        orderService = (OrderService) context.getAttribute("orderService");
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession();
        CheckoutPageBean pageBean = (CheckoutPageBean) session.getAttribute("pageBean");
        
        request.setAttribute("pageBean", pageBean);

        String forward = Path.CHECKOUT_JSP;
        RequestDispatcher rd = request.getRequestDispatcher(forward);
        LOG.trace("Forward to : " + forward);
        rd.forward(request, response);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession();
        CheckoutPageBean pageBean = (CheckoutPageBean) session.getAttribute("pageBean");
        OrderDispatcher orderDispatcher = new OrderDispatcher();
        Order order = (Order) orderDispatcher.toEntity(pageBean);
        User user = (User) session.getAttribute("user");

        order.setUserId(user.getId());
        order.setComment(NEW_ORDER_COMMENT);
        order.setStatusId(STATUS_FORMED_ID);
        Calendar calendar = Calendar.getInstance();
        Date currentDate = calendar.getTime();
        order.setDate(currentDate);

        if (orderService.newOrder(order) == Status.OK) {
            session.removeAttribute("cart");
            session.removeAttribute("pageBean");
            LOG.trace(NEW_ORDER_SUCCESSFUL + Path.SUCCESS_SERVLET);
            session.setAttribute("successInfo", String.valueOf(order.getId()));
            response.sendRedirect(Path.SUCCESS_SERVLET);
            return;
        }

        LOG.trace(ORDER_CREATION_ERROR + Path.ERROR_JSP);
        response.sendRedirect(Path.ERROR_JSP);
    }
}
