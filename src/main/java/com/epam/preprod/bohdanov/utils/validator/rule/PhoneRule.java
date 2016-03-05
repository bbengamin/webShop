package com.epam.preprod.bohdanov.utils.validator.rule;

public class PhoneRule extends ValidationRule {
    public final static String PHONE_REGEX = "^[0-9]{10}$";
    public final static String REGEX_ERROR = "Please enter correct phone";
    public final static String BLANK_ERROR = "This field is required";

    @Override
    public String validate(Object value) {
        if (checkForBlank(String.valueOf(value))) {
            return BLANK_ERROR;
        }
        if (checkWithRegExp(String.valueOf(value), PHONE_REGEX)) {
            return REGEX_ERROR;
        }
        return null;
    }

}
