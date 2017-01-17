package xyz.hedo.shop.controller.command.impl.equipment;

import xyz.hedo.shop.bean.Category;
import xyz.hedo.shop.bean.Equipment;
import xyz.hedo.shop.controller.command.Command;
import xyz.hedo.shop.controller.command.CustomUrlHelper;
import xyz.hedo.shop.service.ShopService;
import xyz.hedo.shop.service.exception.ServiceException;
import xyz.hedo.shop.service.factory.ServiceFactory;

import java.io.UnsupportedEncodingException;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

public class CreateEquipment implements Command {

    private static final Logger logger = Logger.getLogger(String.valueOf(CreateEquipment.class));

    @Override
    public String execute(String request) {

        String response = null;
        String name = null;
        double price = 0.0;
        int quantity = 0;
        int categoryId = 0;

        // get parameters from request and initialize the variable
        try{
            Map<String, String> parameters = CustomUrlHelper.splitQuery(request);
            for (String key : parameters.keySet()) {
                switch (key){
                    case "name":
                        name = parameters.get(key);
                        break;
                    case "price":
                        price = Double.parseDouble(parameters.get(key));
                        break;
                    case "quantity":
                        quantity = Integer.parseInt(parameters.get(key));
                        break;
                    case "categoryId":
                        categoryId = Integer.parseInt(parameters.get(key));
                        break;

                }
            }

            ServiceFactory serviceFactory = ServiceFactory.getInstance();
            ShopService shopService = serviceFactory.getShopService();

            try {

                Category category = shopService.getCategoryById(categoryId);
                Equipment equipment = new Equipment();
                equipment.setName(name);
                equipment.setPrice(price);
                equipment.setQuantity(quantity);
                equipment.setCategory(category);
                shopService.addEquipment(equipment);
                response = "Success";

            } catch (ServiceException e) {
                // write log
                logger.log(Level.SEVERE, e.getMessage());
                response = "Error. Cannot create equipment.";
            }

        }catch (UnsupportedEncodingException e){
            response = "Error. Wrong parameters were sent. Cannot create equipment.";
        }

        return response;
    }
}
