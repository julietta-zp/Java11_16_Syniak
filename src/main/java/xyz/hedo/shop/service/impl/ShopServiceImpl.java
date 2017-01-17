package xyz.hedo.shop.service.impl;

import xyz.hedo.shop.DAO.CategoryDAO;
import xyz.hedo.shop.DAO.EquipmentDAO;
import xyz.hedo.shop.DAO.OrderDAO;
import xyz.hedo.shop.DAO.OrderEquipmentsDAO;
import xyz.hedo.shop.DAO.exception.DAOException;
import xyz.hedo.shop.DAO.factory.DAOFactory;
import xyz.hedo.shop.bean.Category;
import xyz.hedo.shop.bean.Equipment;
import xyz.hedo.shop.bean.Order;
import xyz.hedo.shop.service.ShopService;
import xyz.hedo.shop.service.exception.ServiceException;

import java.util.List;

public class ShopServiceImpl implements ShopService {

    private DAOFactory dao = DAOFactory.getInstance();
    private OrderDAO orderDAO = dao.getOrderDAO();
    private EquipmentDAO equipmentDAO = dao.getEquipmentDAO();
    private CategoryDAO categoryDAO = dao.getCategoryDAO();
    private OrderEquipmentsDAO orderEquipmentsDAO = dao.getOrderEquipmentsDAO();

    @Override
    public List<Order> getAllOrders() throws ServiceException {
        List<Order> orders;
        try{
            orders = orderDAO.getAllOrders();
        }catch(DAOException e){
            throw new ServiceException(e);
        }
        return orders;
    }

    @Override
    public List<Order> getAllActiveOrders() throws ServiceException {
        List<Order> orders;
        try{
            orders = orderDAO.getAllActiveOrders();
        }catch(DAOException e){
            throw new ServiceException(e);
        }
        return orders;
    }

    @Override
    public List<Order> getAllExpiredOrders() throws ServiceException{
        List<Order> orders;
        try{
            orders = orderDAO.getAllExpiredOrders();
        }catch(DAOException e){
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
            order = orderDAO.getOrderById(orderId);
        }catch (DAOException e){
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
            equipments = orderEquipmentsDAO.getOrderEquipments(orderId);
        }catch(DAOException e){
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
            equipmentDAO.addEquipment(item);
        }catch (DAOException e){
            throw new ServiceException(e);
        }
    }

    @Override
    public void removeEquipment(int itemId) throws ServiceException{
        if (itemId <= 0){
            throw new ServiceException("value of ID is negative or equals 0");
        }
        try {
            equipmentDAO.removeEquipment(itemId);
        }catch (DAOException e){
            throw new ServiceException(e);
        }
    }

    @Override
    public List<Equipment> getAllEquipments() throws ServiceException {
        List<Equipment> equipments;
        try {
            equipments = equipmentDAO.getAllEquipments();
        }catch (DAOException e){
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
            equipment = equipmentDAO.getEquipmentById(itemId);
        }catch (DAOException e){
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
            equipments = equipmentDAO.getEquipmentsByCategory(categoryId);
        }catch (DAOException e){
            throw new ServiceException(e);
        }
        return equipments;
    }

    @Override
    public void addCategory(Category category) throws ServiceException {
        try {
            categoryDAO.addCategory(category);
        }catch (DAOException e){
            throw new ServiceException(e);
        }
    }

    @Override
    public List<Category> getAllCategories() throws ServiceException{
        List<Category> categories;
        try {
            categories = categoryDAO.getAllCategories();
        }catch (DAOException e){
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
            categoryDAO.removeCategory(categoryId);
        }catch (DAOException e){
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
            category = categoryDAO.getCategoryById(categoryId);
        }catch (DAOException e){
            throw new ServiceException(e);
        }
        return category;
    }
}
