package com.restaurant.user.service;

import com.restaurant.user.model.RegularUser;
import com.restaurant.user.model.User;
import com.restaurant.user.model.VIPUser;
import com.restaurant.user.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    private final UserRepository repo;

    public UserService(UserRepository repo) {
        this.repo = repo;
    }

    // Register a new user
    public void register(String username, String password,
                         String email, String phone,
                         String membershipType) throws IOException {
        // Check for duplicate username
        if (repo.findByUsername(username).isPresent()) {
            throw new IllegalArgumentException("Username already exists.");
        }
        String userId = "U" + System.currentTimeMillis();
        User user = membershipType.equalsIgnoreCase("VIP")
                ? new VIPUser(userId, username, password, email, phone)
                : new RegularUser(userId, username, password, email, phone);
        repo.save(user);
    }

    // Login — returns user if credentials match
    public Optional<User> login(String username, String password) throws IOException {
        return repo.findByUsername(username)
                .filter(u -> u.getPassword().equals(password));
    }

    // Get all users (admin)
    public List<User> getAllUsers() throws IOException {
        return repo.findAll();
    }

    // Get single user by ID
    public Optional<User> getUserById(String userId) throws IOException {
        return repo.findById(userId);
    }

    // Update user details
    public void updateUser(String userId, String email,
                           String phone, String password) throws IOException {
        User user = repo.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("User not found."));
        user.setEmail(email);
        user.setPhone(phone);
        user.setPassword(password);
        repo.update(user);
    }

    // Delete a user
    public void deleteUser(String userId) throws IOException {
        repo.delete(userId);
    }
}