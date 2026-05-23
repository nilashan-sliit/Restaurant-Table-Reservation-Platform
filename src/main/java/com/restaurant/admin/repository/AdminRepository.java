package com.restaurant.admin.repository;

import com.restaurant.admin.model.Admin;
import org.springframework.stereotype.Repository;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Repository
public class AdminRepository {

    private static final String FILE_PATH =
            System.getProperty("user.dir") + "/data/admins.txt";

    // Find all admins
    public List<Admin> findAll() throws IOException {

        ensureFileExists();

        List<Admin> admins = new ArrayList<>();

        File file = new File(FILE_PATH);

        if (!file.exists()) {
            return admins;
        }

        try (BufferedReader reader =
                     new BufferedReader(
                             new FileReader(file))) {

            String line;

            while ((line = reader.readLine()) != null) {

                if (line.isBlank() || !line.contains("|")) {
                    continue;
                }

                admins.add(parseLine(line));
            }
        }

        return admins;
    }

    // Find by ID
    public Optional<Admin> findById(String id)
            throws IOException {

        ensureFileExists();

        return findAll().stream()
                .filter(a -> a.getId().equals(id))
                .findFirst();
    }

    // Find by username
    public Optional<Admin> findByUsername(String name)
            throws IOException {

        ensureFileExists();

        return findAll().stream()
                .filter(a -> a.getName().equals(name))
                .findFirst();
    }

    // Save admin
    public void save(Admin admin)
            throws IOException {

        ensureFileExists();

        try (BufferedWriter writer =
                     new BufferedWriter(
                             new FileWriter(FILE_PATH, true))) {

            writer.write(serialize(admin));
            writer.newLine();
        }
    }

    // Update admin
    public void update(Admin updated)
            throws IOException {

        ensureFileExists();

        List<Admin> admins = findAll();

        try (BufferedWriter writer =
                     new BufferedWriter(
                             new FileWriter(FILE_PATH, false))) {

            for (Admin admin : admins) {

                if (admin.getId()
                        .equals(updated.getId())) {

                    writer.write(
                            serialize(updated));

                } else {

                    writer.write(
                            serialize(admin));
                }

                writer.newLine();
            }
        }
    }

    // Delete admin
    public void delete(String id)
            throws IOException {

        ensureFileExists();

        List<Admin> admins = findAll();

        try (BufferedWriter writer =
                     new BufferedWriter(
                             new FileWriter(FILE_PATH, false))) {

            for (Admin admin : admins) {

                if (!admin.getId().equals(id)) {

                    writer.write(
                            serialize(admin));

                    writer.newLine();
                }
            }
        }
    }

    // Convert line -> Admin
    private Admin parseLine(String line) {

        String[] parts = line.split("\\|");

        if (parts.length < 5) {
            throw new RuntimeException("Corrupted admin data file");
        }

        return new Admin(
                parts[0],
                parts[1],
                parts[2],
                parts[3],
                parts[4]
        );
    }

    // Convert Admin -> line
    private String serialize(Admin admin) {

        return String.join("|",
                admin.getId(),
                admin.getName(),
                admin.getPassword(),
                admin.getEmail(),
                admin.getPhone()
        );
    }

    // Create file if missing
    private void ensureFileExists()
            throws IOException {

        File file = new File(FILE_PATH);

        if (!file.exists()) {

            file.getParentFile().mkdirs();
            file.createNewFile();
        }
    }
}