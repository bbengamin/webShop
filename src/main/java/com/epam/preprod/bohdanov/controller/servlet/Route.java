package com.epam.preprod.bohdanov.controller.servlet;

import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.Map;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import com.epam.preprod.bohdanov.controller.Path;
import com.epam.preprod.bohdanov.model.bean.pages.RoutePageBean;
import com.epam.preprod.bohdanov.model.constants.Sorts;
import com.epam.preprod.bohdanov.service.ItemService;
import com.epam.preprod.bohdanov.utils.Pager;
import com.epam.preprod.bohdanov.utils.builder.URLBuilder;
import com.epam.preprod.bohdanov.utils.dispatcher.ConditionDispatcher;
import com.epam.preprod.bohdanov.utils.dispatcher.Dispatcher;
import com.epam.preprod.bohdanov.utils.filter.Condition;

@WebServlet("/route")
public class Route extends HttpServlet {
    private static final long serialVersionUID = -3500639545777068713L;
    private static final Logger LOG = Logger.getLogger(Route.class);
    private ItemService itemService;

    public void init(ServletConfig servletConfig) throws ServletException {
        LOG.trace("Route servlet start");
        ServletContext context = servletConfig.getServletContext();
        itemService = (ItemService) context.getAttribute("itemService");
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        Dispatcher<Condition> conditionDispatcher = new ConditionDispatcher();
        Condition condition = conditionDispatcher.fromRequest(request);

        RoutePageBean pageBean = itemService.doFilter(condition);

        Pager pager = new Pager(pageBean.getTotalCountOfProducts(), condition);
        pageBean.setPagination(pager.generatePagination());
        pageBean.setLimits(generateLimits(condition));
        pageBean.setSorts(generateSorts(condition));

        request.setAttribute("pageBean", pageBean);
        request.setAttribute("condition", condition);

        String forward = Path.CATEGORY_JSP;
        RequestDispatcher rd = request.getRequestDispatcher(forward);
        LOG.trace("Forward to : " + forward);
        rd.forward(request, response);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        doGet(request, response);
    }

    private Map<Integer, String> generateLimits(Condition condition) {
        URLBuilder urlBuilder = new URLBuilder("route");
        urlBuilder.fillFilterStateFromCondition(condition);
        urlBuilder.fillOrdersFromCondition(condition);
        Map<Integer, String> limits = new LinkedHashMap<>();

        for (int i = 5; i < 100; i += 20) {
            limits.put(i, urlBuilder.getUrl() + "&limit=" + i);
        }
        return limits;
    }

    private Map<Sorts, String> generateSorts(Condition condition) {
        URLBuilder urlBuilder = new URLBuilder("route");
        urlBuilder.fillFilterStateFromCondition(condition);
        urlBuilder.fillPageFromCondition(condition);
        urlBuilder.fillLimitsFromCondition(condition);

        Map<Sorts, String> sorts = new LinkedHashMap<>();
        for (Sorts item : Sorts.values()) {
            sorts.put(item, urlBuilder.getUrl() + "&sort=" + item.getName() + "&order=" + item.getOrder());
        }
        return sorts;
    }
}
