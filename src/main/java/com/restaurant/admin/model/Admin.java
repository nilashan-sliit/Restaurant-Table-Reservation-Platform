package com.restaurant.admin.model;

public class Admin {
    private String Id;
    private String name;

    public Admin(String id, String name, String password, String email, String phone) {
        Id = id;
        this.name = name;
        this.password = password;
        this.email = email;
        this.phone = phone;
    }

    public String getId() {
        return Id;
    }

    public void setId(String id) {
        Id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    private String password;
    private String email;
    private String phone;
}
