package com.epam.preprod.bohdanov.utils.validator.rule;

public class PasswordRule extends ValidationRule {
    public final static String PASSWORD_REGEX = "([A-Za-z0-9-]+)";
    public final static String REGEX_ERROR = "Please enter correct password";

    @Override
    public String validate(Object value) {
        if (checkForBlank(String.valueOf(value))) {
            return BLANK_ERROR;
        }
        if (checkWithRegExp(String.valueOf(value), PASSWORD_REGEX)) {
            return REGEX_ERROR;
        }
        return null;
    }

}
