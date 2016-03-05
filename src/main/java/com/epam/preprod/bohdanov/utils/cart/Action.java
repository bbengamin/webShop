package com.epam.preprod.bohdanov.utils.cart;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.json.JSONObject;

import com.epam.preprod.bohdanov.model.bean.CartBean;

public abstract class Action {
    private static final String RETURN_ANSWER_ERROR = "Return answer error";
    private static final Logger LOG = Logger.getLogger(Action.class);

    public void doAction(HttpServletRequest request, HttpServletResponse response) {
        HttpSession session = request.getSession();
        CartBean cart = CartBean.getInstance(session);
        JSONObject answer = updateCart(request, cart);
        outResultToResponse(response, answer);
    }

    private void outResultToResponse(HttpServletResponse response, JSONObject result) {
        try {
            response.setContentType("application/json");
            PrintWriter out = response.getWriter();
            out.println(result);
            out.close();
        } catch (IOException e) {
            LOG.error(RETURN_ANSWER_ERROR, e);
        }
    }

    public abstract JSONObject updateCart(HttpServletRequest request, CartBean cart);
}
