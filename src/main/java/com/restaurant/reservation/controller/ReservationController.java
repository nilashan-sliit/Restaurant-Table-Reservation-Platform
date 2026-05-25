package com.restaurant.reservation.controller;

import com.restaurant.reservation.service.ReservationService;
import com.restaurant.user.model.User;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@Controller
@RequestMapping("/reservations")
public class ReservationController {

    private final ReservationService service;

    public ReservationController(ReservationService service) {
        this.service = service;
    }

    // ─── RESERVE ────────────────────────────────────────────

    @GetMapping("/reserve")
    public String showReserveForm(HttpSession session, Model model) {
        User user = (User) session.getAttribute("loggedInUser");
        if (user == null) return "redirect:/users/login";

        model.addAttribute("user", user);
        model.addAttribute("tables", ReservationService.AVAILABLE_TABLES);
        model.addAttribute("timeSlots", ReservationService.TIME_SLOTS);
        return "reserve";
    }

    @PostMapping("/reserve")
    public String reserve(@RequestParam String tableId,
                          @RequestParam String date,
                          @RequestParam String timeSlot,
                          HttpSession session, Model model) {
        User user = (User) session.getAttribute("loggedInUser");
        if (user == null) return "redirect:/users/login";

        try {
            service.reserve(user, tableId, date, timeSlot);
            return "redirect:/reservations/my";
        } catch (Exception e) {
            model.addAttribute("error", e.getMessage());
            model.addAttribute("user", user);
            model.addAttribute("tables", ReservationService.AVAILABLE_TABLES);
            model.addAttribute("timeSlots", ReservationService.TIME_SLOTS);
            return "reserve";
        }
    }

    // ─── MY RESERVATIONS ────────────────────────────────────

    @GetMapping("/my")
    public String myReservations(HttpSession session, Model model) throws IOException {
        User user = (User) session.getAttribute("loggedInUser");
        if (user == null) return "redirect:/users/login";

        model.addAttribute("user", user);
        model.addAttribute("reservations", service.getByUserId(user.getUserId()));
        return "my-reservations";
    }

    // ─── CANCEL ─────────────────────────────────────────────

    @GetMapping("/cancel/{id}")
    public String showCancelConfirm(@PathVariable String id,
                                    HttpSession session, Model model) throws IOException {
        User user = (User) session.getAttribute("loggedInUser");
        if (user == null) return "redirect:/users/login";

        var reservation = service.getById(id)
                .orElseThrow(() -> new RuntimeException("Reservation not found."));

        model.addAttribute("reservation", reservation);
        model.addAttribute("user", user);
        return "cancel";
    }

    @PostMapping("/cancel/{id}")
    public String cancelReservation(@PathVariable String id,
                                    HttpSession session) throws IOException {
        User user = (User) session.getAttribute("loggedInUser");
        if (user == null) return "redirect:/users/login";

        service.cancel(id);
        return "redirect:/reservations/my";
    }

    // ─── ADMIN — ALL RESERVATIONS ───────────────────────────

    @GetMapping("/list")
    public String listAll(Model model) throws IOException {
        var reservations = service.getAllReservations();
        long activeCount = reservations.stream()
                .filter(r -> "Active".equals(r.getStatus())).count();
        long cancelledCount = reservations.size() - activeCount;

        model.addAttribute("reservations", reservations);
        model.addAttribute("activeCount", activeCount);
        model.addAttribute("cancelledCount", cancelledCount);
        return "reservations-list";
    }

    // ─── DELETE (admin only) ─────────────────────────────────

    @PostMapping("/delete/{id}")
    public String deleteReservation(@PathVariable String id) throws IOException {
        service.delete(id);
        return "redirect:/reservations/list";
    }
}