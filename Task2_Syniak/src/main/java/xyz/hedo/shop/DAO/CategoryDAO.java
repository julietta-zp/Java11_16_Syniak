package xyz.hedo.shop.DAO;

import xyz.hedo.shop.DAO.exception.DAOException;
import xyz.hedo.shop.bean.Category;

import java.util.List;

public interface CategoryDAO {

    List<Category> getAllCategories() throws DAOException;
    void addCategory(Category category) throws DAOException;
    void removeCategory(int categoryId) throws DAOException;
    Category getCategoryById(int categoryId) throws DAOException;
}
