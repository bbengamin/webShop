package com.epam.preprod.bohdanov.utils.builder;

import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;

import com.epam.preprod.bohdanov.utils.filter.Condition;
import static com.epam.preprod.bohdanov.utils.filter.Condition.*;

public class URLBuilder {
    private StringBuilder builder;

    public URLBuilder(String servletName) {
        builder = new StringBuilder(servletName + "?");
    }

    public URLBuilder addParameter(String paramName, String paramValue) {
        if (StringUtils.isNotBlank(paramValue)) {
            builder.append(paramName).append("=").append(paramValue).append("&");
        }
        return this;
    }

    public URLBuilder addParameters(String paramName, String[] paramValue) {
        if (ArrayUtils.isNotEmpty(paramValue)) {
            for (String item : paramValue) {
                builder.append(paramName).append("=").append(item).append("&");
            }
        }
        return this;
    }

    public URLBuilder fillFilterStateFromCondition(Condition condition) {
        addParameter(NAME, condition.getName());
        addParameters(CATEGORY, condition.getCategory());
        addParameters(MANUFACTURER, condition.getManufacturer());
        addParameter(PRICE_FROM, condition.getPriceFrom());
        addParameter(PRICE_TO, condition.getPriceTo());
        return this;
    }

    public URLBuilder fillOrdersFromCondition(Condition condition) {
        addParameter(ORDER, condition.getOrder());
        addParameter(SORT, condition.getSort());
        return this;
    }

    public URLBuilder fillLimitsFromCondition(Condition condition) {
        addParameter(LIMIT, String.valueOf(condition.getLimit()));
        return this;
    }

    public URLBuilder fillPageFromCondition(Condition condition) {
        addParameter(PAGE, String.valueOf(condition.getPage()));
        return this;
    }

    public String getUrl() {
        return builder.substring(0, builder.length() - 1);
    }
}
