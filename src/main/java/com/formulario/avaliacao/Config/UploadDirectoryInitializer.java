package com.formulario.avaliacao.Config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Component
public class UploadDirectoryInitializer implements CommandLineRunner {

    @Value("${upload.path:uploads/imagens}")
    private String uploadPath;

    @Override
    public void run(String... args) throws Exception {
        Path uploadDir = Paths.get(uploadPath);
        if (!uploadDir.isAbsolute()) {
            uploadDir = uploadDir.toAbsolutePath();
        }
        
        if (!Files.exists(uploadDir)) {
            Files.createDirectories(uploadDir);
            System.out.println("Diretório de upload criado: " + uploadDir);
        } else {
            System.out.println("Diretório de upload já existe: " + uploadDir);
        }
    }
}

