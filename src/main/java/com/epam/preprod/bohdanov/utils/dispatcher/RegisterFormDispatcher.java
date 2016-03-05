package com.epam.preprod.bohdanov.utils.dispatcher;

import static com.epam.preprod.bohdanov.model.constants.RegisterFormBeanConstants.CAPTCHA;
import static com.epam.preprod.bohdanov.model.constants.RegisterFormBeanConstants.DELIVERY;
import static com.epam.preprod.bohdanov.model.constants.RegisterFormBeanConstants.EMAIL;
import static com.epam.preprod.bohdanov.model.constants.RegisterFormBeanConstants.FACULTY;
import static com.epam.preprod.bohdanov.model.constants.RegisterFormBeanConstants.FIRST_NAME;
import static com.epam.preprod.bohdanov.model.constants.RegisterFormBeanConstants.LAST_NAME;
import static com.epam.preprod.bohdanov.model.constants.RegisterFormBeanConstants.LOGIN;
import static com.epam.preprod.bohdanov.model.constants.RegisterFormBeanConstants.PASSWORD;
import static com.epam.preprod.bohdanov.model.constants.RegisterFormBeanConstants.PASSWORD_CONFIRM;
import static com.epam.preprod.bohdanov.model.constants.RegisterFormBeanConstants.PHONE;

import javax.servlet.http.HttpServletRequest;

import com.epam.preprod.bohdanov.model.bean.RegisterFormBean;
import com.epam.preprod.bohdanov.model.entity.Faculty;
import com.epam.preprod.bohdanov.model.entity.User;

public class RegisterFormDispatcher implements Dispatcher<RegisterFormBean> {

    @Override
    public Object toEntity(RegisterFormBean sourse) {
        User user = new User();

        user.setFirstName(sourse.getFirstName());
        user.setLastName(sourse.getLastName());
        user.setEmail(sourse.getEmail());
        user.setPhone(sourse.getPhone());
        user.setLogin(sourse.getLogin());
        user.setPassword(sourse.getPassword());
        user.setDelivery(Boolean.parseBoolean(sourse.getDelivery()));
        user.setFaculty(Faculty.getFacultyByValue(Integer.parseInt(sourse.getFaculty())));
        user.setImage(sourse.getImage());

        return user;
    }

    @Override
    public RegisterFormBean fromRequest(HttpServletRequest request) {
        RegisterFormBean form = new RegisterFormBean();
        form.setFirstName(request.getParameter(FIRST_NAME));
        form.setLastName(request.getParameter(LAST_NAME));
        form.setEmail(request.getParameter(EMAIL));
        form.setPhone(request.getParameter(PHONE));
        form.setLogin(request.getParameter(LOGIN));
        form.setPassword(request.getParameter(PASSWORD));
        form.setPasswordConfirm(request.getParameter(PASSWORD_CONFIRM));
        form.setFaculty(request.getParameter(FACULTY));
        form.setCaptcha(request.getParameter(CAPTCHA));
        form.setDelivery(request.getParameter(DELIVERY));
        return form;
    }

}
