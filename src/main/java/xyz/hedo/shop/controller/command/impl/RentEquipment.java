package xyz.hedo.shop.controller.command.impl;

import xyz.hedo.shop.controller.command.Command;
import xyz.hedo.shop.controller.command.CustomUrlHelper;
import xyz.hedo.shop.service.UserService;
import xyz.hedo.shop.service.exception.ServiceException;
import xyz.hedo.shop.service.factory.ServiceFactory;

import java.io.UnsupportedEncodingException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;

public class RentEquipment implements Command {

    private static final Logger logger = Logger.getLogger(String.valueOf(RentEquipment.class));

    @Override
    public String execute(String request) {

        String response = null;
        int userId = 0;
        Date dateTimeFrom = new Date();
        Date dateTimeTo = new Date();
        List<Integer> equipmentIds = new ArrayList<>();

        // get parameters from request and initialize the variable
        try{
            Map<String, String> parameters = CustomUrlHelper.splitQuery(request);
            for (String key : parameters.keySet()) {
                switch (key){
                    case "userId":
                        userId = Integer.parseInt(parameters.get(key));
                        break;
                    case "from":
                        dateTimeFrom = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(parameters.get(key));
                        break;
                    case "to":
                        dateTimeTo = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(parameters.get(key));
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

                userService.rentEquipment(userId, equipmentIds, dateTimeFrom, dateTimeTo);
                response = "Success";

            } catch (ServiceException e) {
                // write log
                logger.log(Level.SEVERE, e.getMessage());
                response = "Error. Cannot rent equipment.";
            }

        }catch (UnsupportedEncodingException e){
            response = "Error. Wrong parameters were sent. Cannot rent equipment.";
        }catch (ParseException e){
            response = "Error occured.";}

        return response;
    }
}
