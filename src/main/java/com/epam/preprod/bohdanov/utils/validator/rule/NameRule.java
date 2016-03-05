package com.epam.preprod.bohdanov.utils.validator.rule;

public class NameRule extends ValidationRule {

    @Override
    public String validate(Object value) {
        if (checkForBlank(String.valueOf(value))) {
            return BLANK_ERROR;
        }
        return null;
    }

}
