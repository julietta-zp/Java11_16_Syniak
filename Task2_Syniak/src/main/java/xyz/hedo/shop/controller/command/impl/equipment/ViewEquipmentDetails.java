package xyz.hedo.shop.controller.command.impl.equipment;

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

public class ViewEquipmentDetails implements Command {

    private static final Logger logger = Logger.getLogger(String.valueOf(ViewEquipmentDetails.class));

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
            ShopService shopService = serviceFactory.getShopService();

            try {

                Equipment equipment = shopService.getEquipmentById(id);
                StringBuilder sb = new StringBuilder();
                sb.append(equipment.getName());
                sb.append(": $");
                sb.append(equipment.getPrice());
                sb.append(", ");
                sb.append(equipment.getQuantity());
                sb.append("pcs.");
                response = sb.toString();

            } catch (ServiceException e) {
                // write log
                logger.log(Level.SEVERE, e.getMessage(), e);
                response = "Error. Cannot get equipment.";
            }

        }catch (UnsupportedEncodingException e){
            response = "Error. Wrong parameters were sent.";
        }

        return response;
    }
}
