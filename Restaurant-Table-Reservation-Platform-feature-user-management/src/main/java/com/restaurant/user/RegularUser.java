package com.restaurant.user;

public class RegularUser extends User {

    // Constructor - creates a Regular user
    public RegularUser(String userId, String username, String password,
                       String email, String phone) {
        // Call the parent User class and set "Regular"
        super(userId, username, password, email, phone, "Regular");
    }

    // This is polymorphism - different message for Regular users
    @Override
    public String getUserType() {
        return "Regular User";
    }
}