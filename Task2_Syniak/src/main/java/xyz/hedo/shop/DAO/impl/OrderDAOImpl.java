package xyz.hedo.shop.DAO.impl;

import xyz.hedo.shop.DAO.OrderDAO;
import xyz.hedo.shop.DAO.exception.DAOException;
import xyz.hedo.shop.bean.Order;
import xyz.hedo.shop.bean.User;

import java.sql.*;
import java.util.*;
import java.util.Date;

public class OrderDAOImpl implements OrderDAO {

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
    public List<Order> getAllOrders() throws DAOException{
        Connection con = null;
        Statement st = null;
        ResultSet res = null;
        List<Order> orders = new ArrayList<Order>();
        try {
            con = DriverManager.getConnection(jdbcUrl, jdbcLogin, jdbcPassword);
            String sql = "SELECT o.id, o.user_id, o.datetime_from, o.datetime_to, o.total_price, o.created_at, u.first_name, u.last_name FROM orders AS o LEFT JOIN users AS u ON o.user_id = u.id";
            st = con.createStatement();
            res = st.executeQuery(sql);
            while (res.next()) {
                Order order = new Order();
                order.setId(res.getInt(1));
                User user = new User();
                user.setId(res.getInt(2));
                user.setFirstName(res.getString(7));
                user.setLastName(res.getString(8));
                order.setUser(user);
                order.setDateTimeFrom(res.getDate(3));
                order.setDateTimeTo(res.getDate(4));
                order.setTotalPrice(res.getDouble(5));
                order.setCreatedAt(res.getTimestamp(6));
                orders.add(order);
            }
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
        return orders;
    }

    @Override
    public Order getOrderById(int id) throws DAOException {
        Connection con = null;
        PreparedStatement ps = null;
        ResultSet res = null;
        Order order = new Order();
        try {
            con = DriverManager.getConnection(jdbcUrl, jdbcLogin, jdbcPassword);
            String sql = "SELECT o.user_id, o.datetime_from, o.datetime_to, o.total_price, o.active, o.created_at, u.first_name, u.last_name FROM orders AS o LEFT JOIN users AS u ON o.user_id = u.id WHERE o.id = ?";
            ps = con.prepareStatement(sql);
            ps.setInt(1, id);
            res = ps.executeQuery();
            while (res.next()) {
                order.setId(id);
                User user = new User();
                user.setId(res.getInt(1));
                user.setFirstName(res.getString(7));
                user.setLastName(res.getString(8));
                order.setUser(user);
                order.setActive(res.getBoolean(5));
                order.setDateTimeFrom(res.getDate(2));
                order.setDateTimeTo(res.getDate(3));
                order.setTotalPrice(res.getDouble(4));
                order.setCreatedAt(res.getTimestamp(6));
            }
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
        return order;
    }

    @Override
    public void closeOrder(int id) throws DAOException {
        Connection con = null;
        PreparedStatement ps = null;
        try {
            con = DriverManager.getConnection(jdbcUrl, jdbcLogin, jdbcPassword);
            String sql = "UPDATE orders SET orders.active = 0 WHERE orders.id = ?";
            ps = con.prepareStatement(sql);
            ps.setInt(1, id);
            int rows = ps.executeUpdate();
            if (rows == 0) {
                System.out.println("Something went wrong.");
            } else if (rows > 0) {
                System.out.println("The equipments were successfully returned!");
            }
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

    // returns created order id
    @Override
    public Integer createOrder(int userId, Date datetimeFrom, Date datetimeTo, double totalPrice) throws DAOException {
        Connection con = null;
        PreparedStatement ps = null;
        ResultSet res = null;
        int createdOrderId = 0;
        try {
            con = DriverManager.getConnection(jdbcUrl, jdbcLogin, jdbcPassword);
            String sql = "INSERT INTO orders(user_id, datetime_from, datetime_to, total_price) VALUES(?,?,?,?)";
            ps = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            ps.setInt(1, userId);
            ps.setDate(2, new java.sql.Date(datetimeFrom.getTime()));
            ps.setDate(3, new java.sql.Date(datetimeTo.getTime()));
            ps.setDouble(4, totalPrice);
            int rows = ps.executeUpdate();
            res = ps.getGeneratedKeys();
            if (rows == 0) {
                System.out.println("Something went wrong.");
            } else if (rows > 0) {
                if(res.next())
                {
                    createdOrderId = res.getInt(1);
                }
                System.out.println("Order was successfully created");
            }
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
        return createdOrderId;
    }

    @Override
    public List<Order> getAllActiveOrders() throws DAOException {
        Connection con = null;
        Statement st = null;
        ResultSet res = null;
        List<Order> orders = new ArrayList<Order>();
        try {
            con = DriverManager.getConnection(jdbcUrl, jdbcLogin, jdbcPassword);
            String sql = "SELECT o.id, o.user_id, o.datetime_from, o.datetime_to, o.total_price, o.created_at, u.first_name, u.last_name, u.email FROM orders AS o LEFT JOIN users AS u ON o.user_id = u.id WHERE  o.active = 1";
            st = con.createStatement();
            res = st.executeQuery(sql);
            while (res.next()) {
                Order order = new Order();
                order.setId(res.getInt(1));
                User user = new User();
                user.setId(res.getInt(2));
                user.setFirstName(res.getString(7));
                user.setLastName(res.getString(8));
                user.setEmail(res.getString(9));
                order.setUser(user);
                order.setDateTimeFrom(res.getDate(3));
                order.setDateTimeTo(res.getDate(4));
                order.setTotalPrice(res.getDouble(5));
                order.setCreatedAt(res.getTimestamp(6));
                orders.add(order);
            }
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
        return orders;
    }

    @Override
    public List<Order> getAllExpiredOrders() throws DAOException {
        Connection con = null;
        Statement st = null;
        ResultSet res = null;
        List<Order> orders = new ArrayList<Order>();
        try {
            con = DriverManager.getConnection(jdbcUrl, jdbcLogin, jdbcPassword);
            String sql = "SELECT o.id, o.user_id, o.datetime_from, o.datetime_to, o.total_price, o.created_at, u.first_name, u.last_name, u.email FROM orders AS o LEFT JOIN users AS u ON o.user_id = u.id WHERE  o.datetime_to < CURRENT_DATE ";
            st = con.createStatement();
            res = st.executeQuery(sql);
            while (res.next()) {
                Order order = new Order();
                order.setId(res.getInt(1));
                User user = new User();
                user.setId(res.getInt(2));
                user.setFirstName(res.getString(7));
                user.setLastName(res.getString(8));
                user.setEmail(res.getString(9));
                order.setUser(user);
                order.setDateTimeFrom(res.getDate(3));
                order.setDateTimeTo(res.getDate(4));
                order.setTotalPrice(res.getDouble(5));
                order.setCreatedAt(res.getTimestamp(6));
                orders.add(order);
            }
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
        return orders;
    }
}
