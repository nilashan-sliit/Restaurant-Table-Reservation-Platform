package com.restaurant.admin.service;

import com.restaurant.admin.model.Admin;
import com.restaurant.admin.repository.AdminRepository;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

@Service
public class AdminService {

    private final AdminRepository repo;

    public AdminService(AdminRepository repo) {
        this.repo = repo;
    }

    // Register admin
    public void register(String name,
                         String password,
                         String email,
                         String phone)
            throws IOException {

        if (repo.findByUsername(name).isPresent()) {

            throw new IllegalArgumentException(
                    "Username already exists."
            );
        }

        String id = "A" + System.currentTimeMillis();

        Admin admin = new Admin(
                id,
                name,
                password,
                email,
                phone
        );

        repo.save(admin);
    }

    // Login
    public Optional<Admin> login(String name,
                                 String password)
            throws IOException {

        return repo.findByUsername(name)
                .filter(a ->
                        a.getPassword()
                                .equals(password));
    }

    // Find all
    public List<Admin> getAllAdmins()
            throws IOException {

        return repo.findAll();
    }

    // Find by ID
    public Optional<Admin> getAdminById(String id)
            throws IOException {

        return repo.findById(id);
    }

    // Update
    public void updateAdmin(String id,
                            String email,
                            String phone,
                            String password)
            throws IOException {

        Admin admin = repo.findById(id)
                .orElseThrow(() ->
                        new IllegalArgumentException(
                                "Admin not found."
                        ));

        admin.setEmail(email);
        admin.setPhone(phone);
        admin.setPassword(password);

        repo.update(admin);
    }


    // Delete
    public void deleteAdmin(String id)
            throws IOException {

        repo.delete(id);
    }
}