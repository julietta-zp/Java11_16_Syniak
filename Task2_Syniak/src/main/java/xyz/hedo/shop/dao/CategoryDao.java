package xyz.hedo.shop.dao;

import xyz.hedo.shop.dao.exception.DaoException;
import xyz.hedo.shop.bean.Category;

import java.util.List;

public interface CategoryDao {

    List<Category> getAllCategories() throws DaoException;
    void addCategory(Category category) throws DaoException;
    void removeCategory(int categoryId) throws DaoException;
    Category getCategoryById(int categoryId) throws DaoException;
}
