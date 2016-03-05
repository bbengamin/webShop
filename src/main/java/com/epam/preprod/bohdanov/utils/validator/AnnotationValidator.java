package com.epam.preprod.bohdanov.utils.validator;

import java.lang.reflect.Field;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.Logger;

import com.epam.preprod.bohdanov.utils.validator.annotation.Validate;
import com.epam.preprod.bohdanov.utils.validator.rule.ValidationRule;

public class AnnotationValidator {
    private static final String VALIDATION_ERROR = "Validation error";
    private static final Logger LOG = Logger.getLogger(AnnotationValidator.class);
    private Object target;

    public AnnotationValidator(Object target) {
        this.target = target;
    }

    public Map<String, String> validate() {
        Map<String, String> errors = new HashMap<String, String>();
        Class<? extends Object> objectClass = target.getClass();
        try {
            for (Field field : objectClass.getDeclaredFields()) {
                if (field.isAnnotationPresent(Validate.class)) {
                    Validate annotation = field.getAnnotation(Validate.class);

                    ValidationRule rule = annotation.ruleClass().newInstance();
                    field.setAccessible(true);
                    String message = rule.validate(field.get(target));

                    if (message != null) {
                        errors.put(annotation.errorName(), message);
                    }
                }
            }
        } catch (IllegalAccessException | InstantiationException e) {
            LOG.error(VALIDATION_ERROR, e);
            return Collections.emptyMap();
        }

        return errors;
    }
}
