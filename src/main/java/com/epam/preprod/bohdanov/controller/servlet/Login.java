package com.epam.preprod.bohdanov.controller.servlet;

import java.io.IOException;
import java.util.Map;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;

import com.epam.preprod.bohdanov.controller.Path;
import com.epam.preprod.bohdanov.model.bean.LoginFormBean;
import com.epam.preprod.bohdanov.model.entity.User;
import com.epam.preprod.bohdanov.service.UserService;
import com.epam.preprod.bohdanov.utils.validator.AnnotationValidator;

public class Login extends HttpServlet {
    private static final Logger LOG = Logger.getLogger(Login.class);
    private static final long serialVersionUID = 1L;
    private UserService userService;

    private final String NO_SUCH_USER = "No such user";

    public void init(ServletConfig servletConfig) throws ServletException {
        ServletContext context = servletConfig.getServletContext();

        userService = (UserService) context.getAttribute("userService");
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession();
        request.setAttribute("form", session.getAttribute("form"));
        request.setAttribute("errors", session.getAttribute("errors"));
        LOG.trace("form: " + session.getAttribute("form"));
        LOG.trace("errors: " + session.getAttribute("errors"));
        session.removeAttribute("form");
        session.removeAttribute("errors");
        RequestDispatcher rd = request.getRequestDispatcher(Path.LOGIN_JSP);
        rd.forward(request, response);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        LoginFormBean form = new LoginFormBean(request.getParameter("email"), request.getParameter("password"));
        AnnotationValidator validator = new AnnotationValidator(form);
        Map<String, String> errors = validator.validate();

        if (errors.isEmpty()) {
            User user = (User) userService.login(form.getEmail(), form.getPassword());
            if (user != null) {
                user.setPassword(null);
                HttpSession session = request.getSession();
                session.setAttribute("isLogin", true);
                session.setAttribute("user", user);
                if (StringUtils.isNotBlank(request.getParameter("action"))) {
                    response.sendRedirect(request.getParameter("action"));
                } else {
                    response.sendRedirect(Path.CATEGORY_SERVLET);
                }
                return;
            } else {
                errors.put("login_error", NO_SUCH_USER);
            }
        }
        HttpSession session = request.getSession();
        session.setAttribute("errors", errors);
        session.setAttribute("form", form);
        response.sendRedirect(Path.LOGIN_SERVLET);
    }

}
