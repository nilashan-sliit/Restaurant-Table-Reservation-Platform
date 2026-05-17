package com.example.Tablemanagment.controller;

import com.example.Tablemanagment.model.Table;
import com.example.Tablemanagment.service.TableService;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/tables")
@CrossOrigin(origins = "*")
public class TableController {

    private final TableService service;

    public TableController(TableService service) {
        this.service = service;
    }

    // CREATE RESERVATION
    @PostMapping("/add")
    public String reserveTable(@RequestBody Table table)
            throws IOException {

        service.addTable(table);

        return "Reservation added successfully";
    }

    // READ ALL RESERVATIONS
    @GetMapping
    public List<Table> getAllTables()
            throws IOException {

        return service.getAllTables();
    }

    // DELETE RESERVATION
    @DeleteMapping("/delete/{id}")
    public String deleteTable(@PathVariable Long id)
            throws IOException {

        service.deleteTable(id);

        return "Reservation cancelled successfully";
    }
}