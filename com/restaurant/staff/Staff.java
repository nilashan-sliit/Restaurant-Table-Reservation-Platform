package com.restaurant.staff;

public class Staff {
    // Private variables (Encapsulation)
    private String id;
    private String name;
    private String role;
    private String contact;

    public Staff(String id, String name, String role, String contact) {
        this.id = id;
        this.name = name;
        this.role = role;
        this.contact = contact;
    }

    // Public Getters
    public String getId() { return id; }
    public String getName() { return name; }
    public String getRole() { return role; }
    public String getContact() { return contact; }
}