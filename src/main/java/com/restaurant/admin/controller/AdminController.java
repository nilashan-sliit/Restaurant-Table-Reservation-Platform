package com.restaurant.admin.controller;

import com.restaurant.admin.model.Admin;
import com.restaurant.admin.service.AdminService;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@Controller
public class AdminController {

    private final AdminService service;

    public AdminController(AdminService service) {
        this.service = service;
    }

    // ================= LOGIN PAGE =================
    @GetMapping("/login")
    public String loginPage() {
        return "login";   // your existing template (NO UI CHANGE)
    }

    // ================= LOGIN ACTION =================
    @PostMapping("/login")
    public String login(@RequestParam String username,
                        @RequestParam String password,
                        HttpSession session,
                        Model model) {

        // HARD CODED ADMIN LOGIN
        if ("admin".equals(username) && "admin".equals(password)) {

            Admin admin = new Admin(
                    "A0",
                    "admin",
                    "admin",
                    "admin@system.com",
                    "0000000000"
            );

            session.setAttribute("loggedAdmin", admin);

            return "redirect:/admin/dashboard";
        }

        model.addAttribute("error", "Invalid username or password");
        return "login";
    }

    // ================= DASHBOARD =================
    @GetMapping("/admin/dashboard")
    public String dashboard(HttpSession session, Model model) throws IOException {

        Admin admin = (Admin) session.getAttribute("loggedAdmin");

        if (admin == null) {
            return "redirect:/login";
        }

        model.addAttribute("admin", admin);
        model.addAttribute("admins", service.getAllAdmins());

        return "admin-dashboard";
    }

    // ================= PROFILE =================
    @GetMapping("/admin/profile")
    public String profile(HttpSession session, Model model) {

        Admin admin = (Admin) session.getAttribute("loggedAdmin");

        if (admin == null) {
            return "redirect:/login";
        }

        model.addAttribute("admin", admin);
        return "profile";
    }

    // ================= DELETE =================
    @PostMapping("/admin/delete/{id}")
    public String delete(@PathVariable String id) throws IOException {
        service.deleteAdmin(id);
        return "redirect:/admin/dashboard";
    }

    // ================= LOGOUT =================
    @GetMapping("/admin/logout")
    public String logout(HttpSession session) {
        session.invalidate();
        return "redirect:/login";
    }
}