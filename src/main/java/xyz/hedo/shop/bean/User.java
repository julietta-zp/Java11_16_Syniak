package xyz.hedo.shop.bean;

import java.io.Serializable;
import java.util.Date;

/**
 * User java-bean
 */

public class User implements Serializable {

    private int id;
    private String firstName;
    private String lastName;
    // for signing in
    private String email;
    // store as hash
    private String password;
    private Date createdAt;
    private Date updatedAt;

    public User() {
    }

    public User(String firstName, String lastName, Date createdAt) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.createdAt = createdAt;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
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
