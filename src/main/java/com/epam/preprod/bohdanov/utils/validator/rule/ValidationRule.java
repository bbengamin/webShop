package com.epam.preprod.bohdanov.utils.validator.rule;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang3.StringUtils;

public abstract class ValidationRule {
    protected final static String BLANK_ERROR = "This field is required";

    public abstract String validate(Object value);

    protected boolean checkWithRegExp(String value, String regex) {
        Pattern p = Pattern.compile(regex);
        Matcher m = p.matcher(value);
        return !m.matches();
    }

    protected boolean checkForBlank(String value) {
        return StringUtils.isBlank(value);
    }
}
