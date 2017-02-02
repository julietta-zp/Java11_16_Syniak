package xyz.hedo.shop.controller.command.impl.category;

import xyz.hedo.shop.bean.Category;
import xyz.hedo.shop.controller.command.Command;
import xyz.hedo.shop.controller.command.CustomUrlHelper;
import xyz.hedo.shop.service.ShopService;
import xyz.hedo.shop.service.exception.ServiceException;
import xyz.hedo.shop.service.factory.ServiceFactory;

import java.io.UnsupportedEncodingException;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

public class CreateCategory implements Command {

    private static final Logger logger = Logger.getLogger(String.valueOf(CreateCategory.class));

    @Override
    public String execute(String request) {

        String name = null;
        String response = null;

        // get parameters from request and initialize the variable
        try{
            Map<String, String> parameters = CustomUrlHelper.splitQuery(request);
            for (String key : parameters.keySet()) {
                if (key.equals(PARAMETER_NAME)){
                    name = parameters.get(key);
                }
            }

            ServiceFactory serviceFactory = ServiceFactory.getInstance();
            ShopService shopService = serviceFactory.getShopService();

            try {

                Category category = new Category();
                category.setName(name);
                shopService.addCategory(category);
                response = "Success";

            } catch (ServiceException e) {
                // write log
                logger.log(Level.SEVERE, e.getMessage(), e);
                response = "Error. Cannot create category.";
            }

        }catch (UnsupportedEncodingException e){
            response = "Error. Wrong parameters were sent. Cannot create category.";
        }

        return response;
    }
}
