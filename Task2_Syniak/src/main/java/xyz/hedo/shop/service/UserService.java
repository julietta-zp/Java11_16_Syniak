package xyz.hedo.shop.service;

import xyz.hedo.shop.bean.User;
import xyz.hedo.shop.service.exception.ServiceException;

import java.util.Date;
import java.util.List;

public interface UserService {

    void signUp(User user) throws ServiceException;
    void signIn(String email, String password) throws ServiceException;
    void signOut(String email) throws ServiceException;

    List<User> getAllUsers() throws ServiceException;
    User getUserById(int id) throws ServiceException;
//    void addNewUser(User user) throws ServiceException;
    void removeUser(int id) throws ServiceException;

    void rentEquipment(int userId, List<Integer> equipmentIds, Date datetimeFrom, Date datetimeTo) throws ServiceException;
    void returnEquipment(int orderId, List<Integer> equipmentIds) throws ServiceException;

}
