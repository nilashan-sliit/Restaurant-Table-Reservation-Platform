package com.restaurant.user.model;

public class User {

    private String userId;
    private String username;
    private String password;
    private String email;
    private String phone;
    private String membershipType;
    private String registrationDate;

    // Full constructor
    public User(String userId, String username, String password,
                String email, String phone,
                String membershipType, String registrationDate) {
        this.userId = userId;
        this.username = username;
        this.password = password;
        this.email = email;
        this.phone = phone;
        this.membershipType = membershipType;
        this.registrationDate = registrationDate;
    }

    // Convenience constructor (auto-sets today's date)
    public User(String userId, String username, String password,
                String email, String phone, String membershipType) {
        this(userId, username, password, email, phone, membershipType,
                java.time.LocalDate.now().toString());
    }

    // Getters
    public String getUserId()           { return userId; }
    public String getUsername()         { return username; }
    public String getPassword()         { return password; }
    public String getEmail()            { return email; }
    public String getPhone()            { return phone; }
    public String getMembershipType()   { return membershipType; }
    public String getRegistrationDate() { return registrationDate; }

    // Setters
    public void setUserId(String userId)                   { this.userId = userId; }
    public void setUsername(String username)               { this.username = username; }
    public void setPassword(String password)               { this.password = password; }
    public void setEmail(String email)                     { this.email = email; }
    public void setPhone(String phone)                     { this.phone = phone; }
    public void setMembershipType(String membershipType)   { this.membershipType = membershipType; }
    public void setRegistrationDate(String registrationDate) { this.registrationDate = registrationDate; }

    // Polymorphism method — overridden in subclasses
    public String getUserType() {
        return membershipType;
    }
}