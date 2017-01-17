package xyz.hedo.shop.controller.command.impl.category;

import xyz.hedo.shop.controller.command.Command;
import xyz.hedo.shop.controller.command.CustomUrlHelper;
import xyz.hedo.shop.service.ShopService;
import xyz.hedo.shop.service.exception.ServiceException;
import xyz.hedo.shop.service.factory.ServiceFactory;

import java.io.UnsupportedEncodingException;
import java.util.Map;

public class DeleteCategory implements Command {

    @Override
    public String execute(String request) {
        String response = null;
        int id = 0;

        // get parameters from request and initialize the variable
        try{
            Map<String, String> parameters = CustomUrlHelper.splitQuery(request);
            for (String key : parameters.keySet()) {
                if (key.equals("id")){
                    id = Integer.parseInt(parameters.get(key));
                }
            }

            ServiceFactory serviceFactory = ServiceFactory.getInstance();
            ShopService shopService = serviceFactory.getShopService();

            try {

                shopService.removeCategory(id);
                response = "Success";

            } catch (ServiceException e) {
                // write log
                response = "Error. Cannot delete category.";
            }

        }catch (UnsupportedEncodingException e){
            response = "Error. Wrong parameters were sent. Cannot delete category.";
        }

        return response;
    }
}
