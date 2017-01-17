package xyz.hedo.shop.service;

import xyz.hedo.shop.bean.Category;
import xyz.hedo.shop.bean.Equipment;
import xyz.hedo.shop.bean.Order;
import xyz.hedo.shop.service.exception.ServiceException;

import java.util.List;

public interface ShopService {

    List<Order> getAllOrders() throws ServiceException;
    List<Order> getAllActiveOrders() throws ServiceException;
    List<Order> getAllExpiredOrders() throws ServiceException;
    Order getOrderById(int orderId) throws ServiceException;
    List<Equipment> getOrderEquipments(int orderId) throws ServiceException;

    void addEquipment(Equipment item) throws ServiceException;
    Equipment getEquipmentById(int itemId) throws ServiceException;
    List<Equipment> getEquipmentsByCategory(int categoryId) throws ServiceException;
    void removeEquipment(int itemId) throws ServiceException;
    List<Equipment> getAllEquipments() throws ServiceException;

    void addCategory(Category category) throws ServiceException;
    void removeCategory(int categoryId) throws ServiceException;
    Category getCategoryById(int categoryId) throws ServiceException;
    List<Category> getAllCategories() throws ServiceException;

}
