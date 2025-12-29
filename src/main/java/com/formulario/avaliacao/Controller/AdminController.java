package com.formulario.avaliacao.Controller;

import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.InputStream;
import java.nio.charset.StandardCharsets;

@RestController
public class AdminController {

    @GetMapping(value = "/admin/verificar", produces = MediaType.TEXT_HTML_VALUE)
    public ResponseEntity<String> verificar() {
        try {
            Resource resource = new ClassPathResource("static/admin/verificar.html");
            InputStream inputStream = resource.getInputStream();
            byte[] bytes = inputStream.readAllBytes();
            String html = new String(bytes, StandardCharsets.UTF_8);
            
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.TEXT_HTML);
            
            return ResponseEntity.ok().headers(headers).body(html);
        } catch (Exception e) {
            return ResponseEntity.internalServerError()
                    .body("<html><body><h1>Erro ao carregar página</h1></body></html>");
        }
    }
}
