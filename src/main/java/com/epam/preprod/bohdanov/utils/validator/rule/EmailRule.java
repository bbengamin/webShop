package com.epam.preprod.bohdanov.utils.validator.rule;

public class EmailRule extends ValidationRule {
    public final static String EMAIL_REGEX = "^(([a-zA-Z]|[0-9])|([-]|[_]|[.]))+[@](([a-zA-Z0-9])|([-])){2,63}[.](([a-zA-Z0-9]){2,63})+$";
    public final static String REGEX_ERROR = "Please enter correct email";

    @Override
    public String validate(Object value) {
        if (checkForBlank(String.valueOf(value))) {
            return BLANK_ERROR;
        }
        if (checkWithRegExp(String.valueOf(value), EMAIL_REGEX)) {
            return REGEX_ERROR;
        }
        return null;
    }

}
