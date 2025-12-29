package com.formulario.avaliacao.Controller;

import com.formulario.avaliacao.Dtos.PaginaDto;
import com.formulario.avaliacao.Model.Pagina;
import com.formulario.avaliacao.Service.PaginaService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/paginas")
public class PaginaController {

    @Autowired
    private PaginaService paginaService;

    @GetMapping
    public ResponseEntity<List<Pagina>> listarTodos() {
        return ResponseEntity.ok(paginaService.listarTodos());
    }

    @PostMapping(consumes = "multipart/form-data")
    public ResponseEntity<Pagina> salvar(
            @Valid @RequestPart("dados") PaginaDto paginaDto,
            @RequestPart("imagem") MultipartFile imagem
    ) throws IOException {

        Pagina paginaSalva = paginaService.salvar(paginaDto, imagem);
        return ResponseEntity.status(HttpStatus.CREATED).body(paginaSalva);
    }

    @DeleteMapping("/{email}")
    public ResponseEntity<Void> deletar(@PathVariable String email) throws IOException {
        paginaService.deletar(email);
        return ResponseEntity.noContent().build();
    }
}
