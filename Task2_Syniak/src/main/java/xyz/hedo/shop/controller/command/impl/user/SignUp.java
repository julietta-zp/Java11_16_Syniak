package xyz.hedo.shop.controller.command.impl.user;

import xyz.hedo.shop.bean.User;
import xyz.hedo.shop.controller.command.Command;
import xyz.hedo.shop.controller.command.CustomUrlHelper;
import xyz.hedo.shop.service.UserService;
import xyz.hedo.shop.service.exception.ServiceException;
import xyz.hedo.shop.service.factory.ServiceFactory;

import java.io.UnsupportedEncodingException;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

public class SignUp implements Command {

    private static final Logger logger = Logger.getLogger(String.valueOf(SignUp.class));

    @Override
    public String execute(String request) {
        String response = null;
        String firstName = null;
        String lastName = null;
        String email = null;
        String password = null;

        // get parameters from request and initialize the variable
        try{
            Map<String, String> parameters = CustomUrlHelper.splitQuery(request);
            for (String key : parameters.keySet()) {
                switch (key){
                    case PARAMETER_FIRST_NAME:
                        firstName = parameters.get(key);
                        break;
                    case PARAMETER_LAST_NAME:
                        lastName = parameters.get(key);
                        break;
                    case PARAMETER_EMAIL:
                        email = parameters.get(key);
                        break;
                    case PARAMETER_PASSWORD:
                        password = parameters.get(key);
                        break;
                }
            }

            ServiceFactory serviceFactory = ServiceFactory.getInstance();
            UserService userService = serviceFactory.getUserService();

            try {

                User user = new User();
                user.setFirstName(firstName);
                user.setLastName(lastName);
                user.setEmail(email);
                user.setPassword(password);
                userService.signUp(user);
                response = "Success";

            } catch (ServiceException e) {
                // write log
                logger.log(Level.SEVERE, e.getMessage(), e);
                response = "Error. Cannot create user.";
            }

        }catch (UnsupportedEncodingException e){
            response = "Error. Wrong parameters were sent. Cannot create user.";
        }

        return response;
    }
}
