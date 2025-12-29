package com.formulario.avaliacao.Dtos;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public record ConcursoDto(

        @NotBlank(message = "Nome do concurso é obrigatório")
        String nome,

        @NotNull(message = "Ano do concurso é obrigatório")
        @Positive(message = "Ano do concurso deve ser válido")
        Integer ano

) {}
