package xyz.hedo.shop.dao;

import xyz.hedo.shop.dao.exception.DaoException;
import xyz.hedo.shop.bean.User;

import java.util.List;

public interface UserDao {

    void signIn(String email, String password) throws DaoException;
    void signOut(String email) throws DaoException;

    List<User> getAllUsers() throws DaoException;
    User getUserById(int id) throws DaoException;
    void addUser(User user) throws DaoException;
    void removeUser(int userId) throws DaoException;
}
