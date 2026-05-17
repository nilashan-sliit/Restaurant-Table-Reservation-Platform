package com.restaurant.user.repository;

import com.restaurant.user.model.RegularUser;
import com.restaurant.user.model.User;
import com.restaurant.user.model.VIPUser;
import org.springframework.stereotype.Repository;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Repository
public class UserRepository {

    private static final String FILE_PATH =
            System.getProperty("user.dir") + "/data/users.txt";

    // Read all users from file
    public List<User> findAll() throws IOException {
        List<User> users = new ArrayList<>();
        File file = new File(FILE_PATH);

        // If file doesn't exist yet, return empty list
        if (!file.exists()) return users;

        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = reader.readLine()) != null) {
                if (!line.isBlank()) {
                    users.add(parseLine(line));
                }
            }
        }
        return users;
    }

    // Find by userId
    public Optional<User> findById(String userId) throws IOException {
        return findAll().stream()
                .filter(u -> u.getUserId().equals(userId))
                .findFirst();
    }

    // Find by username
    public Optional<User> findByUsername(String username) throws IOException {
        return findAll().stream()
                .filter(u -> u.getUsername().equals(username))
                .findFirst();
    }

    // Save a new user (append to file)
    public void save(User user) throws IOException {
        ensureFileExists();
        try (BufferedWriter writer = new BufferedWriter(
                new FileWriter(FILE_PATH, true))) {
            writer.write(serialize(user));
            writer.newLine();
        }
    }

    // Update existing user (rewrite whole file)
    public void update(User updated) throws IOException {
        List<User> users = findAll();
        try (BufferedWriter writer = new BufferedWriter(
                new FileWriter(FILE_PATH, false))) {
            for (User u : users) {
                if (u.getUserId().equals(updated.getUserId())) {
                    writer.write(serialize(updated));
                } else {
                    writer.write(serialize(u));
                }
                writer.newLine();
            }
        }
    }

    // Delete by userId (rewrite file without that user)
    public void delete(String userId) throws IOException {
        List<User> users = findAll();
        try (BufferedWriter writer = new BufferedWriter(
                new FileWriter(FILE_PATH, false))) {
            for (User u : users) {
                if (!u.getUserId().equals(userId)) {
                    writer.write(serialize(u));
                    writer.newLine();
                }
            }
        }
    }

    // Convert a line from file → User object
    private User parseLine(String line) {
        String[] parts = line.split("\\|");
        // Format: userId|username|password|email|phone|membershipType|registrationDate
        String type = parts[5];
        if (type.equals("VIP")) {
            VIPUser u = new VIPUser(parts[0], parts[1], parts[2], parts[3], parts[4]);
            u.setRegistrationDate(parts[6]);
            return u;
        } else {
            RegularUser u = new RegularUser(parts[0], parts[1], parts[2], parts[3], parts[4]);
            u.setRegistrationDate(parts[6]);
            return u;
        }
    }

    // Convert a User object → pipe-separated line
    private String serialize(User user) {
        return String.join("|",
                user.getUserId(),
                user.getUsername(),
                user.getPassword(),
                user.getEmail(),
                user.getPhone(),
                user.getMembershipType(),
                user.getRegistrationDate());
    }

    // Create file and parent directories if they don't exist
    private void ensureFileExists() throws IOException {
        File file = new File(FILE_PATH);
        if (!file.exists()) {
            file.getParentFile().mkdirs();
            file.createNewFile();
        }
    }
}