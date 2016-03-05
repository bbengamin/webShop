package com.epam.preprod.bohdanov.utils.validator.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import com.epam.preprod.bohdanov.utils.validator.rule.ValidationRule;

@Target(value = ElementType.FIELD)
@Retention(value = RetentionPolicy.RUNTIME)
public @interface Validate {
    Class<? extends ValidationRule> ruleClass();

    String errorName();
}
