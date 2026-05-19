package com.restaurant.user;

public class VIPUser extends User {

    // Constructor - creates a VIP user
    public VIPUser(String userId, String username, String password,
                   String email, String phone) {
        // Call the parent User class
        super(userId, username, password, email, phone, "VIP");
    }

    // This is polymorphism - different message for VIP users
    @Override
    public String getUserType() {
        return "VIP User (with discount)";
    }
}
