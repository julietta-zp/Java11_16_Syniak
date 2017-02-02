package xyz.hedo.shop.controller.command.impl.user;

import xyz.hedo.shop.bean.User;
import xyz.hedo.shop.controller.command.Command;
import xyz.hedo.shop.service.UserService;
import xyz.hedo.shop.service.exception.ServiceException;
import xyz.hedo.shop.service.factory.ServiceFactory;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ViewAllUsers implements Command {

    private static final Logger logger = Logger.getLogger(String.valueOf(ViewAllUsers.class));

    @Override
    public String execute(String request) {
        String response = null;

        // get parameters from request and initialize the variable

        ServiceFactory serviceFactory = ServiceFactory.getInstance();
        UserService userService = serviceFactory.getUserService();

        try {

            List<User> users = userService.getAllUsers();
            StringBuilder sb = new StringBuilder();
            for (User user : users) {
                sb.append(user.getLastName()).append(" ").append(user.getFirstName()).append(System.lineSeparator());
            }
            sb.deleteCharAt(sb.length()-1);
            response = sb.toString();

        } catch (ServiceException e) {
            // write log
            logger.log(Level.SEVERE, e.getMessage(), e);
            response = "Error during procedure.";
        }

        return response;
    }
}
