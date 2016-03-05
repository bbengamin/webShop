package com.epam.preprod.bohdanov.utils;

import java.util.LinkedHashMap;
import java.util.Map;

import com.epam.preprod.bohdanov.controller.Path;
import com.epam.preprod.bohdanov.utils.builder.URLBuilder;
import com.epam.preprod.bohdanov.utils.filter.Condition;

public class Pager {
    private static final String PAGE_PARAM_NAME = "&page=";
    private final int limit = 2;
    private final String PREV_TEXT = "Prev";
    private final String NEXT_TEXT = "Next";

    private int totalCountOfProducts;
    private Condition filterCondition;

    public Pager(int totalCountOfProducts, Condition filterCondition) {
        this.totalCountOfProducts = totalCountOfProducts;
        this.filterCondition = filterCondition;
    }

    public Map<String, String> generatePagination() {
        int totalPages = calculatePageCount(totalCountOfProducts, filterCondition.getLimit());
        Map<String, String> pages = new LinkedHashMap<>();

        if (totalPages == 1) {
            return pages;
        }
        URLBuilder urlBuilder = fillBuilderFromCondition(filterCondition);

        if (filterCondition.getPage() > 1) {
            pages.put(PREV_TEXT, urlBuilder.getUrl() + PAGE_PARAM_NAME + (filterCondition.getPage() - 1));
        }

        int pageCount = 2 * limit + 1;
        int left = Math.max(2, filterCondition.getPage() - 2 * limit - 1);
        int right = Math.min(totalPages - 1, filterCondition.getPage() + 2 * limit + 1);

        while (right - left > 2 * limit) {
            if (filterCondition.getPage() - left < right - filterCondition.getPage()) {
                right--;
                right = right < filterCondition.getPage() ? filterCondition.getPage() : right;
            } else {
                left++;
                left = left > filterCondition.getPage() ? filterCondition.getPage() : left;
            }
        }

        pages.put(String.valueOf(1), urlBuilder.getUrl() + PAGE_PARAM_NAME + 1);
        for (int i = 1, out = left; i <= pageCount && out <= right; i++, out++) {
            pages.put(String.valueOf(out), urlBuilder.getUrl() + PAGE_PARAM_NAME + out);
        }
        pages.put(String.valueOf(totalPages), urlBuilder.getUrl() + PAGE_PARAM_NAME + totalPages);

        if (filterCondition.getPage() < totalPages) {
            pages.put(NEXT_TEXT, urlBuilder.getUrl() + PAGE_PARAM_NAME + (filterCondition.getPage() + 1));
        }
        return pages;
    }

    private int calculatePageCount(int countOfProduct, int limit) {
        if (countOfProduct >= limit) {
            if (countOfProduct % limit == 0) {
                return countOfProduct / limit;
            } else {
                return countOfProduct / limit + 1;
            }
        } else {
            return 1;
        }
    }

    private URLBuilder fillBuilderFromCondition(Condition filterCondition) {
        URLBuilder urlBuilder = new URLBuilder(Path.CATEGORY_SERVLET);
        urlBuilder.fillFilterStateFromCondition(filterCondition);
        urlBuilder.fillOrdersFromCondition(filterCondition);
        urlBuilder.fillLimitsFromCondition(filterCondition);
        return urlBuilder;

    }
}