const form = document.getElementById("formAprovado");
const concursosDiv = document.getElementById("concursos");
const mensagem = document.getElementById("mensagem");

document.getElementById("addConcurso").addEventListener("click", () => {
    const div = document.createElement("div");
    div.className = "concurso";

    div.innerHTML = `
        <input type="text" placeholder="Nome do concurso" required>
        <input type="number" placeholder="Ano" required>
        <button type="button" class="btn-remover">×</button>
    `;

    div.querySelector(".btn-remover").addEventListener("click", () => {
        div.remove();
    });

    concursosDiv.appendChild(div);
});

form.addEventListener("submit", async (e) => {
    e.preventDefault();

    const submitButton = form.querySelector('button[type="submit"]');
    const originalButtonText = submitButton.innerText;
    
    submitButton.disabled = true;
    submitButton.innerText = "Salvando...";
    mensagem.innerText = "";
    mensagem.style.display = "none";

    const concursos = [];
    document.querySelectorAll(".concurso").forEach(div => {
        const inputs = div.querySelectorAll("input");
        concursos.push({
            nome: inputs[0].value,
            ano: Number(inputs[1].value)
        });
    });

    const dados = {
        nome: document.getElementById("nome").value,
        email: document.getElementById("email").value,
        telefone: document.getElementById("telefone").value,
        concursos: concursos
    };

    const formData = new FormData();
    formData.append("dados", new Blob(
        [JSON.stringify(dados)],
        { type: "application/json" }
    ));
    formData.append("imagem", document.getElementById("imagem").files[0]);

    try {
        const response = await fetch("/paginas", {
            method: "POST",
            body: formData
        });

        if (!response.ok) {
            const erro = await response.json();
            throw new Error(erro.message || "Erro ao salvar");
        }

        mensagem.style.display = "block";
        mensagem.style.color = "green";
        mensagem.innerText = "Cadastro realizado com sucesso!";
        form.reset();
        concursosDiv.innerHTML = "";

    } catch (err) {
        mensagem.style.display = "block";
        mensagem.style.color = "red";
        mensagem.innerText = err.message;
    } finally {
        submitButton.disabled = false;
        submitButton.innerText = originalButtonText;
    }
});
