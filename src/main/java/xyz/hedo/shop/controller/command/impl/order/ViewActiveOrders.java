package xyz.hedo.shop.controller.command.impl.order;

import xyz.hedo.shop.bean.Order;
import xyz.hedo.shop.controller.command.Command;
import xyz.hedo.shop.service.ShopService;
import xyz.hedo.shop.service.exception.ServiceException;
import xyz.hedo.shop.service.factory.ServiceFactory;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ViewActiveOrders implements Command{

    private static final Logger logger = Logger.getLogger(String.valueOf(ViewActiveOrders.class));

    @Override
    public String execute(String request) {
        String response = null;

        ServiceFactory serviceFactory = ServiceFactory.getInstance();
        ShopService shopService = serviceFactory.getShopService();

        try {

            List<Order> activeOrders = shopService.getAllActiveOrders();
            StringBuilder sb = new StringBuilder();
            for (Order order : activeOrders) {
                sb.append("Order #").append(order.getId());
                sb.append(System.lineSeparator());
            }
            response = sb.toString();

        } catch (ServiceException e) {
            // write log
            logger.log(Level.SEVERE, e.getMessage());
            response = "Error during procedure.";
        }

        return response;
    }
}
