package xyz.hedo.shop.service.impl;

import xyz.hedo.shop.dao.CategoryDao;
import xyz.hedo.shop.dao.EquipmentDao;
import xyz.hedo.shop.dao.OrderDao;
import xyz.hedo.shop.dao.OrderEquipmentsDao;
import xyz.hedo.shop.dao.exception.DaoException;
import xyz.hedo.shop.dao.factory.DaoFactory;
import xyz.hedo.shop.bean.Category;
import xyz.hedo.shop.bean.Equipment;
import xyz.hedo.shop.bean.Order;
import xyz.hedo.shop.service.ShopService;
import xyz.hedo.shop.service.exception.ServiceException;

import java.util.List;

public class ShopServiceImpl implements ShopService {

    private DaoFactory dao = DaoFactory.getInstance();
    private OrderDao orderDao = dao.getOrderDao();
    private EquipmentDao equipmentDao = dao.getEquipmentDao();
    private CategoryDao categoryDao = dao.getCategoryDao();
    private OrderEquipmentsDao orderEquipmentsDao = dao.getOrderEquipmentsDao();

    @Override
    public List<Order> getAllOrders() throws ServiceException {
        List<Order> orders;
        try{
            orders = orderDao.getAllOrders();
        }catch(DaoException e){
            throw new ServiceException(e);
        }
        return orders;
    }

    @Override
    public List<Order> getAllActiveOrders() throws ServiceException {
        List<Order> orders;
        try{
            orders = orderDao.getAllActiveOrders();
        }catch(DaoException e){
            throw new ServiceException(e);
        }
        return orders;
    }

    @Override
    public List<Order> getAllExpiredOrders() throws ServiceException{
        List<Order> orders;
        try{
            orders = orderDao.getAllExpiredOrders();
        }catch(DaoException e){
            throw new ServiceException(e);
        }
        return orders;
    }

    @Override
    public Order getOrderById(int orderId) throws ServiceException {
        if (orderId <= 0){
            throw new ServiceException("value of ID is negative or equals 0");
        }
        Order order;
        try {
            order = orderDao.getOrderById(orderId);
        }catch (DaoException e){
            throw new ServiceException(e);
        }
        return order;
    }

    @Override
    public List<Equipment> getOrderEquipments (int orderId) throws ServiceException {
        if (orderId <= 0){
            throw new ServiceException("value of ID is negative or equals 0");
        }
        List<Equipment> equipments;
        try{
            equipments = orderEquipmentsDao.getOrderEquipments(orderId);
        }catch(DaoException e){
            throw new ServiceException(e);
        }
        return equipments;
    }

    @Override
    public void addEquipment(Equipment item) throws ServiceException{
        if (item == null){
            throw new ServiceException("No item to add");
        }

        try {
            equipmentDao.addEquipment(item);
        }catch (DaoException e){
            throw new ServiceException(e);
        }
    }

    @Override
    public void removeEquipment(int itemId) throws ServiceException{
        if (itemId <= 0){
            throw new ServiceException("value of ID is negative or equals 0");
        }
        try {
            equipmentDao.removeEquipment(itemId);
        }catch (DaoException e){
            throw new ServiceException(e);
        }
    }

    @Override
    public List<Equipment> getAllEquipments() throws ServiceException {
        List<Equipment> equipments;
        try {
            equipments = equipmentDao.getAllEquipments();
        }catch (DaoException e){
            throw new ServiceException(e);
        }
        return equipments;
    }

    @Override
    public Equipment getEquipmentById(int itemId) throws ServiceException {
        if (itemId <= 0){
            throw new ServiceException("value of ID is negative or equals 0");
        }
        Equipment equipment;
        try {
            equipment = equipmentDao.getEquipmentById(itemId);
        }catch (DaoException e){
            throw new ServiceException(e);
        }
        return equipment;
    }

    @Override
    public List<Equipment> getEquipmentsByCategory(int categoryId) throws ServiceException {
        if (categoryId <= 0){
            throw new ServiceException("value of ID is negative or equals 0");
        }
        List<Equipment> equipments;
        try {
            equipments = equipmentDao.getEquipmentsByCategory(categoryId);
        }catch (DaoException e){
            throw new ServiceException(e);
        }
        return equipments;
    }

    @Override
    public void addCategory(Category category) throws ServiceException {
        try {
            categoryDao.addCategory(category);
        }catch (DaoException e){
            throw new ServiceException(e);
        }
    }

    @Override
    public List<Category> getAllCategories() throws ServiceException{
        List<Category> categories;
        try {
            categories = categoryDao.getAllCategories();
        }catch (DaoException e){
            throw new ServiceException(e);
        }
        return categories;
    }

    @Override
    public void removeCategory(int categoryId) throws ServiceException {
        if (categoryId <= 0){
            throw new ServiceException("value of ID is negative or equals 0");
        }
        try {
            categoryDao.removeCategory(categoryId);
        }catch (DaoException e){
            throw new ServiceException(e);
        }
    }

    @Override
    public Category getCategoryById(int categoryId) throws ServiceException {
        if (categoryId <= 0){
            throw new ServiceException("value of ID is negative or equals 0");
        }
        Category category;
        try {
            category = categoryDao.getCategoryById(categoryId);
        }catch (DaoException e){
            throw new ServiceException(e);
        }
        return category;
    }
}
