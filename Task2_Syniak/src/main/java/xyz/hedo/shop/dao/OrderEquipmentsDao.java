package xyz.hedo.shop.dao;

import xyz.hedo.shop.dao.exception.DaoException;
import xyz.hedo.shop.bean.Equipment;

import java.util.List;
import java.util.Map;

public interface OrderEquipmentsDao {

    List<Integer> getOrderEquipmentIds (int orderId) throws DaoException;
    List<Equipment> getOrderEquipments (int orderId) throws DaoException;
    Map<Integer, Integer> countBorrowedEquipments() throws DaoException;
    void addEquipmentsToOrder (int orderId, List<Equipment> equipments) throws DaoException;
}
