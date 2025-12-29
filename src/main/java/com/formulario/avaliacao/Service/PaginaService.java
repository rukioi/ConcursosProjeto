package com.formulario.avaliacao.Service;

import com.formulario.avaliacao.Dtos.ConcursoDto;
import com.formulario.avaliacao.Dtos.PaginaDto;
import com.formulario.avaliacao.Exception.RegrasNegocioException;
import com.formulario.avaliacao.Exception.ResourceNotFoundException;
import com.formulario.avaliacao.Model.Concurso;
import com.formulario.avaliacao.Model.Pagina;
import com.formulario.avaliacao.Repository.PaginaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class PaginaService {

    @Autowired
    private PaginaRepository paginaRepository;

    @Autowired
    private UploadImagensService imagemService;

    @Transactional
    public Pagina salvar(PaginaDto paginaDto, MultipartFile imagem) throws IOException {

        if (paginaRepository.existsById(paginaDto.email())) {
            throw new RegrasNegocioException("Email já cadastrado");
        }

        String imagemPath = imagemService.salvarImagem(imagem);

        Pagina pagina = new Pagina();
        pagina.setEmail(paginaDto.email());
        pagina.setNome(paginaDto.nome());
        pagina.setTelefone(paginaDto.telefone());
        pagina.setImagemPath(imagemPath);

        List<Concurso> concursos = paginaDto.concursos()
                .stream()
                .map(dto -> new Concurso(
                        dto.nome(),
                        dto.ano(),
                        pagina
                ))
                .collect(Collectors.toList());

        pagina.setConcursos(concursos);

        return paginaRepository.save(pagina);
    }

    public List<Pagina> listarTodos() {
        return paginaRepository.findAll();
    }

    @Transactional
    public void deletar(String email) throws IOException {

        Pagina pagina = paginaRepository.findById(email)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Aprovado não encontrado")
                );

        if (pagina.getImagemPath() != null) {
            imagemService.deletarImagem(pagina.getImagemPath());
        }

        paginaRepository.delete(pagina);
    }
}
