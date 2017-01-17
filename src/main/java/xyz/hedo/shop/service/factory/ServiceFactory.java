package xyz.hedo.shop.service.factory;

import xyz.hedo.shop.service.ShopService;
import xyz.hedo.shop.service.UserService;
import xyz.hedo.shop.service.impl.ShopServiceImpl;
import xyz.hedo.shop.service.impl.UserServiceImpl;

public class ServiceFactory {

    private static final ServiceFactory instance = new ServiceFactory();

    private UserService userService = new UserServiceImpl();
    private ShopService shopService = new ShopServiceImpl();

    private ServiceFactory() {
    }

    public UserService getUserService() {
        return userService;
    }

    public ShopService getShopService() {
        return shopService;
    }

    public static ServiceFactory getInstance() {
        return instance;
    }

}
