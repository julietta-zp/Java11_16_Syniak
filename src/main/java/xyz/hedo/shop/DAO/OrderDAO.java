package xyz.hedo.shop.DAO;

import xyz.hedo.shop.DAO.exception.DAOException;
import xyz.hedo.shop.bean.Equipment;
import xyz.hedo.shop.bean.Order;

import java.util.Date;
import java.util.List;
import java.util.Map;

public interface OrderDAO {

    List<Order> getAllOrders() throws DAOException;
    Order getOrderById(int id) throws DAOException;
    Integer createOrder(int userId, Date datetimeFrom, Date datetimeTo, double totalPrice) throws DAOException;
    void closeOrder(int id) throws DAOException;

    List<Order> getAllActiveOrders() throws DAOException;
    List<Order> getAllExpiredOrders() throws DAOException;
}
