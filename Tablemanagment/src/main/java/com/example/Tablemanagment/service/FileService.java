package com.example.Tablemanagment.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.file.*;
import java.util.List;

@Service
public class FileService {

    @Value("${file.path}")
    private String filePathString;

    private Path getFilePath() {
        return Paths.get(filePathString);
    }

    public List<String> readAll() throws IOException {
        Path filePath = getFilePath();
        if (!Files.exists(filePath)) {
            Files.createFile(filePath);
        }
        return Files.readAllLines(filePath);
    }

    public void create(String content) throws IOException {
        Path filePath = getFilePath();
        Files.write(filePath,
                (content + System.lineSeparator()).getBytes(),
                StandardOpenOption.CREATE,
                StandardOpenOption.APPEND);
    }

    public void update(int lineNumber, String newContent) throws IOException {
        List<String> lines = readAll();
        if (lineNumber < 0 || lineNumber >= lines.size()) {
            throw new IllegalArgumentException("Invalid line number");
        }
        lines.set(lineNumber, newContent);
        Files.write(getFilePath(), lines);
    }

    public void delete(int lineNumber) throws IOException {
        List<String> lines = readAll();
        if (lineNumber < 0 || lineNumber >= lines.size()) {
            throw new IllegalArgumentException("Invalid line number");
        }
        lines.remove(lineNumber);
        Files.write(getFilePath(), lines);
    }
}