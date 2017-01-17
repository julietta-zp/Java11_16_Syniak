package xyz.hedo.shop.controller.command.impl.user;

import xyz.hedo.shop.controller.command.Command;
import xyz.hedo.shop.controller.command.CustomUrlHelper;
import xyz.hedo.shop.service.UserService;
import xyz.hedo.shop.service.exception.ServiceException;
import xyz.hedo.shop.service.factory.ServiceFactory;

import java.io.UnsupportedEncodingException;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

public class SignIn implements Command {

    private static final Logger logger = Logger.getLogger(String.valueOf(SignIn.class));

    @Override
    public String execute(String request) {
        String email = null;
        String password = null;

        String response = null;

        // get parameters from request and initialize the variable
        try{
            Map<String, String> parameters = CustomUrlHelper.splitQuery(request);
            for (String key : parameters.keySet()) {
                if (key.equals("email")){
                    email = parameters.get(key);
                } else if (key.equals("password")){
                    password = parameters.get(key);
                }
            }

            ServiceFactory serviceFactory = ServiceFactory.getInstance();
            UserService userService = serviceFactory.getUserService();

            try {
                userService.signIn(email, password);
                response = "Welcome!";

            } catch (ServiceException e) {
                // write log
                logger.log(Level.SEVERE, e.getMessage());
                response = "Error. Cannot sign in user.";
            }

        }catch (UnsupportedEncodingException e){
            response = "Error. Wrong parameters were sent. Cannot sign in user.";
        }

        return response;
    }
}
