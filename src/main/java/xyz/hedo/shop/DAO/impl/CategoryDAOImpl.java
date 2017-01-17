package xyz.hedo.shop.DAO.impl;

import xyz.hedo.shop.DAO.CategoryDAO;
import xyz.hedo.shop.DAO.exception.DAOException;
import xyz.hedo.shop.bean.Category;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CategoryDAOImpl implements CategoryDAO {

    private final String jdbcDriver = "org.gjt.mm.mysql.Driver";
    private final String jdbcUrl = "jdbc:mysql://127.0.0.1/rent_shop";
    private final String jdbcLogin = "root";
    private final String jdbcPassword = "root";

    @Override
    public List<Category> getAllCategories() throws DAOException {
        Connection con = null;
        Statement st = null;
        ResultSet res = null;
        List<Category> categories = new ArrayList<Category>();
        try {
            Class.forName(jdbcDriver);
            con = DriverManager.getConnection(jdbcUrl, jdbcLogin, jdbcPassword);
            String sql = "SELECT id, name FROM categories";
            st = con.createStatement();
            res = st.executeQuery(sql);
            while (res.next()) {
                int id = res.getInt("id");
                String name = res.getString("name");
                Category category = new Category();
                category.setId(id);
                category.setName(name);
                categories.add(category);
            }
        }catch (ClassNotFoundException e){
            throw new DAOException(e);
        }catch (SQLException e){
            throw new DAOException(e);
        }finally {
            try {
                if (res != null) res.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }

            try {
                if (st != null) st.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }

            try {
                if (con != null) con.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }

        }
        return categories;
    }

    @Override
    public Category getCategoryById(int categoryId) throws DAOException {
        Connection con = null;
        PreparedStatement ps = null;
        ResultSet res = null;
        Category category = null;
        try {
            Class.forName(jdbcDriver);
            con = DriverManager.getConnection(jdbcUrl, jdbcLogin, jdbcPassword);
            String sql = "SELECT name FROM categories WHERE id = ?";
            ps = con.prepareStatement(sql);
            ps.setInt(1, categoryId);
            res = ps.executeQuery();
            if (!res.isBeforeFirst() ) {
                System.out.println("No such category was found");
            }
            while (res.next()) {
                category = new Category();
                category.setId(categoryId);
                category.setName(res.getString("name"));
            }
        }catch (ClassNotFoundException e){
            throw new DAOException(e);
        }catch (SQLException e){
            throw new DAOException(e);
        }finally {
            try {
                if (res != null) res.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }

            try {
                if (ps != null) ps.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }

            try {
                if (con != null) con.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }

        }
        return category;
    }

    @Override
    public void addCategory(Category category) throws DAOException {
        Connection con = null;
        PreparedStatement ps = null;
        try {
            Class.forName(jdbcDriver);
            con = DriverManager.getConnection(jdbcUrl, jdbcLogin, jdbcPassword);
            String sql = "INSERT INTO categories(name) VALUES(?)";
            ps = con.prepareStatement(sql);
            ps.setString(1, category.getName());
            int rows = ps.executeUpdate();
            if (rows == 0) {
                System.out.println("Something went wrong.");
            } else if (rows > 0) {
                System.out.println("Category was successfully added");
            }
        }catch (ClassNotFoundException e){
            throw new DAOException(e);
        }catch (SQLException e){
            throw new DAOException(e);
        }finally {
            try {
                if (ps != null) ps.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }

            try {
                if (con != null) con.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }

        }
    }

    @Override
    public void removeCategory(int categoryId) throws DAOException{
        Connection con = null;
        PreparedStatement ps = null;
        try {
            Class.forName(jdbcDriver);
            con = DriverManager.getConnection(jdbcUrl, jdbcLogin, jdbcPassword);
            String sql = "DELETE FROM categories WHERE id = ?";
            ps = con.prepareStatement(sql);
            ps.setInt(1, categoryId);
            int rows = ps.executeUpdate();
            if (rows == 0) {
                System.out.println("Nothing to delete. No such category");
            } else if (rows > 0) {
                System.out.println("Category was successfully deleted");
            }
        }catch (ClassNotFoundException e){
            throw new DAOException(e);
        }catch (SQLException e){
            throw new DAOException(e);
        }finally {
            try {
                if (ps != null) ps.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }

            try {
                if (con != null) con.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }

        }
    }


}
