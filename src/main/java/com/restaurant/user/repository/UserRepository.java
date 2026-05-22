package com.restaurant.user.repository;

import com.restaurant.user.model.RegularUser;
import com.restaurant.user.model.User;
import com.restaurant.user.model.VIPUser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Repository
public class UserRepository {

    private static final Logger log = LoggerFactory.getLogger(UserRepository.class);

    // Relative path inside the working directory (do NOT start with '/')
    private static final Path FILE_PATH = Paths.get("data", "users.txt");

    public UserRepository() {
        // Log the absolute path we will use so you can verify it at runtime
        log.info("UserRepository using file: {}", FILE_PATH.toAbsolutePath());

        // Optionally, log whether the file exists right now
        try {
            log.info("users file exists: {}", Files.exists(FILE_PATH));
        } catch (Exception ex) {
            log.warn("Could not check users file existence: {}", ex.getMessage());
        }
    }

    // Read all users from file
    public List<User> findAll() throws IOException {
        List<User> users = new ArrayList<>();

        if (Files.notExists(FILE_PATH)) {
            log.debug("findAll: users file does not exist at {}", FILE_PATH.toAbsolutePath());
            return users;
        }

        int linesRead = 0;
        try (BufferedReader reader = Files.newBufferedReader(FILE_PATH, StandardCharsets.UTF_8)) {
            String line;
            while ((line = reader.readLine()) != null) {
                linesRead++;
                if (!line.isBlank()) {
                    try {
                        users.add(parseLine(line));
                    } catch (Exception ex) {
                        log.warn("Skipping malformed user line (line {}): {} — {}", linesRead, line, ex.getMessage());
                    }
                }
            }
        }

        log.info("findAll: read {} lines, returning {} users from {}", linesRead, users.size(), FILE_PATH.toAbsolutePath());
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
    public synchronized void save(User user) throws IOException {
        ensureFileExists();
        try (BufferedWriter writer = Files.newBufferedWriter(FILE_PATH, StandardCharsets.UTF_8, StandardOpenOption.APPEND)) {
            writer.write(serialize(user));
            writer.newLine();
            writer.flush();
        }
        log.info("Saved user {} to {}", user.getUsername(), FILE_PATH.toAbsolutePath());
    }

    // Update existing user (rewrite whole file)
    public synchronized void update(User updated) throws IOException {
        List<User> users = findAll();
        try (BufferedWriter writer = Files.newBufferedWriter(FILE_PATH, StandardCharsets.UTF_8, StandardOpenOption.TRUNCATE_EXISTING)) {
            for (User u : users) {
                if (u.getUserId().equals(updated.getUserId())) {
                    writer.write(serialize(updated));
                } else {
                    writer.write(serialize(u));
                }
                writer.newLine();
            }
        }
        log.info("Updated user {} in {}", updated.getUserId(), FILE_PATH.toAbsolutePath());
    }

    // Delete by userId (rewrite file without that user)
    public synchronized void delete(String userId) throws IOException {
        List<User> users = findAll();
        try (BufferedWriter writer = Files.newBufferedWriter(FILE_PATH, StandardCharsets.UTF_8, StandardOpenOption.TRUNCATE_EXISTING)) {
            for (User u : users) {
                if (!u.getUserId().equals(userId)) {
                    writer.write(serialize(u));
                    writer.newLine();
                }
            }
        }
        log.info("Deleted user {} from {}", userId, FILE_PATH.toAbsolutePath());
    }

    // Convert a line from file → User object
    private User parseLine(String line) {
        // Use -1 to keep empty trailing fields if any
        String[] parts = line.split("\\|", -1);
        if (parts.length < 7) {
            throw new IllegalArgumentException("Malformed user line, expected 7 parts but got " + parts.length);
        }
        // Format: userId|username|password|email|phone|membershipType|registrationDate
        String type = parts[5];
        if ("VIP".equals(type)) {
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
        Path parent = FILE_PATH.getParent();
        if (parent != null && Files.notExists(parent)) {
            Files.createDirectories(parent);
            log.info("Created parent directory {}", parent.toAbsolutePath());
        }
        if (Files.notExists(FILE_PATH)) {
            Files.createFile(FILE_PATH);
            log.info("Created users file at {}", FILE_PATH.toAbsolutePath());
        }
    }
}