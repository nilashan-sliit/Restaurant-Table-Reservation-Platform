package com.restaurant.staff;

// Inheritance: Manager inherits everything from Staff
public class Manager extends Staff {
    public Manager(String id, String name, String contact) {
        // 'super' sends the data up to the Staff class you just wrote
        super(id, name, "Manager", contact);
    }
}