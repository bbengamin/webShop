package com.epam.preprod.bohdanov.utils.builder;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;

public class SQLSelectBuilder {
    private static final String OVER_STRING = " >= ? ";
    private static final String EQUALS_STRING = " = ? ";
    private static final String LESS_STRING = " <= ? ";
    private static final String LEFT_BRACKET = "(";
    private static final String RIGHT_BRACKET = ")";
    private static final String LIKE_STRING = " LIKE '%' ? '%'";
    private static final String COMMA_STRING = ",";
    private static final String QUESTION_MARK_STRING = "?";
    private static final String SPACE_STRING = " ";
    private static final String LIMIT_STRING = " LIMIT ?, ?";
    private static final String ORDER_BY_STRING = " ORDER BY ";
    private static final String QUOTE_STRING = "`";
    private static final String FROM_STRING = " FROM ";
    private static final String SELECT_STRING = "SELECT ";
    private static final String AND_STRING = " AND ";
    private static final String WHERE_STRING = " WHERE ";
    private StringBuilder builder;
    private List<Object> params;

    public SQLSelectBuilder(String selectParam, String table) {
        builder = new StringBuilder(SELECT_STRING).append(selectParam).append(FROM_STRING + QUOTE_STRING).append(table)
                .append(QUOTE_STRING);
        params = new ArrayList<>();
    }

    public SQLSelectBuilder addLess(String fieldName, String param) {
        if (StringUtils.isNotBlank(param)) {
            if (params.isEmpty()) {
                builder.append(WHERE_STRING);
            } else {
                builder.append(AND_STRING);
            }
            builder.append(QUOTE_STRING).append(fieldName).append(QUOTE_STRING + LESS_STRING);
            params.add(param);
        }
        return this;
    }

    public SQLSelectBuilder addEquals(String fieldName, String param) {
        if (StringUtils.isNotBlank(param)) {
            if (params.isEmpty()) {
                builder.append(WHERE_STRING);
            } else {
                builder.append(AND_STRING);
            }
            builder.append(QUOTE_STRING).append(fieldName).append(QUOTE_STRING + EQUALS_STRING);
            params.add(param);
        }
        return this;
    }

    public SQLSelectBuilder addOver(String fieldName, String param) {
        if (StringUtils.isNotBlank(param)) {
            if (params.isEmpty()) {
                builder.append(WHERE_STRING);
            } else {
                builder.append(AND_STRING);
            }
            builder.append(QUOTE_STRING).append(fieldName).append(QUOTE_STRING + OVER_STRING);
            params.add(param);
        }
        return this;
    }

    public SQLSelectBuilder addLimit(Integer page, Integer limit) {
        if (page == 0) {
            page = 1;
        }
        if (limit == 0) {
            limit = 10;
        }
        int currentPage = (page - 1) * limit;
        builder.append(LIMIT_STRING);
        params.add(currentPage);
        params.add(limit);
        return this;
    }

    public SQLSelectBuilder addOrder(String fieldName, String order) {
        builder.append(ORDER_BY_STRING).append(fieldName).append(SPACE_STRING).append(order);
        return this;
    }

    public SQLSelectBuilder addIn(String fieldName, String[] param) {
        if (ArrayUtils.isNotEmpty(param)) {
            if (params.isEmpty()) {
                builder.append(WHERE_STRING);
            } else {
                builder.append(AND_STRING);
            }
            builder.append(QUOTE_STRING).append(fieldName).append(QUOTE_STRING + " IN" + LEFT_BRACKET);
            for (int i = 0; i < param.length; i++) {
                if (i != 0) {
                    builder.append(COMMA_STRING);
                }
                builder.append(QUESTION_MARK_STRING);
                params.add(param[i]);
            }
            builder.append(RIGHT_BRACKET);
        }
        return this;
    }

    public SQLSelectBuilder addLike(String fieldName, String param) {
        if (StringUtils.isNotBlank(param)) {
            if (params.isEmpty()) {
                builder.append(WHERE_STRING);
            } else {
                builder.append(AND_STRING);
            }
            builder.append(QUOTE_STRING).append(fieldName).append(QUOTE_STRING + LIKE_STRING);
            params.add(param);
        }
        return this;
    }

    public List<Object> getParameters() {
        return params;
    }

    @Override
    public String toString() {
        return builder.toString().trim();
    }

}
