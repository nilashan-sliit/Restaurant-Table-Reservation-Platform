package com.restaurant.user.model;

public class RegularUser extends User {

    public RegularUser(String userId, String username, String password,
                       String email, String phone) {
        super(userId, username, password, email, phone, "Regular",
                java.time.LocalDate.now().toString());
    }

    @Override
    public String getUserType() {
        return "Regular User";
    }
}