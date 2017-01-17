package xyz.hedo.shop.DAO;

import xyz.hedo.shop.DAO.exception.DAOException;
import xyz.hedo.shop.bean.Equipment;

import java.util.List;
import java.util.Map;

public interface OrderEquipmentsDAO {

    List<Integer> getOrderEquipmentIds (int orderId) throws DAOException;
    List<Equipment> getOrderEquipments (int orderId) throws DAOException;
    Map<Integer, Integer> countBorrowedEquipments() throws DAOException;
    void addEquipmentsToOrder (int orderId, List<Equipment> equipments) throws DAOException;
}
