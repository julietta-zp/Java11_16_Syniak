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

public class SignOut implements Command {

    private static final Logger logger = Logger.getLogger(String.valueOf(SignOut.class));

    @Override
    public String execute(String request) {
        String email = null;

        String response = null;

        // get parameters from request and initialize the variable
        try{
            Map<String, String> parameters = CustomUrlHelper.splitQuery(request);
            for (String key : parameters.keySet()) {
                if (key.equals(PARAMETER_EMAIL)){
                    email = parameters.get(key);
                }
            }

            ServiceFactory serviceFactory = ServiceFactory.getInstance();
            UserService userService = serviceFactory.getUserService();

            try {
                userService.signOut(email);
                response = "Bye!";

            } catch (ServiceException e) {
                // write log
                logger.log(Level.SEVERE, e.getMessage(), e);
                response = "Error. Cannot sign out user.";
            }

        }catch (UnsupportedEncodingException e){
            response = "Error. Wrong parameters were sent. Cannot sign out user.";
        }

        return response;
    }
}
