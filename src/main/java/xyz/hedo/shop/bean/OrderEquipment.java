package xyz.hedo.shop.bean;

import java.io.Serializable;

/**
 * Each {@link Order} has many {@link Equipment} in it
 */

public class OrderEquipment implements Serializable {

    private Order order;
    private Equipment equipment;

    public OrderEquipment() {
    }

    public OrderEquipment(Order order, Equipment equipment) {
        this.order = order;
        this.equipment = equipment;
    }

    public Order getOrder() {
        return order;
    }

    public void setOrder(Order order) {
        this.order = order;
    }

    public Equipment getEquipment() {
        return equipment;
    }

    public void setEquipment(Equipment equipment) {
        this.equipment = equipment;
    }
}
