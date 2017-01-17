package xyz.hedo.shop.DAO.impl;

import xyz.hedo.shop.DAO.OrderEquipmentsDAO;
import xyz.hedo.shop.DAO.exception.DAOException;
import xyz.hedo.shop.bean.Category;
import xyz.hedo.shop.bean.Equipment;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class OrderEquipmentsDAOImpl implements OrderEquipmentsDAO {

    private final String jdbcDriver = "org.gjt.mm.mysql.Driver";
    private final String jdbcUrl = "jdbc:mysql://127.0.0.1/rent_shop";
    private final String jdbcLogin = "root";
    private final String jdbcPassword = "root";

    @Override
    public List<Integer> getOrderEquipmentIds(int orderId) throws DAOException{
        Connection con = null;
        PreparedStatement ps = null;
        ResultSet res = null;
        List<Integer> orderEquipmentIds = new ArrayList<>();
        try {
            Class.forName(jdbcDriver);
            con = DriverManager.getConnection(jdbcUrl, jdbcLogin, jdbcPassword);
            String sql = "SELECT equipment_id AS item FROM rent_shop.orders_m2m_equipments AS oe WHERE order_id = ?";
            ps = con.prepareStatement(sql);
            ps.setInt(1, orderId);
            res = ps.executeQuery();
            while (res.next()) {
                orderEquipmentIds.add(res.getInt("item"));
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
        return orderEquipmentIds;
    }

    // returns map of equipment ids and its quantity
    @Override
    public Map<Integer, Integer> countBorrowedEquipments() throws DAOException {
        Connection con = null;
        Statement st = null;
        ResultSet res = null;
        Map<Integer, Integer> borrowedEquipmentsQuantity = new HashMap<>();
        try {
            Class.forName(jdbcDriver);
            con = DriverManager.getConnection(jdbcUrl, jdbcLogin, jdbcPassword);
            String sql = "SELECT equipment_id, count(equipment_id) AS qty FROM rent_shop.orders_m2m_equipments AS oe LEFT JOIN rent_shop.orders AS o ON oe.order_id = o.id WHERE o.active = 1 GROUP BY equipment_id";
            st = con.createStatement();
            res = st.executeQuery(sql);
            while (res.next()) {
                borrowedEquipmentsQuantity.put(res.getInt("equipment_id"), res.getInt("qty"));
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
        return borrowedEquipmentsQuantity;
    }

    @Override
    public void addEquipmentsToOrder(int orderId, List<Equipment> equipments) throws DAOException {
        Connection con = null;
        PreparedStatement ps = null;
        try {
            Class.forName(jdbcDriver);
            con = DriverManager.getConnection(jdbcUrl, jdbcLogin, jdbcPassword);
            StringBuilder sql = new StringBuilder("INSERT INTO orders_m2m_equipments(order_id, equipment_id) VALUES(?,?)");
            for (int i = 0; i < equipments.size()-1; i++) {
                sql.append(", (?, ?)");
            }
            ps = con.prepareStatement(sql.toString());
            int counter = 0;
            for (Equipment item : equipments){
                ps.setInt(++counter, orderId);
                ps.setInt(++counter, item.getId());
            }
            int rows = ps.executeUpdate();
            if (rows == 0) {
                System.out.println("Something went wrong.");
            } else if (rows > 0) {
                System.out.println("Order items were successfully added");
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
    public List<Equipment> getOrderEquipments(int orderId) throws DAOException {
        Connection con = null;
        PreparedStatement ps = null;
        ResultSet res = null;
        List<Equipment> orderEquipments = new ArrayList<>();
        try {
            Class.forName(jdbcDriver);
            con = DriverManager.getConnection(jdbcUrl, jdbcLogin, jdbcPassword);
            String sql = "SELECT equipment_id, e.name, e.price, e.quantity, c.name, c.id FROM rent_shop.orders_m2m_equipments AS oe LEFT JOIN rent_shop.equipments AS e ON oe.equipment_id = e.id LEFT JOIN rent_shop.categories AS c ON e.category_id = c.id WHERE oe.order_id = ?";
            ps = con.prepareStatement(sql);
            ps.setInt(1, orderId);
            res = ps.executeQuery();
            while (res.next()) {
                Equipment equipment = new Equipment();
                equipment.setId(res.getInt("equipment_id"));
                equipment.setName(res.getString("name"));
                equipment.setPrice(res.getDouble("price"));
                equipment.setQuantity(res.getInt("quantity"));
                Category category = new Category();
                category.setName(res.getString("c.name"));
                category.setId(res.getInt("c.id"));
                equipment.setCategory(category);
                orderEquipments.add(equipment);
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
        return orderEquipments;
    }
}
