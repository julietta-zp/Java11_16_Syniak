package xyz.hedo.shop.controller.command.impl.order;

import xyz.hedo.shop.bean.Equipment;
import xyz.hedo.shop.bean.Order;
import xyz.hedo.shop.controller.command.Command;
import xyz.hedo.shop.controller.command.CustomUrlHelper;
import xyz.hedo.shop.service.ShopService;
import xyz.hedo.shop.service.exception.ServiceException;
import xyz.hedo.shop.service.factory.ServiceFactory;

import java.io.UnsupportedEncodingException;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ViewOrderDetails implements Command {

    private static final Logger logger = Logger.getLogger(String.valueOf(ViewOrderDetails.class));

    @Override
    public String execute(String request) {
        String response = null;
        int id = 0;

        // get parameters from request and initialize the variable
        try{
            Map<String, String> parameters = CustomUrlHelper.splitQuery(request);
            for (String key : parameters.keySet()) {
                if (key.equals("id")){// не забывай именовать константные строки
                    id = Integer.parseInt(parameters.get(key));
                }
            }

            ServiceFactory serviceFactory = ServiceFactory.getInstance();
            ShopService shopService = serviceFactory.getShopService();

            try {

                Order order = shopService.getOrderById(id);
                List<Equipment> equipments = shopService.getOrderEquipments(id);
                StringBuilder sb = new StringBuilder();
                sb.append("Order #").append(order.getId());
                sb.append(System.lineSeparator());
                sb.append("Date from: ").append(order.getDateTimeFrom());
                sb.append(System.lineSeparator());
                sb.append("Date to: ").append(order.getDateTimeTo());
                sb.append(System.lineSeparator());
                sb.append("Equipments: ");
                sb.append(System.lineSeparator());
                for (Equipment item : equipments){
                    sb.append(item.getName());
                    sb.append(System.lineSeparator());
                }

                response = sb.toString();

            } catch (ServiceException e) {
                // write log
                logger.log(Level.SEVERE, e.getMessage());
                response = "Error. Cannot get equipment.";
            }

        }catch (UnsupportedEncodingException e){
            response = "Error. Wrong parameters were sent.";
        }

        return response;
    }
}
