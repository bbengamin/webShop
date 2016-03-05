package com.epam.preprod.bohdanov.controller.servlet;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;

import com.epam.preprod.bohdanov.controller.Path;

@WebServlet("/success")
public class Success extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private static final Logger LOG = Logger.getLogger(Success.class);

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession();
        String info = (String) session.getAttribute("successInfo");
        if (StringUtils.isBlank(info)) {
            response.sendError(HttpServletResponse.SC_NOT_FOUND);
            return;
        }
        session.removeAttribute("info");
        request.setAttribute("info", info);
        String forward = Path.SUCCESS_JSP;
        RequestDispatcher rd = request.getRequestDispatcher(forward);
        LOG.trace("Forward to : " + forward);
        rd.forward(request, response);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        doGet(request, response);
    }

}
