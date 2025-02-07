# 📌 Sistema Web para Consulta e Armazenamento de Endereços  

Este repositório contém a implementação de um sistema web que permite a busca de endereços pelo CEP, utilizando a API pública **ViaCEP**, e o armazenamento das consultas para referência futura. A aplicação foi desenvolvida utilizando **Java com Spring Boot** no backend, **MySQL** como banco de dados e **Vue.js** no frontend.  

## 🎯 Objetivo  

O sistema possibilita que o usuário:  
- Consulte endereços a partir de um CEP informado.  
- Armazene os endereços consultados.  
- Visualize e ordene os registros salvos com base em critérios específicos.  

## 🛠️ Tecnologias Utilizadas  

### Backend  
- Java 17  
- Spring Boot 3.x  
- Spring Data JPA  
- MySQL  
- OpenFeign (para integração com a API do ViaCEP)  
- Spring Web  
- Spring Validation  
- Lombok  

### Frontend  
- Vue.js 3  
- Vue Router  
- Axios  
- Bootstrap 5  

## 📌 Funcionalidades  

- 🔍 **Consulta de Endereço**: O usuário informa um CEP e o sistema recupera os dados do ViaCEP.  
- 💾 **Armazenamento dos Endereços**: Os endereços buscados são salvos no banco de dados para consulta posterior.  
- 📄 **Listagem dos Endereços**: O sistema exibe os endereços armazenados.  
- 📌 **Ordenação**: Os registros podem ser ordenados por **Cidade**, **Bairro** ou **Estado**, em ordem crescente ou decrescente.  
- ⚠️ **Validação de CEP**: O sistema trata CEPs inválidos e exibe mensagens de erro adequadas.  

## 🚀 Como Executar o Projeto  

### Pré-requisitos  
- Docker e Docker Compose (recomendado)  
- Java 17+  
- Node.js 18+  
- MySQL 8+  

### 🏗️ Configuração e Execução  

#### 1️⃣ Clonar o repositório  
```bash
git clone https://github.com/Gustavolskw/Desafio_tecnico_Ultra-LIMS.git 
git clone git@github.com:Gustavolskw/Desafio_tecnico_Ultra-LIMS.git
cd Desafio_tecnico_Ultra-LIMS
