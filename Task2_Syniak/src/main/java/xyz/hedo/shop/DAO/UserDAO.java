package xyz.hedo.shop.DAO;

import xyz.hedo.shop.DAO.exception.DAOException;
import xyz.hedo.shop.bean.User;

import java.util.List;

public interface UserDAO {

    void signIn(String email, String password) throws DAOException;
    void signOut(String email) throws DAOException;

    List<User> getAllUsers() throws DAOException;
    User getUserById(int id) throws DAOException;
    void addUser(User user) throws DAOException;
    void removeUser(int userId) throws DAOException;
}
