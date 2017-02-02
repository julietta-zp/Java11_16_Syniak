package xyz.hedo.shop.controller.command.impl.order;

import xyz.hedo.shop.bean.Order;
import xyz.hedo.shop.controller.command.Command;
import xyz.hedo.shop.service.ShopService;
import xyz.hedo.shop.service.exception.ServiceException;
import xyz.hedo.shop.service.factory.ServiceFactory;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ViewExpiredOrders implements Command{

    private static final Logger logger = Logger.getLogger(String.valueOf(ViewExpiredOrders.class));

    @Override
    public String execute(String request) {
        String response = null;

        ServiceFactory serviceFactory = ServiceFactory.getInstance();
        ShopService shopService = serviceFactory.getShopService();

        try {

            List<Order> expiredOrders = shopService.getAllExpiredOrders();
            StringBuilder sb = new StringBuilder();
            for (Order order : expiredOrders) {
                sb.append("Order #");
                sb.append(order.getId());
                sb.append(System.lineSeparator());
            }
            response = sb.toString();

        } catch (ServiceException e) {
            // write log
            logger.log(Level.SEVERE, e.getMessage(), e);
            response = "Error during procedure.";
        }

        return response;
    }
}
