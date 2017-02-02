package xyz.hedo.shop.dao.impl;

import xyz.hedo.shop.dao.EquipmentDao;
import xyz.hedo.shop.dao.exception.DaoException;
import xyz.hedo.shop.bean.Category;
import xyz.hedo.shop.bean.Equipment;

import java.sql.*;
import java.util.*;

public class EquipmentDaoImpl implements EquipmentDao {

    private final String jdbcDriver = "org.gjt.mm.mysql.Driver";
    private final String jdbcUrl = "jdbc:mysql://127.0.0.1/rent_shop";
    private final String jdbcLogin = "root";
    private final String jdbcPassword = "root";

    {
        try{
            Class.forName(jdbcDriver);
        }catch (ClassNotFoundException e){
            e.printStackTrace();
        }
    }

    @Override
    public List<Equipment> getAllEquipments() throws DaoException {
        Connection con = null;
        Statement st = null;
        ResultSet res = null;
        List<Equipment> items = new ArrayList<Equipment>();
        try {
            con = DriverManager.getConnection(jdbcUrl, jdbcLogin, jdbcPassword);
            String sql = "SELECT eq.id, eq.name, eq.price, eq.quantity, cat.id, cat.name FROM equipments AS eq LEFT JOIN categories AS cat ON cat.id = eq.category_id";
            st = con.createStatement();
            res = st.executeQuery(sql);
            while (res.next()) {
                Category category = new Category();
                category.setId(res.getInt(5));
                category.setName(res.getString(6));
                Equipment item = new Equipment();
                item.setId(res.getInt(1));
                item.setName(res.getString(2));
                item.setPrice(res.getDouble(3));
                item.setQuantity(res.getInt(4));
                item.setCategory(category);
                items.add(item);
            }
        }catch (SQLException e){
            throw new DaoException(e);
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
    public List<Equipment> getEquipmentsByCategory(int categoryId) throws DaoException {
        Connection con = null;
        PreparedStatement ps = null;
        ResultSet res = null;
        List<Equipment> items = new ArrayList<Equipment>();
        try {
            con = DriverManager.getConnection(jdbcUrl, jdbcLogin, jdbcPassword);
            String sql = "SELECT eq.id, eq.name, eq.price, eq.quantity, cat.id, cat.name FROM equipments AS eq LEFT JOIN categories AS cat ON cat.id = eq.category_id WHERE eq.category_id = ?";
            ps = con.prepareStatement(sql);
            ps.setInt(1, categoryId);
            res = ps.executeQuery();
            while (res.next()) {
                Category cat = new Category();
                cat.setId(res.getInt(5));
                cat.setName(res.getString(6));
                Equipment item = new Equipment();
                item.setId(res.getInt(1));
                item.setName(res.getString(2));
                item.setPrice(res.getDouble(3));
                item.setQuantity(res.getInt(4));
                item.setCategory(cat);
                items.add(item);
            }
        }catch (SQLException e){
            throw new DaoException(e);
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
    public Equipment getEquipmentById(int id) throws DaoException {
        Connection con = null;
        PreparedStatement ps = null;
        ResultSet res = null;
        Equipment item = null;
        try {
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
                category.setId(res.getInt(5));
                category.setName(res.getString(6));
                item = new Equipment();
                item.setId(res.getInt(1));
                item.setName(res.getString(2));
                item.setPrice(res.getDouble(3));
                item.setQuantity(res.getInt(4));
                item.setCategory(category);
            }
        }catch (SQLException e){
            throw new DaoException(e);
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
    public void addEquipment(Equipment equipment) throws DaoException {
        Connection con = null;
        PreparedStatement ps = null;
        try {
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
        }catch (SQLException e){
            throw new DaoException(e);
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
    public void removeEquipment(int itemId) throws DaoException {
        Connection con = null;
        PreparedStatement ps = null;
        try {
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
        }catch (SQLException e){
            throw new DaoException(e);
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
