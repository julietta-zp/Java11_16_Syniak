package xyz.hedo.shop.DAO.impl;

import xyz.hedo.shop.DAO.EquipmentDAO;
import xyz.hedo.shop.DAO.exception.DAOException;
import xyz.hedo.shop.bean.Category;
import xyz.hedo.shop.bean.Equipment;

import java.sql.*;
import java.util.*;

public class EquipmentDAOImpl implements EquipmentDAO {

    private final String jdbcDriver = "org.gjt.mm.mysql.Driver";
    private final String jdbcUrl = "jdbc:mysql://127.0.0.1/rent_shop";
    private final String jdbcLogin = "root";
    private final String jdbcPassword = "root";

    @Override
    public List<Equipment> getAllEquipments() throws DAOException{
        Connection con = null;
        Statement st = null;
        ResultSet res = null;
        List<Equipment> items = new ArrayList<Equipment>();
        try {
            Class.forName(jdbcDriver);
            con = DriverManager.getConnection(jdbcUrl, jdbcLogin, jdbcPassword);
            String sql = "SELECT * FROM equipments AS eq LEFT JOIN categories AS cat ON cat.id = eq.category_id";
            st = con.createStatement();
            res = st.executeQuery(sql);
            while (res.next()) {
                Category category = new Category();
                category.setId(res.getInt("cat.id"));
                category.setName(res.getString("cat.name"));
                Equipment item = new Equipment();
                item.setId(res.getInt("id"));
                item.setName(res.getString("name"));
                item.setPrice(res.getDouble("price"));
                item.setQuantity(res.getInt("quantity"));
                item.setCategory(category);
                items.add(item);
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
        return items;
    }

    @Override
    public List<Equipment> getEquipmentsByCategory(int categoryId) throws DAOException{
        Connection con = null;
        PreparedStatement ps = null;
        ResultSet res = null;
        List<Equipment> items = new ArrayList<Equipment>();
        try {
            Class.forName(jdbcDriver);
            con = DriverManager.getConnection(jdbcUrl, jdbcLogin, jdbcPassword);
            String sql = "SELECT eq.id, eq.name, eq.price, eq.quantity, cat.id, cat.name FROM equipments AS eq LEFT JOIN categories AS cat ON cat.id = eq.category_id WHERE eq.category_id = ?";
            ps = con.prepareStatement(sql);
            ps.setInt(1, categoryId);
            res = ps.executeQuery();
            while (res.next()) {
                Category cat = new Category();
                cat.setId(res.getInt("cat.id"));
                cat.setName(res.getString("cat.name"));
                Equipment item = new Equipment();
                item.setId(res.getInt("eq.id"));
                item.setName(res.getString("eq.name"));
                item.setPrice(res.getDouble("eq.price"));
                item.setQuantity(res.getInt("eq.quantity"));
                item.setCategory(cat);
                items.add(item);
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
        return items;
    }

    @Override
    public Equipment getEquipmentById(int id) throws DAOException{
        Connection con = null;
        PreparedStatement ps = null;
        ResultSet res = null;
        Equipment item = null;
        try {
            Class.forName(jdbcDriver);
            con = DriverManager.getConnection(jdbcUrl, jdbcLogin, jdbcPassword);
            String sql = "SELECT eq.id, eq.name, eq.price, eq.quantity, cat.id, cat.name FROM equipments AS eq LEFT JOIN categories AS cat ON cat.id = eq.category_id WHERE eq.id = ?";
            ps = con.prepareStatement(sql);
            ps.setInt(1, id);
            res = ps.executeQuery();
            if (!res.isBeforeFirst() ) {
                System.out.println("No such equipment was found");
            }
            while (res.next()) {
                Category category = new Category();
                category.setId(res.getInt("cat.id"));
                category.setName(res.getString("cat.name"));
                item = new Equipment();
                item.setId(res.getInt("eq.id"));
                item.setName(res.getString("eq.name"));
                item.setPrice(res.getDouble("eq.price"));
                item.setQuantity(res.getInt("eq.quantity"));
                item.setCategory(category);
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
        return item;
    }

    @Override
    public void addEquipment(Equipment equipment) throws DAOException {
        Connection con = null;
        PreparedStatement ps = null;
        try {
            Class.forName(jdbcDriver);
            con = DriverManager.getConnection(jdbcUrl, jdbcLogin, jdbcPassword);
            String sql = "INSERT INTO equipments(name, price, quantity, category_id) VALUES(?,?,?,?)";
            ps = con.prepareStatement(sql);
            ps.setString(1, equipment.getName());
            ps.setDouble(2, equipment.getPrice());
            ps.setInt(3, equipment.getQuantity());
            ps.setInt(4, equipment.getCategory().getId());
            int rows = ps.executeUpdate();
            if (rows == 0) {
                System.out.println("Something went wrong.");
            } else if (rows > 0) {
                System.out.println("Equipment was successfully added");
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
    public void removeEquipment(int itemId) throws DAOException{
        Connection con = null;
        PreparedStatement ps = null;
        try {
            Class.forName(jdbcDriver);
            con = DriverManager.getConnection(jdbcUrl, jdbcLogin, jdbcPassword);
            String sql = "DELETE FROM equipments WHERE id = ?";
            ps = con.prepareStatement(sql);
            ps.setInt(1, itemId);
            int rows = ps.executeUpdate();
            if (rows == 0) {
                System.out.println("Nothing to delete. No such category");
            } else if (rows > 0) {
                System.out.println("Equipment was successfully deleted");
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
