package com.restaurant.user.model;

public class VIPUser extends User {

    public VIPUser(String userId, String username, String password,
                   String email, String phone) {
        super(userId, username, password, email, phone, "VIP",
                java.time.LocalDate.now().toString());
    }

    @Override
    public String getUserType() {
        return "VIP User (with discount)";
    }
}