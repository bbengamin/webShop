package com.epam.preprod.bohdanov.utils;

import java.math.BigDecimal;
import java.text.NumberFormat;
import java.util.Locale;

public class PriceUtils {
    public String getLocalizedBigDecimalValue(BigDecimal input, Locale locale) {
        NumberFormat formatter = NumberFormat.getNumberInstance(locale);
        formatter.setGroupingUsed(true);
        formatter.setMaximumFractionDigits(2);
        formatter.setMinimumFractionDigits(2);
        return formatter.format(input);
    }
}
