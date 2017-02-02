package xyz.hedo.shop.dao.impl;

import xyz.hedo.shop.dao.UserDao;
import xyz.hedo.shop.dao.exception.DaoException;
import xyz.hedo.shop.bean.User;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserDaoImpl implements UserDao {

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
    public void signIn(String email, String password) throws DaoException {
        Connection con = null;
        PreparedStatement ps = null;
        try {
            con = DriverManager.getConnection(jdbcUrl, jdbcLogin, jdbcPassword);
            String sql = "UPDATE users SET users.status = 1 WHERE users.email = ? AND users.password = ?";
            ps = con.prepareStatement(sql);
            ps.setString(1, email);
            ps.setString(2, password);
            int rows = ps.executeUpdate();
            if (rows == 0) {
                System.out.println("Something went wrong.");
            } else if (rows > 0) {
                System.out.println("You were successfully signed in!");
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
    public void signOut(String email) throws DaoException {
        Connection con = null;
        PreparedStatement ps = null;
        try {
            con = DriverManager.getConnection(jdbcUrl, jdbcLogin, jdbcPassword);
            String sql = "UPDATE users SET users.status = 0 WHERE users.email = ?";
            ps = con.prepareStatement(sql);
            ps.setString(1, email);
            int rows = ps.executeUpdate();
            if (rows == 0) {
                System.out.println("Something went wrong.");
            } else if (rows > 0) {
                System.out.println("You were successfully signed out!");
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
    public List<User> getAllUsers() throws DaoException {
        Connection con = null;
        Statement st = null;
        ResultSet res = null;
        List<User> users = new ArrayList<User>();
        try {
            con = DriverManager.getConnection(jdbcUrl, jdbcLogin, jdbcPassword);
            String sql = "SELECT id, first_name, last_name, email FROM users";
            st = con.createStatement();
            res = st.executeQuery(sql);
            while (res.next()) {
                User user = new User();
                user.setId(res.getInt(1));
                user.setFirstName(res.getString(2));
                user.setLastName(res.getString(3));
                user.setEmail(res.getString(4));
                users.add(user);
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
        return users;
    }

    @Override
    public User getUserById(int id) throws DaoException {
        Connection con = null;
        PreparedStatement ps = null;
        ResultSet res = null;
        User user = null;
        try {
            con = DriverManager.getConnection(jdbcUrl, jdbcLogin, jdbcPassword);
            String sql = "SELECT id, first_name, last_name, email, created_at, updated_at FROM users WHERE id = ?";
            ps = con.prepareStatement(sql);
            ps.setInt(1, id);
            res = ps.executeQuery();
            if (!res.isBeforeFirst() ) {
                System.out.println("No such user was found");
            }
            while (res.next()) {
                user = new User();
                user.setId(res.getInt(1));
                user.setFirstName(res.getString(2));
                user.setLastName(res.getString(3));
                user.setEmail(res.getString(4));
                user.setCreatedAt(res.getTimestamp(5));
                user.setUpdatedAt(res.getTimestamp(6));
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
        return user;
    }

    @Override
    public void addUser(User user) throws DaoException {
        Connection con = null;
        PreparedStatement ps = null;
        try {
            con = DriverManager.getConnection(jdbcUrl, jdbcLogin, jdbcPassword);
            String sql = "INSERT INTO users(first_name, last_name, email, password) VALUES(?,?,?,?)";
            ps = con.prepareStatement(sql);
            ps.setString(1, user.getFirstName());
            ps.setString(2, user.getLastName());
            ps.setString(3, user.getEmail());
            ps.setString(4, user.getPassword());
            int rows = ps.executeUpdate();
            if (rows == 0) {
                System.out.println("Something went wrong.");
            } else if (rows > 0) {
                System.out.println("Profile was successfully created!");
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
    public void removeUser(int userId) throws DaoException {
        Connection con = null;
        PreparedStatement ps = null;
        try {
            con = DriverManager.getConnection(jdbcUrl, jdbcLogin, jdbcPassword);
            String sql = "DELETE FROM users WHERE id = ?";
            ps = con.prepareStatement(sql);
            ps.setInt(1, userId);
            int rows = ps.executeUpdate();
            if (rows == 0) {
                System.out.println("Nothing to delete. No such category");
            } else if (rows > 0) {
                System.out.println("User was successfully deleted");
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
