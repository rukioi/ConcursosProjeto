# Deploy no Render - Instruções

## Configurações no Render

### 1. Web Service Settings

- **Name:** `ConcursosProjeto`
- **Environment:** `Production`
- **Language:** `Docker`
- **Branch:** `master`
- **Root Directory:** (deixe vazio)
- **Dockerfile Path:** `Dockerfile` (padrão)

### 2. Environment Variables

Adicione estas variáveis de ambiente no Render:

```
DATABASE_URL=jdbc:postgresql://aws-1-sa-east-1.pooler.supabase.com:6543/postgres?sslmode=require
DB_USERNAME=postgres.zzmrgzlzcjfkhsksxfyr
DB_PASSWORD=Concurs@123@
SPRING_PROFILES_ACTIVE=prod
PORT=8081
```

### 3. Build & Deploy

O Render irá:
1. Clonar o repositório
2. Construir a imagem Docker usando o Dockerfile
3. Executar o container com as variáveis de ambiente

## Arquivos Criados

- ✅ `Dockerfile` - Configuração Docker para Spring Boot
- ✅ `.dockerignore` - Arquivos ignorados no build
- ✅ `application-prod.properties` - Configurações de produção
- ✅ `AvaliacaoApplication.java` - Ajustado para usar PORT do Render

## Observações Importantes

1. **Upload de Imagens:** As imagens são salvas em `/tmp/uploads/imagens` (diretório temporário)
   - No Render, arquivos em `/tmp` são perdidos quando o container reinicia
   - Para produção real, considere usar storage na nuvem (S3, Cloudinary, etc.)

2. **Banco de Dados:** Usando Supabase externo (não precisa criar banco no Render)

3. **Porta:** O Render define automaticamente a porta via variável `PORT`

4. **Flyway:** Habilitado em produção para criar/atualizar tabelas automaticamente

## Testando Localmente

Para testar o Docker localmente:

```bash
# Build da imagem
docker build -t avaliacao-app .

# Executar container
docker run -p 8081:8081 \
  -e DATABASE_URL="jdbc:postgresql://aws-1-sa-east-1.pooler.supabase.com:6543/postgres?sslmode=require" \
  -e DB_USERNAME="postgres.zzmrgzlzcjfkhsksxfyr" \
  -e DB_PASSWORD="Concurs@123@" \
  -e SPRING_PROFILES_ACTIVE=prod \
  -e PORT=8081 \
  avaliacao-app
```

## Troubleshooting

### Erro: "failed to read dockerfile"
- Verifique se o Dockerfile está na raiz do projeto
- Verifique se está commitado no Git

### Erro de conexão com banco
- Verifique se as variáveis de ambiente estão corretas
- Verifique se o Supabase está acessível
- Aguarde alguns minutos se o circuit breaker estiver aberto

### Erro de porta
- O Render define a porta automaticamente via variável `PORT`
- Não precisa configurar porta manualmente
