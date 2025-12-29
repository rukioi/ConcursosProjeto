const cadastrosDiv = document.getElementById("cadastros");
const loadingDiv = document.getElementById("loading");

async function carregarCadastros() {
    try {
        let apiUrl = '/paginas';
        
        if (window.location.port === '63342' || window.location.port === '') {
            apiUrl = 'http://localhost:8081/paginas';
        }
        
        const response = await fetch(apiUrl, {
            method: 'GET',
            headers: {
                'Accept': 'application/json'
            }
        });
        
        if (!response.ok) {
            const errorText = await response.text();
            throw new Error(`Erro ${response.status}: ${errorText.substring(0, 200)}`);
        }

        const contentType = response.headers.get("content-type");
        if (contentType && !contentType.includes("application/json")) {
            const text = await response.text();
            throw new Error(`Resposta não é JSON. Content-Type: ${contentType}. Recebido: ${text.substring(0, 200)}`);
        }

        const cadastros = await response.json();
        loadingDiv.style.display = "none";

        if (cadastros.length === 0) {
            cadastrosDiv.innerHTML = '<div class="empty-state">Nenhum cadastro encontrado.</div>';
            return;
        }

        cadastrosDiv.innerHTML = cadastros.map(cadastro => {
            const imagemUrl = cadastro.imagemPath 
                ? `/uploads/${cadastro.imagemPath}` 
                : '';

            const concursosHtml = cadastro.concursos && cadastro.concursos.length > 0
                ? `
                    <div class="concursos-list">
                        <h4>Concursos Aprovados:</h4>
                        ${cadastro.concursos.map(concurso => 
                            `<span class="concurso-item">${concurso.nome} (${concurso.ano})</span>`
                        ).join('')}
                    </div>
                `
                : '<p><strong>Concursos:</strong> Nenhum</p>';

            return `
                <div class="cadastro-item">
                    <div class="cadastro-imagem">
                        ${imagemUrl ? `<img src="${imagemUrl}" alt="Foto">` : '<div style="display:flex;align-items:center;justify-content:center;height:100%;color:#999;">Sem imagem</div>'}
                    </div>
                    <div class="cadastro-info">
                        <h3>${cadastro.nome}</h3>
                        <p><strong>Email:</strong> ${cadastro.email}</p>
                        <p><strong>Telefone:</strong> ${cadastro.telefone}</p>
                        ${concursosHtml}
                    </div>
                </div>
            `;
        }).join('');
    } catch (err) {
        loadingDiv.style.display = "none";
        cadastrosDiv.innerHTML = `<div class="empty-state" style="color: #e74c3c;">Erro ao carregar cadastros: ${err.message}</div>`;
    }
}

carregarCadastros();

