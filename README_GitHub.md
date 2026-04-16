# 👨‍💻 Guia de Desenvolvimento: Código e Versionamento

Este guia tem como objetivo ajudar todos os integrantes do projeto a trabalharem de forma organizada utilizando **Git** e **GitHub** — desde baixar o código na máquina até colaborar de forma profissional.

---

## 🚀 1. Clonando o Repositório (Baixando o Código)

Primeiro, você precisa trazer o projeto para sua máquina.

### 🔧 Requisitos

- Ter o **Git** instalado: https://git-scm.com/
- Ter uma conta no **GitHub**

---

### 📥 Passo a passo

1. Acesse o repositório no GitHub  
2. Clique no botão verde **Code**  
3. Copie a URL do repositório  

4. Abra o terminal (ou Git Bash) e execute:

```bash
git clone https://github.com/seu-usuario/seu-repositorio.git
```

5. Acesse a pasta do projeto:
```bash
cd seu-repositorio
```

## 🌿 2. Entendendo as Branches

Utilizamos branches para evitar conflitos e manter o projeto organizado.

### 🌳 Estrutura de branches:
main → versão estável (NÃO modificar diretamente)
dev → versão de desenvolvimento
feature/nome-da-feature → novas funcionalidades

## 🛠️ 3. Criando sua Branch

Antes de começar qualquer tarefa:
```bash
git checkout dev
git pull origin dev
git checkout -b feature/nome-da-sua-feature
```

##✍️ 4. Fazendo Alterações

Agora você pode editar o código normalmente na sua IDE:

IntelliJ IDEA
VS Code
ou outra de sua preferência

## 💾 5. Salvando Alterações (Commit)

Depois de fazer alterações:
```bash
git add .
git commit -m "feat: descrição curta do que foi feito"
```

### 💡 Exemplos de commit:
feat: adicionar tela inicial
fix: corrigir erro no botão
docs: atualizar README

## ⬆️ 6. Enviando para o GitHub
```bash
git push origin feature/nome-da-sua-feature
```

##🔀 7. Criando um Pull Request
Acesse o repositório no GitHub
Clique em Compare & Pull Request
Adicione uma descrição do que foi feito
Envie o Pull Request

OBS: Outro integrante deve revisar antes de aprovar

## 🔄 8. Mantendo o Código Atualizado

Antes de começar uma nova tarefa:
```bash
git checkout dev
git pull origin dev
```

## ⚠️ Regras Importantes
❌ NÃO fazer push direto na main
❌ NÃO trabalhar direto na dev sem branch
✅ Sempre criar uma branch para cada tarefa
✅ Sempre usar Pull Request
✅ Manter commits organizados e claros

## 🧠 Fluxo de Trabalho Recomendado
Atualizar a branch dev
Criar uma nova branch
Desenvolver a funcionalidade
Fazer commit das alterações
Enviar para o GitHub
Abrir Pull Request
Aguardar revisão
Fazer merge na dev

## 🧩 Ferramentas Recomendadas
IntelliJ IDEA
VS Code
Git Bash / Terminal
GitHub Desktop (opcional para iniciantes)

## 🏁 Objetivo Final

Manter o projeto:

Organizado 📂
Estável ⚙️
Colaborativo 🤝
