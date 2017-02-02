package xyz.hedo.shop.controller.command.impl.category;

import xyz.hedo.shop.bean.Category;
import xyz.hedo.shop.controller.command.Command;
import xyz.hedo.shop.service.ShopService;
import xyz.hedo.shop.service.exception.ServiceException;
import xyz.hedo.shop.service.factory.ServiceFactory;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ViewAllCategories implements Command {

    private static final Logger logger = Logger.getLogger(String.valueOf(ViewAllCategories.class));

    @Override
    public String execute(String request) {
        String response = null;

        // get parameters from request and initialize the variable

        ServiceFactory serviceFactory = ServiceFactory.getInstance();
        ShopService shopService = serviceFactory.getShopService();

        try {

            List<Category> categories = shopService.getAllCategories();
            StringBuilder sb = new StringBuilder();
            for (Category category : categories) {
                sb.append(category.getName()).append(',');
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
