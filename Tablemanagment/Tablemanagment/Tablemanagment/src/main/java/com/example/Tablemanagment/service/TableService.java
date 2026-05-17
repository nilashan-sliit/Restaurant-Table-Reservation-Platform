package com.example.Tablemanagment.service;

import com.example.Tablemanagment.model.StandardTable;
import com.example.Tablemanagment.model.Table;
import com.example.Tablemanagment.model.VIPTable;
import com.example.Tablemanagment.repository.TableFileRepository;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Service
public class TableService {

    private final TableFileRepository repo;

    private Long currentId = 1L;

    public TableService(TableFileRepository repo) {
        this.repo = repo;
    }

    // CREATE RESERVATION
    public void addTable(Table table) throws IOException {

        table.setId(currentId++);

        Table newTable;

        if (table.getTableType().equalsIgnoreCase("VIP")) {

            newTable = new VIPTable(
                    table.getId(),
                    table.getCapacity(),
                    table.getLocation(),
                    table.getReservationDate()
            );

        } else {

            newTable = new StandardTable(
                    table.getId(),
                    table.getCapacity(),
                    table.getLocation(),
                    table.getReservationDate()
            );
        }

        String line =
                newTable.getId() + "," +
                        newTable.getTableType() + "," +
                        newTable.getCapacity() + "," +
                        newTable.getLocation() + "," +
                        newTable.getReservationDate();

        repo.save(line);
    }


    // READ ALL RESERVATIONS
    public List<Table> getAllTables() throws IOException {

        List<String> lines = repo.readAll();

        List<Table> tables = new ArrayList<>();

        for (String line : lines) {

            String[] p = line.split(",");

            Table table = new Table();

            table.setId(Long.parseLong(p[0]));
            table.setTableType(p[1]);
            table.setCapacity(Integer.parseInt(p[2]));
            table.setLocation(p[3]);
            table.setReservationDate(p[4]);

            tables.add(table);
        }

        return tables;
    }


    // DELETE RESERVATION
    public void deleteTable(Long id) throws IOException {

        List<String> lines = repo.readAll();

        List<String> newLines = new ArrayList<>();

        for (String line : lines) {

            String[] parts = line.split(",");

            if (!parts[0].equals(String.valueOf(id))) {

                newLines.add(line);
            }
        }

        repo.writeAll(newLines);
    }
}