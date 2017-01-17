package xyz.hedo.shop.DAO;

import xyz.hedo.shop.DAO.exception.DAOException;
import xyz.hedo.shop.bean.Equipment;

import java.util.List;

public interface EquipmentDAO {

    List<Equipment> getAllEquipments() throws DAOException;
    List<Equipment> getEquipmentsByCategory(int categoryId) throws DAOException;
    Equipment getEquipmentById(int id) throws DAOException;
    void addEquipment(Equipment equipment) throws DAOException;
    void removeEquipment(int id) throws DAOException;
}
