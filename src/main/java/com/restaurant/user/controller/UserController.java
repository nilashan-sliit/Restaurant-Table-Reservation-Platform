package com.restaurant.user.controller;

import com.restaurant.user.model.User;
import com.restaurant.user.service.UserService;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@Controller
@RequestMapping("/users")
public class UserController {

    private final UserService service;

    public UserController(UserService service) {
        this.service = service;
    }

    // ─── REGISTER ───────────────────────────────────────────

    @GetMapping("/register")
    public String showRegister() {
        return "register";
    }

    @PostMapping("/register")
    public String register(@RequestParam String username,
                           @RequestParam String password,
                           @RequestParam String email,
                           @RequestParam String phone,
                           @RequestParam String membershipType,
                           Model model) {
        try {
            service.register(username, password, email, phone, membershipType);
            return "redirect:/users/login";
        } catch (Exception e) {
            model.addAttribute("error", e.getMessage());
            return "register";
        }
    }

    // ─── LOGIN ──────────────────────────────────────────────

    @GetMapping("/login")
    public String showLogin() {
        return "login";
    }

    @PostMapping("/login")
    public String login(@RequestParam String username,
                        @RequestParam String password,
                        HttpSession session,
                        Model model) {
        try {
            User user = service.login(username, password)
                    .orElseThrow(() -> new RuntimeException("Invalid username or password."));
            session.setAttribute("loggedInUser", user);
            return "redirect:/users/profile";
        } catch (Exception e) {
            model.addAttribute("error", e.getMessage());
            return "login";
        }
    }

    // ─── PROFILE ────────────────────────────────────────────

    @GetMapping("/profile")
    public String showProfile(HttpSession session, Model model) {
        User user = (User) session.getAttribute("loggedInUser");
        if (user == null) return "redirect:/users/login";
        model.addAttribute("user", user);
        return "profile";
    }

    @PostMapping("/update")
    public String updateUser(@RequestParam String userId,
                             @RequestParam String email,
                             @RequestParam String phone,
                             @RequestParam(required = false) String password,
                             HttpSession session,
                             Model model) {
        try {
            User current = (User) session.getAttribute("loggedInUser");

            // If password field left blank, keep existing password
            String newPassword = (password == null || password.isBlank())
                    ? current.getPassword()
                    : password;

            service.updateUser(userId, email, phone, newPassword);

            // Refresh session with updated data
            User updated = service.getUserById(userId).orElseThrow();
            session.setAttribute("loggedInUser", updated);
            model.addAttribute("user", updated);
            model.addAttribute("updateSuccess", true);
            return "profile";
        } catch (Exception e) {
            model.addAttribute("error", e.getMessage());
            return "profile";
        }
    }

    // ─── ADMIN USER LIST ────────────────────────────────────

    @GetMapping("/list")
    public String listUsers(Model model) throws IOException {
        var users = service.getAllUsers();
        long vipCount = users.stream()
                .filter(u -> u.getMembershipType().equals("VIP")).count();
        long regularCount = users.size() - vipCount;
        model.addAttribute("users", users);
        model.addAttribute("vipCount", vipCount);
        model.addAttribute("regularCount", regularCount);
        return "user-list";
    }

    // ─── DELETE ─────────────────────────────────────────────

    @PostMapping("/delete/{userId}")
    public String deleteUser(@PathVariable String userId) throws IOException {
        service.deleteUser(userId);
        return "redirect:/users/list";
    }

    // ─── LOGOUT ─────────────────────────────────────────────

    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate();
        return "redirect:/users/login";
    }
}