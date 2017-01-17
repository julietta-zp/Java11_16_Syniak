package xyz.hedo.shop.bean;

import java.io.Serializable;
import java.util.Date;

/**
 * Each {@link Equipment} has a category
 */
public class Category implements Serializable{

    private int id;
    private String name;
    private Date createdAt;
    private Date updatedAt;

    public Category() {
    }

    public Category(int id, String name, Date createdAt) {
        this.id = id;
        this.name = name;
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
