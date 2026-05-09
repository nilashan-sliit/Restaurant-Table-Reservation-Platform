package com.example.Tablemanagment.controller;

import com.example.Tablemanagment.service.FileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/file")
public class FileController {

    @Autowired
    private FileService fileService;

    @GetMapping
    public List<String> getAll() throws IOException {
        return fileService.readAll();
    }

    @PostMapping
    public void create(@RequestBody String content) throws IOException {
        fileService.create(content);
    }

    @PutMapping("/{lineNumber}")
    public void update(@PathVariable int lineNumber, @RequestBody String content) throws IOException {
        fileService.update(lineNumber, content);
    }

    @DeleteMapping("/{lineNumber}")
    public void delete(@PathVariable int lineNumber) throws IOException {
        fileService.delete(lineNumber);
    }
}