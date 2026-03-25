# 🤖 Totem Interativo – Museu de Robôs Exploradores de Marte

## 📌 Sobre o Projeto

Este projeto consiste no desenvolvimento de um **totem interativo** para um museu multitemático, com foco em uma exposição sobre **robôs exploradores do planeta Marte**.

A aplicação será construída em **Java**, utilizando **JDialog** para a interface gráfica, e terá como objetivo melhorar a experiência dos visitantes, permitindo:

- Visualizar obras da exposição 🖼️  
- Acessar informações sobre cada item 📖  
- Responder a um questionário interativo 📊  
- Visualizar os resultados em tempo real 📈  

---

## 🎯 Objetivo Geral

Desenvolver um sistema que simule um **totem digital interativo**, auxiliando visitantes na navegação e compreensão das obras expostas no museu.

---

## 🎯 Objetivos Específicos

- Identificar necessidades do usuário e transformá-las em soluções práticas  
- Aplicar conceitos de desenvolvimento de sistemas computacionais  
- Utilizar boas práticas de **Programação Orientada a Objetos (POO)**  
- Estimular o trabalho em equipe e integração de conhecimentos  

---

## 🧠 Contexto da Aplicação

A aplicação simula um cenário onde uma organização sem fins lucrativos cria um museu com o tema:

> 🤖 **Robôs Exploradores em Marte**

Dentro desse ambiente, os visitantes poderão interagir com um totem digital para:

- Explorar obras (imagens) relacionadas ao tema  
- Ler descrições e históricos das obras  
- Participar de uma pesquisa sobre a exposição  

---

## ⚙️ Funcionalidades do Sistema

### 🖼️ Exposição de Obras
- Exibição de no mínimo **10 imagens**
- Cada obra contém:
  - Título
  - Descrição
  - Contexto histórico

---

### 📊 Questionário Interativo
- Questionário com **5 perguntas de múltipla escolha**
- Ao final, o sistema apresenta:
  - Resultado consolidado das respostas

---

### 📈 Processamento de Dados
- As respostas são armazenadas em **estruturas de dados dinâmicas (vetores/listas)**
- O sistema realiza cálculos para:
  - Gerar estatísticas
  - Apoiar decisões futuras (manutenção ou encerramento da exposição)

> ⚠️ **Importante:**  
> Os dados **não são persistidos em disco** (armazenamento apenas em memória).

---

### 🔒 Privacidade (LGPD)
- O sistema **não coleta dados sensíveis**
- Nenhuma informação pessoal do visitante é armazenada

---

### 🖥️ Interface e Usabilidade
- Interface desenvolvida com **JDialog (obrigatório)**  
- Adaptado para uso em **telas sensíveis ao toque**
- Implementação de um:
  - ⌨️ **Teclado virtual próprio (dentro da aplicação)**  
  - Sem uso do teclado do sistema operacional  

---

## 🧱 Arquitetura do Projeto (POO)

O sistema segue o paradigma de **Programação Orientada a Objetos**, podendo conter classes como:

- Boas práticas com **Construtores** .
- **Encapsulamento** entre os códigos de suas respectivas classes.
- Organização entre pastas e pacotes.

---

## 🛠️ Tecnologias Utilizadas

- **Java**
- **Swing (JDialog)**
- Paradigma **POO**

---

## 🚀 Execução do Projeto

1. Clonar o repositório:
```bash
git clone <url-do-repositorio>
```

2. Abrir em uma IDE Java (IntelliJ, Eclipse ou NetBeans)
3. Executar a classe principal:
```bash
Main.java
```
