package xyz.hedo.shop.controller.command.impl;

import xyz.hedo.shop.controller.command.Command;
import xyz.hedo.shop.controller.command.CustomUrlHelper;
import xyz.hedo.shop.service.UserService;
import xyz.hedo.shop.service.exception.ServiceException;
import xyz.hedo.shop.service.factory.ServiceFactory;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ReturnEquipment implements Command {

    private static final Logger logger = Logger.getLogger(String.valueOf(ReturnEquipment.class));

    @Override
    public String execute(String request) {
        String response = null;
        int orderId = 0;
        List<Integer> equipmentIds = new ArrayList<>();

        // get parameters from request and initialize the variable
        try{
            Map<String, String> parameters = CustomUrlHelper.splitQuery(request);
            for (String key : parameters.keySet()) {
                switch (key){
                    case "orderId":
                        orderId = Integer.parseInt(parameters.get(key));
                        break;
                    case "itemId1":
                        equipmentIds.add(Integer.parseInt(parameters.get(key)));
                        break;
                    case "itemId2":
                        equipmentIds.add(Integer.parseInt(parameters.get(key)));
                        break;
                    case "itemId3":
                        equipmentIds.add(Integer.parseInt(parameters.get(key)));
                        break;
                }
            }

            ServiceFactory serviceFactory = ServiceFactory.getInstance();
            UserService userService = serviceFactory.getUserService();

            try {

                userService.returnEquipment(orderId, equipmentIds);
                response = "Success";

            } catch (ServiceException e) {
                // write log
                logger.log(Level.SEVERE, e.getMessage());
                response = "Error.";
            }

        }catch (UnsupportedEncodingException e){
            response = "Error. Wrong parameters were sent.";
        }

        return response;
    }
}
