package com.restaurant.staff.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import com.restaurant.staff.model.Staff;
import com.restaurant.staff.service.StaffService;

@RestController
@RequestMapping("/api/staff")
public class StaffController {

    @Autowired
    private StaffService staffService;

    @PostMapping("/add")
    public String addStaff(@RequestBody Staff staff) {
        staffService.saveStaff(staff);
        return "Staff member added successfully to staff.txt via Spring Boot!";
    }
}