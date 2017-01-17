package xyz.hedo.shop.DAO.factory;

import xyz.hedo.shop.DAO.*;
import xyz.hedo.shop.DAO.impl.*;

public class DAOFactory {
    private static final DAOFactory daoFactory = new DAOFactory();

    private DAOFactory() {
    }

    private UserDAO userDAO = new UserDAOImpl();
    private EquipmentDAO equipmentDAO = new EquipmentDAOImpl();
    private OrderDAO orderDAO = new OrderDAOImpl();
    private CategoryDAO categoryDAO = new CategoryDAOImpl();
    private OrderEquipmentsDAO orderEquipmentsDAO = new OrderEquipmentsDAOImpl();

    public OrderEquipmentsDAO getOrderEquipmentsDAO() {
        return orderEquipmentsDAO;
    }

    public UserDAO getUserDAO() {
        return userDAO;
    }

    public EquipmentDAO getEquipmentDAO() {
        return equipmentDAO;
    }

    public OrderDAO getOrderDAO() {
        return orderDAO;
    }

    public CategoryDAO getCategoryDAO() {
        return categoryDAO;
    }

    public static DAOFactory getInstance() {
        return daoFactory;
    }
}
