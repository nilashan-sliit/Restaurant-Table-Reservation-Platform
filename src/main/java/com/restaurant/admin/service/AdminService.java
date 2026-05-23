package com.restaurant.admin.service;

import com.restaurant.admin.model.Admin;
import com.restaurant.admin.repository.AdminRepository;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

@Service
public class AdminService extends AdminRepository {


    public void register(String name,
                         String password,
                         String email,
                         String phone)
            throws IOException {

        // Check if username exists
        if (findByUsername(name).isPresent()) {

            throw new IllegalArgumentException(
                    "Username already exists."
            );
        }

        // Generate ID
        String id = "A" + System.currentTimeMillis();

        Admin admin = new Admin(
                id,
                name,
                password,
                email,
                phone
        );

        save(admin);
    }

    // Login
    public Optional<Admin> login(String name,
                                 String password)
            throws IOException {

        return findByUsername(name)
                .filter(a ->
                        a.getPassword()
                                .equals(password));
    }

    // Find all admins
    public List<Admin> getAllAdmins()
            throws IOException {

        return findAll();
    }

    // Find by ID
    public Optional<Admin> getAdminById(String id)
            throws IOException {

        return findById(id);
    }

    // Update admin
    public void updateAdmin(String id,
                            String email,
                            String phone,
                            String password)
            throws IOException {

        Admin admin = findById(id)
                .orElseThrow(() ->
                        new IllegalArgumentException(
                                "Admin not found."
                        ));

        admin.setEmail(email);
        admin.setPhone(phone);
        admin.setPassword(password);

        update(admin);
    }

    // Delete admin
    public void deleteAdmin(String id)
            throws IOException {

        delete(id);
    }
}