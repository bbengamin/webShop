package com.epam.preprod.bohdanov.model.bean;

import static com.epam.preprod.bohdanov.model.constants.RegisterFormBeanConstants.*;

import com.epam.preprod.bohdanov.utils.validator.annotation.Validate;
import com.epam.preprod.bohdanov.utils.validator.rule.EmailRule;
import com.epam.preprod.bohdanov.utils.validator.rule.LoginRule;
import com.epam.preprod.bohdanov.utils.validator.rule.NameRule;
import com.epam.preprod.bohdanov.utils.validator.rule.PasswordRule;
import com.epam.preprod.bohdanov.utils.validator.rule.PhoneRule;

public class RegisterFormBean {
    @Validate(ruleClass = NameRule.class, errorName = FIRST_NAME)
    private String firstName;
    private String lastName;
    @Validate(ruleClass = EmailRule.class, errorName = EMAIL)
    private String email;
    @Validate(ruleClass = PhoneRule.class, errorName = PHONE)
    private String phone;
    private String faculty;
    @Validate(ruleClass = LoginRule.class, errorName = LOGIN)
    private String login;
    @Validate(ruleClass = PasswordRule.class, errorName = PASSWORD)
    private String password;
    private String passwordConfirm;
    private String captcha;
    private String delivery;
    private String image;

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getFaculty() {
        return faculty;
    }

    public void setFaculty(String faculty) {
        this.faculty = faculty;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPasswordConfirm() {
        return passwordConfirm;
    }

    public void setPasswordConfirm(String passwordConfirm) {
        this.passwordConfirm = passwordConfirm;
    }

    public String getCaptcha() {
        return captcha;
    }

    public void setCaptcha(String captcha) {
        this.captcha = captcha;
    }

    public String getDelivery() {
        return delivery;
    }

    public void setDelivery(String delivery) {
        this.delivery = delivery;
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("RegisterFormBean [firstName=");
        builder.append(firstName);
        builder.append(", lastName=");
        builder.append(lastName);
        builder.append(", email=");
        builder.append(email);
        builder.append(", phone=");
        builder.append(phone);
        builder.append(", faculty=");
        builder.append(faculty);
        builder.append(", login=");
        builder.append(login);
        builder.append(", password=");
        builder.append(password);
        builder.append(", passwordConfirm=");
        builder.append(passwordConfirm);
        builder.append(", captcha=");
        builder.append(captcha);
        builder.append(", delivery=");
        builder.append(delivery);
        builder.append(", image=");
        builder.append(image);
        builder.append("]");
        return builder.toString();
    }

}
