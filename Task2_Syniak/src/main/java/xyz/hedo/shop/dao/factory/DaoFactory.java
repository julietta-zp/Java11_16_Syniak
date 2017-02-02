package xyz.hedo.shop.dao.factory;

import xyz.hedo.shop.dao.*;
import xyz.hedo.shop.dao.impl.*;

public class DaoFactory {
    private static final DaoFactory DAO_FACTORY = new DaoFactory();

    private DaoFactory() {
    }

    private UserDao userDao = new UserDaoImpl();
    private EquipmentDao equipmentDao = new EquipmentDaoImpl();
    private OrderDao orderDao = new OrderDaoImpl();
    private CategoryDao categoryDao = new CategoryDaoImpl();
    private OrderEquipmentsDao orderEquipmentsDao = new OrderEquipmentsDaoImpl();

    public OrderEquipmentsDao getOrderEquipmentsDao() {
        return orderEquipmentsDao;
    }

    public UserDao getUserDao() {
        return userDao;
    }

    public EquipmentDao getEquipmentDao() {
        return equipmentDao;
    }

    public OrderDao getOrderDao() {
        return orderDao;
    }

    public CategoryDao getCategoryDao() {
        return categoryDao;
    }

    public static DaoFactory getInstance() {
        return DAO_FACTORY;
    }
}
