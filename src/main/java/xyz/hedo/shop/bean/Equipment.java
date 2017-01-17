package xyz.hedo.shop.bean;

import java.io.Serializable;
import java.util.Date;

/**
 * Equipment item to rent
 */

public class Equipment implements Serializable{
    private int id;
    private String name;
    // unit price
    private double price;
    // total quantity of this type of equipment
    private int quantity;
    // category_id needed
    private Category category;
    private Date createdAt;
    private Date updatedAt;

    public Equipment() {
    }

    public Equipment(int id, String name, double price, int quantity, Category category, Date createdAt) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.quantity = quantity;
        this.category = category;
        this.createdAt = createdAt;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
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
