package xyz.hedo.shop.dao;

import xyz.hedo.shop.dao.exception.DaoException;
import xyz.hedo.shop.bean.Equipment;

import java.util.List;

public interface EquipmentDao {

    List<Equipment> getAllEquipments() throws DaoException;
    List<Equipment> getEquipmentsByCategory(int categoryId) throws DaoException;
    Equipment getEquipmentById(int id) throws DaoException;
    void addEquipment(Equipment equipment) throws DaoException;
    void removeEquipment(int id) throws DaoException;
}
