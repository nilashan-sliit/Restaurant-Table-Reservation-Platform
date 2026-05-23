package com.restaurant.staff.service;

import org.springframework.stereotype.Service;
import com.restaurant.staff.model.Staff;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

@Service
public class StaffService {
    private final String filePath = "staff.txt";

    public void saveStaff(Staff staff) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath, true))) {
            writer.write(staff.getId() + "|" + staff.getName() + "|" + staff.getRole() + "|" + staff.getContact());
            writer.newLine();
        } catch (IOException e) {
            System.out.println("Error writing to file: " + e.getMessage());
        }
    }
}