package xyz.hedo.shop.dao;

import xyz.hedo.shop.dao.exception.DaoException;
import xyz.hedo.shop.bean.Order;

import java.util.Date;
import java.util.List;

public interface OrderDao {

    List<Order> getAllOrders() throws DaoException;
    Order getOrderById(int id) throws DaoException;
    Integer createOrder(int userId, Date datetimeFrom, Date datetimeTo, double totalPrice) throws DaoException;
    void closeOrder(int id) throws DaoException;

    List<Order> getAllActiveOrders() throws DaoException;
    List<Order> getAllExpiredOrders() throws DaoException;
}
