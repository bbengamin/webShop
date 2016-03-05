package com.epam.preprod.bohdanov.utils.validator.rule;

public class LoginRule extends ValidationRule {
    public final static String LOGIN_REGEX = "^[a-z0-9]{5,15}$";
    public final static String REGEX_ERROR = "Please enter correct login";
    public final static String BLANK_ERROR = "This field is required";

    @Override
    public String validate(Object value) {
        if (checkForBlank(String.valueOf(value))) {
            return BLANK_ERROR;
        }
        if (checkWithRegExp(String.valueOf(value), LOGIN_REGEX)) {
            return REGEX_ERROR;
        }
        return null;
    }

}
