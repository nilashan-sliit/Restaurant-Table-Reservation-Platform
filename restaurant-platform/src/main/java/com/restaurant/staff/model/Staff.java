package com.restaurant.staff.model;

public class Staff {
    private String id;
    private String name;
    private String role;
    private String contact;

    // Default Constructor
    public Staff() {}

    // Overloaded Constructor
    public Staff(String id, String name, String role, String contact) {
        this.id = id;
        this.name = name;
        this.role = role;
        this.contact = contact;
    }

    // Getters and Setters
    public String getId() { return id; }
    public void setId(String id) { this.id = id; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public String getRole() { return role; }
    public void setRole(String role) { this.role = role; }
    public String getContact() { return contact; }
    public void setContact(String contact) { this.contact = contact; }
}