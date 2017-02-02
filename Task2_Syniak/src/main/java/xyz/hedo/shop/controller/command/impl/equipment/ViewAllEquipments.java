package xyz.hedo.shop.controller.command.impl.equipment;

import xyz.hedo.shop.bean.Equipment;
import xyz.hedo.shop.controller.command.Command;
import xyz.hedo.shop.service.ShopService;
import xyz.hedo.shop.service.exception.ServiceException;
import xyz.hedo.shop.service.factory.ServiceFactory;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ViewAllEquipments implements Command {

    private static final Logger logger = Logger.getLogger(String.valueOf(ViewAllEquipments.class));

    @Override
    public String execute(String request) {
        String response = null;

        // get parameters from request and initialize the variable

        ServiceFactory serviceFactory = ServiceFactory.getInstance();
        ShopService shopService = serviceFactory.getShopService();

        try {

            List<Equipment> equipments = shopService.getAllEquipments();
            StringBuilder sb = new StringBuilder();
            for (Equipment equipment : equipments) {
                sb.append(equipment.getName()).append(" - ").append("$").append(equipment.getPrice()).append(System.lineSeparator());
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
