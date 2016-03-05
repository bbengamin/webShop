package com.epam.preprod.bohdanov.controller.servlet;

import java.io.IOException;
import java.util.Map;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;

import com.epam.preprod.bohdanov.controller.Path;
import com.epam.preprod.bohdanov.model.bean.RegisterFormBean;
import com.epam.preprod.bohdanov.model.constants.RegisterFormBeanConstants;
import com.epam.preprod.bohdanov.model.entity.Status;
import com.epam.preprod.bohdanov.model.entity.User;
import com.epam.preprod.bohdanov.service.UserService;
import com.epam.preprod.bohdanov.utils.FileManager;
import com.epam.preprod.bohdanov.utils.captcha.CaptchaContainer;
import com.epam.preprod.bohdanov.utils.captcha.CaptchaContainer.UserCaptcha;
import com.epam.preprod.bohdanov.utils.captcha.manager.CaptchaManager;
import com.epam.preprod.bohdanov.utils.dispatcher.Dispatcher;
import com.epam.preprod.bohdanov.utils.dispatcher.RegisterFormDispatcher;
import com.epam.preprod.bohdanov.utils.validator.AnnotationValidator;

@WebServlet("/register")
@MultipartConfig(fileSizeThreshold = 1024 * 1024 * 2, maxFileSize = 1024 * 1024 * 10, maxRequestSize = 1024 * 1024 * 50)
public class Register extends HttpServlet {
    private static final Logger LOG = Logger.getLogger(Register.class);

    private static final long serialVersionUID = 1L;
    private CaptchaContainer captchaContainer;
    private UserService userService;
    private CaptchaManager captchaManager;
    private FileManager fileManager;

    private final String BAD_CAPTCHA_MESSAGE = "Incorrect captcha";
    private final String USER_EXIST_MESSAGE = "User with this email already exist";
    private final String EMPTY_PASSWORD_ERROR_MESSAGE = "Please confirm password";
    private final String NOT_EQUALS_PASSWORDS_ERROR_MESSAGE = "Passwords are not equals";

    public void init(ServletConfig servletConfig) throws ServletException {
        LOG.trace("Register servlet start");
        ServletContext context = servletConfig.getServletContext();

        String uploadPath = (String) context.getAttribute("ImagePath");
        String appPath = context.getRealPath("");
        fileManager = new FileManager(uploadPath, appPath);

        captchaContainer = (CaptchaContainer) context.getAttribute("captchaContainer");
        userService = (UserService) context.getAttribute("userService");
        captchaManager = (CaptchaManager) context.getAttribute("captchaManager");
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String captchaKey = captchaContainer.generateCaptcha();
        LOG.info("New captcha key: " + captchaKey);
        captchaManager.setKey(request, response, captchaKey);

        String forward = Path.REGISTER_JSP;
        RequestDispatcher rd = request.getRequestDispatcher(forward);
        LOG.trace("Forward to : " + forward);
        rd.forward(request, response);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        LOG.info("Regiseter start");
        Dispatcher<RegisterFormBean> registerFormDispatcher = new RegisterFormDispatcher();
        RegisterFormBean form = registerFormDispatcher.fromRequest(request);
        LOG.trace("Form bean: " + form.toString());
        Map<String, String> errors = validateForm(form);

        if (!validateCapthca(captchaManager.getKey(request, response), form.getCaptcha())) {
            errors.put("captcha", BAD_CAPTCHA_MESSAGE);
        }

        if (errors.isEmpty()) {
            form.setImage(fileManager.loadImage(request));
            User user = (User) registerFormDispatcher.toEntity(form);
            if (userService.newUser(user) == Status.OK) {
                LOG.trace("Register success! Forward to: " + Path.CATEGORY_SERVLET);
                response.sendRedirect(Path.CATEGORY_SERVLET);
                return;
            } else {
                errors.put("email", USER_EXIST_MESSAGE);
            }
        }
        fileManager.removeImage(form.getImage());
        HttpSession session = request.getSession();
        session.setAttribute("errors", errors);
        session.setAttribute("registerForm", form);
        LOG.trace("Register error! Errors: " + errors);
        LOG.trace("Redirect to: " + Path.REGISTER_SERVLET);
        response.sendRedirect(Path.REGISTER_SERVLET);
    }

    private Map<String, String> validateForm(RegisterFormBean form) {
        AnnotationValidator validator = new AnnotationValidator(form);
        Map<String, String> errors = validator.validate();

        if (StringUtils.isEmpty(form.getPasswordConfirm())) {
            errors.put(RegisterFormBeanConstants.PASSWORD_CONFIRM, EMPTY_PASSWORD_ERROR_MESSAGE);
        } else if (form.getPassword().compareTo(form.getPasswordConfirm()) != 0) {
            errors.put(RegisterFormBeanConstants.PASSWORD_CONFIRM, NOT_EQUALS_PASSWORDS_ERROR_MESSAGE);
        }

        return errors;
    }

    private boolean validateCapthca(String key, String captchaValue) {
        if (StringUtils.isEmpty(key) || StringUtils.isEmpty(captchaValue)) {
            return false;
        }

        UserCaptcha userCaptcha = captchaContainer.getCaptcha(key);
        if (userCaptcha != null) {
            String expected = String.valueOf(userCaptcha.getValue());
            if (expected.compareTo(captchaValue) != 0 && userCaptcha.isAlive()) {
                userCaptcha.invalidateCaptcha();
                return false;
            }
            return true;
        } else {
            return false;
        }
    }
}
