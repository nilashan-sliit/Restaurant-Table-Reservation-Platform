package com.restaurant.admin.controller;

import com.restaurant.admin.model.Admin;
import com.restaurant.admin.service.AdminService;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.Collections;
import java.util.List;

@Controller
public class AdminController {

    private final AdminService service;

    public AdminController(AdminService service) {
        this.service = service;
    }

    @GetMapping("/login")
    public String loginPage() {
        return "login";
    }

    @PostMapping("/login")
    public String login(@RequestParam String username,
                        @RequestParam String password,
                        HttpSession session,
                        Model model) {

        // hardcoded superadmin
        if ("admin".equals(username) && "admin".equals(password)) {
            session.setAttribute("loggedAdmin", new Admin("A0", "admin", "admin", "admin@system.com", "0000000000"));
            return "redirect:/admin/dashboard";
        }

        // check file-based admins (name or email)
        try {
            for (Admin a : service.getAllAdmins()) {
                if ((a.getName().equals(username) || a.getEmail().equals(username))
                        && a.getPassword().equals(password)) {
                    session.setAttribute("loggedAdmin", a);
                    return "redirect:/admin/dashboard";
                }
            }
        } catch (IOException ignored) {}

        model.addAttribute("error", "Invalid username or password");
        return "login";
    }

    @GetMapping("/admin/dashboard")
    public String dashboard(HttpSession session, Model model) {
        Admin admin = (Admin) session.getAttribute("loggedAdmin");
        if (admin == null) return "redirect:/login";

        List<Admin> admins = Collections.emptyList();
        try { admins = service.getAllAdmins(); } catch (IOException ignored) {}

        model.addAttribute("admin", admin);
        model.addAttribute("admins", admins);
        return "admin-dashboard";
    }

    @PostMapping("/admin/add")
    public String addAdmin(@RequestParam String name,
                           @RequestParam String email,
                           @RequestParam String phone,
                           @RequestParam String password,
                           HttpSession session,
                           Model model) {

        Admin loggedAdmin = (Admin) session.getAttribute("loggedAdmin");
        if (loggedAdmin == null) return "redirect:/login";

        try {
            service.register(name, password, email, phone);
        } catch (Exception e) {
            List<Admin> admins = Collections.emptyList();
            try { admins = service.getAllAdmins(); } catch (IOException ignored) {}
            model.addAttribute("admin", loggedAdmin);
            model.addAttribute("admins", admins);
            model.addAttribute("error", e.getMessage());
            return "admin-dashboard";
        }
        return "redirect:/admin/dashboard";
    }

    @PostMapping("/admin/update")
    public String updateAdmin(@RequestParam String id,
                              @RequestParam String email,
                              @RequestParam String phone,
                              @RequestParam(required = false) String password,
                              HttpSession session,
                              Model model) {

        Admin loggedAdmin = (Admin) session.getAttribute("loggedAdmin");
        if (loggedAdmin == null) return "redirect:/login";

        try {
            Admin existing = service.getAdminById(id)
                    .orElseThrow(() -> new IllegalArgumentException("Admin not found."));
            String newPassword = (password == null || password.isBlank())
                    ? existing.getPassword() : password;
            service.updateAdmin(id, email, phone, newPassword);
        } catch (Exception e) {
            List<Admin> admins = Collections.emptyList();
            try { admins = service.getAllAdmins(); } catch (IOException ignored) {}
            model.addAttribute("admin", loggedAdmin);
            model.addAttribute("admins", admins);
            model.addAttribute("error", e.getMessage());
            return "admin-dashboard";
        }
        return "redirect:/admin/dashboard";
    }

    @PostMapping("/admin/delete/{id}")
    public String delete(@PathVariable String id, HttpSession session) {
        if (session.getAttribute("loggedAdmin") == null) return "redirect:/login";
        try { service.deleteAdmin(id); } catch (IOException ignored) {}
        return "redirect:/admin/dashboard";
    }

    @GetMapping("/admin/logout")
    public String logout(HttpSession session) {
        session.invalidate();
        return "redirect:/login";
    }
}
