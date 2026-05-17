package com.example.Tablemanagment.repository;

import org.springframework.stereotype.Repository;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.List;

@Repository
public class TableFileRepository {

    private static final String FILE_NAME = "data.txt";

    private final Path path = Paths.get(FILE_NAME);

    public void save(String line) throws IOException {

        Files.write(
                path,
                (line + System.lineSeparator()).getBytes(),
                StandardOpenOption.CREATE,
                StandardOpenOption.APPEND
        );
    }

    public List<String> readAll() throws IOException {

        if (!Files.exists(path)) {
            Files.createFile(path);
        }

        return Files.readAllLines(path);
    }

    public void writeAll(List<String> lines) throws IOException {

        Files.write(path, lines);
    }
}