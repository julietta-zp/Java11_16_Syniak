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

public class ViewUserDetails implements Command {

    private static final Logger logger = Logger.getLogger(String.valueOf(ViewUserDetails.class));

    @Override
    public String execute(String request) {
        String response = null;
        int id = 0;

        // get parameters from request and initialize the variable
        try{
            Map<String, String> parameters = CustomUrlHelper.splitQuery(request);
            for (String key : parameters.keySet()) {
                if (key.equals(PARAMETER_ID)){
                    id = Integer.parseInt(parameters.get(key));
                }
            }

            ServiceFactory serviceFactory = ServiceFactory.getInstance();
            UserService userService = serviceFactory.getUserService();

            try {

                User user = userService.getUserById(id);
                StringBuilder sb = new StringBuilder();
                sb.append(user.getFirstName()).append(" ").append(user.getLastName());
                sb.append(" ");
                sb.append(user.getEmail());
                response = sb.toString();

            } catch (ServiceException e) {
                // write log
                logger.log(Level.SEVERE, e.getMessage(), e);
                response = "Error. Cannot get user.";
            }

        }catch (UnsupportedEncodingException e){
            response = "Error. Wrong parameters were sent.";
        }

        return response;
    }
}
