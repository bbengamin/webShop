package com.epam.preprod.bohdanov.model.bean;

import static com.epam.preprod.bohdanov.model.constants.RegisterFormBeanConstants.EMAIL;
import static com.epam.preprod.bohdanov.model.constants.RegisterFormBeanConstants.PASSWORD;

import com.epam.preprod.bohdanov.utils.validator.annotation.Validate;
import com.epam.preprod.bohdanov.utils.validator.rule.EmailRule;
import com.epam.preprod.bohdanov.utils.validator.rule.PasswordRule;

public class LoginFormBean {
    @Validate(ruleClass = EmailRule.class, errorName = EMAIL)
    private String email;
    @Validate(ruleClass = PasswordRule.class, errorName = PASSWORD)
    private String password;

    public LoginFormBean(String email, String password) {
        this.email = email;
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
