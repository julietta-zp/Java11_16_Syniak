package xyz.hedo.shop.bean;

import java.io.Serializable;
import java.util.Date;

/**
 * Each Order was made by one {@link User}
 */

public class Order implements Serializable{
    private int id;
    // user_id needed
    private User user;
    private Date dateTimeFrom;
    private Date dateTimeTo;
    private double totalPrice;
    // checks if order is active or closed
    private boolean active;
    private Date createdAt;
    private Date updatedAt;

    public Order() {
    }

    public Order(int id, User user, Date dateTimeFrom, Date dateTimeTo, double totalPrice, boolean active, Date createdAt) {
        this.id = id;
        this.user = user;
        this.dateTimeFrom = dateTimeFrom;
        this.dateTimeTo = dateTimeTo;
        this.totalPrice = totalPrice;
        this.active = active;
        this.createdAt = createdAt;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Date getDateTimeFrom() {
        return dateTimeFrom;
    }

    public void setDateTimeFrom(Date dateTimeFrom) {
        this.dateTimeFrom = dateTimeFrom;
    }

    public Date getDateTimeTo() {
        return dateTimeTo;
    }

    public void setDateTimeTo(Date dateTimeTo) {
        this.dateTimeTo = dateTimeTo;
    }

    public double getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(double totalPrice) {
        this.totalPrice = totalPrice;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public Date getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(Date updatedAt) {
        this.updatedAt = updatedAt;
    }

}
