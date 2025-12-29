CREATE TABLE IF NOT EXISTS aprovados (
    email VARCHAR(255) NOT NULL,
    nome VARCHAR(150) NOT NULL,
    telefone VARCHAR(20) NOT NULL,
    imagem_path VARCHAR(255),

    CONSTRAINT pk_aprovados PRIMARY KEY (email)
);

CREATE TABLE IF NOT EXISTS concursos (
    id BIGSERIAL PRIMARY KEY,
    aprovado_email VARCHAR(255) NOT NULL,
    nome VARCHAR(150) NOT NULL,
    ano INTEGER NOT NULL,

    CONSTRAINT fk_concurso_aprovado
        FOREIGN KEY (aprovado_email)
        REFERENCES aprovados (email)
        ON DELETE CASCADE
);
