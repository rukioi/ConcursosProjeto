package com.formulario.avaliacao.Service;

import com.formulario.avaliacao.Exception.RegrasNegocioException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

@Service
public class UploadImagensService {

    @Value("${upload.path:uploads/imagens}")
    private String uploadPath;

    private static final List<String> ALLOWED_EXTENSIONS =
            Arrays.asList("jpg", "jpeg", "png");

    private static final long MAX_FILE_SIZE = 5 * 1024 * 1024; // 5MB

    public String salvarImagem(MultipartFile arquivo) throws IOException {

        if (arquivo == null || arquivo.isEmpty()) {
            throw new RegrasNegocioException("Imagem é obrigatória");
        }

        if (arquivo.getSize() > MAX_FILE_SIZE) {
            throw new RegrasNegocioException("Imagem maior que 5MB");
        }

        String nomeOriginal = arquivo.getOriginalFilename();
        if (nomeOriginal == null || !validarExtensao(nomeOriginal)) {
            throw new RegrasNegocioException("Apenas imagens JPG ou PNG são permitidas");
        }

        Path diretorio = Paths.get(uploadPath);
        if (!diretorio.isAbsolute()) {
            diretorio = diretorio.toAbsolutePath();
        }
        if (!Files.exists(diretorio)) {
            Files.createDirectories(diretorio);
        }

        String extensao = obterExtensao(nomeOriginal);
        String nomeArquivo = UUID.randomUUID() + "." + extensao;

        Path caminhoCompleto = diretorio.resolve(nomeArquivo);

        Files.copy(
                arquivo.getInputStream(),
                caminhoCompleto,
                StandardCopyOption.REPLACE_EXISTING
        );

        return nomeArquivo;
    }

    public void deletarImagem(String nomeArquivo) throws IOException {

        if (nomeArquivo == null || nomeArquivo.isBlank()) {
            return;
        }

        Path diretorio = Paths.get(uploadPath);
        if (!diretorio.isAbsolute()) {
            diretorio = diretorio.toAbsolutePath();
        }
        Path caminhoCompleto = diretorio.resolve(nomeArquivo);
        Files.deleteIfExists(caminhoCompleto);
    }

    private boolean validarExtensao(String nomeArquivo) {
        return ALLOWED_EXTENSIONS.contains(
                obterExtensao(nomeArquivo).toLowerCase()
        );
    }

    private String obterExtensao(String nomeArquivo) {
        int ponto = nomeArquivo.lastIndexOf('.');
        return (ponto == -1) ? "" : nomeArquivo.substring(ponto + 1);
    }
}
