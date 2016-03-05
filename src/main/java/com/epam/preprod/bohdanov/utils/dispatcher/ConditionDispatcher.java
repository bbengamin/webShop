package com.epam.preprod.bohdanov.utils.dispatcher;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;

import com.epam.preprod.bohdanov.utils.filter.Condition;
import static com.epam.preprod.bohdanov.utils.filter.Condition.*;

public class ConditionDispatcher implements Dispatcher<Condition> {

    @Override
    public Object toEntity(Condition sourse) {
        throw new UnsupportedOperationException();
    }

    @Override
    public Condition fromRequest(HttpServletRequest request) {
        Condition condition = new Condition();
        if (!StringUtils.isEmpty(request.getParameter(NAME))) {
            condition.setName(request.getParameter(NAME));
        }
        if (!StringUtils.isEmpty(request.getParameter(CATEGORY))) {
            condition.setCategory(request.getParameterValues(CATEGORY));
        }
        if (!StringUtils.isEmpty(request.getParameter(MANUFACTURER))) {
            condition.setManufacturer(request.getParameterValues(MANUFACTURER));
        }
        if (!StringUtils.isEmpty(request.getParameter(PRICE_FROM))) {
            condition.setPriceFrom(request.getParameter(PRICE_FROM));
        }
        if (!StringUtils.isEmpty(request.getParameter(PRICE_TO))) {
            condition.setPriceTo(request.getParameter(PRICE_TO));
        }
        if (!StringUtils.isEmpty(request.getParameter(LIMIT))) {
            condition.setLimit(Integer.parseInt(request.getParameter(LIMIT)));
        }
        if (!StringUtils.isEmpty(request.getParameter(PAGE))) {
            condition.setPage(Integer.parseInt(request.getParameter(PAGE)));
        }
        if (!StringUtils.isEmpty(request.getParameter(SORT))) {
            condition.setSort(request.getParameter(SORT));
        }
        if (!StringUtils.isEmpty(request.getParameter(ORDER))) {
            condition.setOrder(request.getParameter(ORDER));
        }
        return condition;
    }

}
