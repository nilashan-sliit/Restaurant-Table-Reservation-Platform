package com.restaurant.staff;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class StaffService {
    private final String filePath = "staff.txt";

    // Method to save a staff member to the file
    public void saveStaff(Staff staff) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath, true))) {
            writer.write(staff.getId() + "|" + staff.getName() + "|" + staff.getRole() + "|" + staff.getContact());
            writer.newLine();
        } catch (IOException e) {
            System.out.println("Error writing to file: " + e.getMessage());
        }
    }
}