package xyz.hedo.shop.service.impl;

import xyz.hedo.shop.DAO.EquipmentDAO;
import xyz.hedo.shop.DAO.OrderDAO;
import xyz.hedo.shop.DAO.OrderEquipmentsDAO;
import xyz.hedo.shop.DAO.UserDAO;
import xyz.hedo.shop.DAO.exception.DAOException;
import xyz.hedo.shop.DAO.factory.DAOFactory;
import xyz.hedo.shop.bean.Equipment;
import xyz.hedo.shop.bean.Order;
import xyz.hedo.shop.bean.User;
import xyz.hedo.shop.service.UserService;
import xyz.hedo.shop.service.exception.ServiceException;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

public class UserServiceImpl implements UserService {

    private DAOFactory dao = DAOFactory.getInstance();
    private UserDAO userDAO = dao.getUserDAO();
    private OrderDAO orderDAO = dao.getOrderDAO();
    private EquipmentDAO equipmentDAO = dao.getEquipmentDAO();
    private OrderEquipmentsDAO orderEquipmentsDAO = dao.getOrderEquipmentsDAO();

    @Override
    public void signUp(User user) throws ServiceException {
        if (user == null){
            throw new ServiceException("No data was found");
        }
        try{
            if (user.getPassword() == null || user.getPassword().isEmpty()){
                throw new ServiceException("Incorrect password");
            }
            String purePassword = user.getPassword();
            String hashedPassword = hashPassword(purePassword);
            user.setPassword(hashedPassword);
            userDAO.addUser(user);
            // auto signing in after registration
            signIn(user.getEmail(), purePassword);
        }catch(DAOException e){
            throw new ServiceException(e);
        }
    }

    @Override
    public void signIn(String email, String password) throws ServiceException {
        if(email == null || email.isEmpty()){
            throw new ServiceException("Incorrect email");
        }
        try{
            if (password == null || password.isEmpty()){
                throw new ServiceException("Incorrect password");
            }
            String hashedPassword = hashPassword(password);
            userDAO.signIn(email, hashedPassword);
        }catch(DAOException e){
            throw new ServiceException(e);
        }
    }

    @Override
    public void signOut(String email) throws ServiceException {
        if(email == null || email.isEmpty()){
            throw new ServiceException("Enter your email.");
        }
        try{
            userDAO.signOut(email);
        }catch(DAOException e){
            throw new ServiceException(e);
        }
    }


    // for admin usage
    @Override
    public List<User> getAllUsers() throws ServiceException {
        List<User> users;
        try{
            users = userDAO.getAllUsers();
        }catch(DAOException e){
            throw new ServiceException(e);
        }
        return users;
    }

    // for admin usage
    @Override
    public User getUserById(int id) throws ServiceException{
        if (id <= 0){
            throw new ServiceException("value of ID is negative or equals 0");
        }
        User user;
        try{
            user = userDAO.getUserById(id);
        }catch(DAOException e){
            throw new ServiceException(e);
        }
        return user;
    }

    // for admin usage
//    @Override
//    public void addNewUser(User user) throws ServiceException{
//        if (user == null){
//            throw new ServiceException();
//        }
//        try{
//            userDAO.addUser(user);
//        }catch(DAOException e){
//            throw new ServiceException(e);
//        }
//    }

    // for admin usage
    @Override
    public void removeUser(int id) throws ServiceException{
        if (id <= 0){
            throw new ServiceException("value of ID is negative or equals 0");
        }
        try{
            userDAO.removeUser(id);
        }catch(DAOException e){
            throw new ServiceException(e);
        }
    }

    @Override
    public void rentEquipment(int userId, List<Integer> equipmentIds,
                              Date datetimeFrom, Date datetimeTo) throws ServiceException {
        if (userId <= 0){
            throw new ServiceException("value of ID is negative or equals 0");
        }

        if (equipmentIds.size() > 3){
            throw new ServiceException("You can rent not more than 3 items of equipment");
        }

        if (datetimeFrom.after(datetimeTo)){
            throw new ServiceException("Wrong dates were set");
        }

        try{
            List<Equipment> allEquipments = equipmentDAO.getAllEquipments();
            // list of wanted equipments
            List<Equipment> neededEquipments = new ArrayList<>();
            for (Equipment item : allEquipments) {
                for (Integer id : equipmentIds){
                    if (item.getId() == id){
                        neededEquipments.add(item);
                    }
                }
            }

            // if some equipments are not present in the shop
            if (neededEquipments.size() < equipmentIds.size()){
                StringBuilder sb = new StringBuilder();
                sb.append("Not all equipment is present. You can only rent: ");
                for (Equipment item : neededEquipments){
                    sb.append(item.getName()).append(System.lineSeparator());
                }
                throw new ServiceException(sb.toString());
            }

            Map<Integer, Integer> borrowedEquipments = orderEquipmentsDAO.countBorrowedEquipments();
            List<Equipment> availableEquipments = new ArrayList<>();
            // check if wanted equipments are in stock
            if (borrowedEquipments.size() > 0){
                for (Equipment item : neededEquipments){
                    if (borrowedEquipments.containsKey(item.getId())){
                        if (borrowedEquipments.get(item.getId()) < item.getQuantity()){
                            availableEquipments.add(item);
                        } else {
                            System.out.println(item.getName() + " - is not available, sorry.");
                        }
                    } else {
                        if (item.getQuantity() > 0){
                            availableEquipments.add(item);
                        } else {
                            System.out.println(item.getName() + " - is not available, sorry.");
                        }
                    }
                }
            } else {
                for (Equipment item : neededEquipments){
                    if (item.getQuantity() > 0){
                        availableEquipments.add(item);
                    } else {
                        System.out.println(item.getName() + " - is not available, sorry.");
                    }
                }
            }

            if (availableEquipments.size() <= 0){
                throw new ServiceException("Sorry. No equipment available!");
            }

            double totalPrice = 0.0d;

            // count order total price
            for (Equipment item : availableEquipments){
                totalPrice += item.getPrice();
            }
            if (totalPrice != 0){
                int orderId = orderDAO.createOrder(userId, datetimeFrom, datetimeTo, totalPrice);
                orderEquipmentsDAO.addEquipmentsToOrder(orderId, availableEquipments);
            }

        }catch(DAOException e){
            throw new ServiceException(e);
        }
    }

    @Override
    public void returnEquipment(int orderId, List<Integer> equipmentIds) throws ServiceException{
        if (orderId <= 0){
            throw new ServiceException("value of ID is negative or equals 0");
        }

        if (equipmentIds.size() <= 0){
            throw new ServiceException("Nothing to return");
        }

        try{
            Order order = orderDAO.getOrderById(orderId);

            if (!order.isActive()){
                throw new ServiceException("The order was already closed");
            }

            List<Integer> orderEquipmentIds = orderEquipmentsDAO.getOrderEquipmentIds(orderId);
            if (orderEquipmentIds.containsAll(equipmentIds) && equipmentIds.containsAll(orderEquipmentIds)){
                orderDAO.closeOrder(orderId);
            } else {
                throw new ServiceException("It's not the same equipment that was borrowed");
            }
        }catch(DAOException e){
            throw new ServiceException(e);
        }
    }


    private String hashPassword(String password) throws ServiceException{
        String hashedPassword = null;
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            md.update(password.getBytes("UTF-8"));
            byte[] digest = md.digest();
            hashedPassword = new String(digest);
        }catch (NoSuchAlgorithmException | UnsupportedEncodingException e){
            throw new ServiceException(e);
        }
        return hashedPassword;
    }
}
