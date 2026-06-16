package com.library.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

@RestController
@RequestMapping("/api/files")
@RequiredArgsConstructor
public class FileUploadController {

    @PostMapping("/upload-book-image")
    public String uploadBookImage(
            @RequestParam("file")
            MultipartFile file)
            throws IOException {

        String fileName =
                System.currentTimeMillis()
                        + "_"
                        + file.getOriginalFilename();

        Path uploadPath =
                Paths.get(
                        "uploads/books");

        Files.createDirectories(
                uploadPath);

        Files.copy(
                file.getInputStream(),
                uploadPath.resolve(fileName),
                StandardCopyOption.REPLACE_EXISTING);

        return fileName;
    }
}