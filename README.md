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
```

#### 2️⃣ Instalar/Configurar um Banco de dados Mysql
- Instalar MySQL 8+
- Criar um Schema com nome API_CEP


### 2️⃣ Configurar um Docker de um Banco de dados Mysql
```docker
version: "4"

services:
  mysql-cep:
    image: mysql:8.3.0
    container_name: address-database
    environment:
      MYSQL_ROOT_PASSWORD: ${MYSQL_ROOT_PASSWORD:-mysql} 
    ports:
      - "3307:3306"
    volumes:
      - ./mysql:/var/lib/mysql
      - ./docker/mysql/init.sql:/docker-entrypoint-initdb.d/init.sql 
```

##OBS
- Criar um pasta na raiz do projeto(junto ao docker-compose.yml) com o nome de docker
- Dentro da pasta docker criar uma subpasta chamada mysql
- dentro dessa subpasta criar um arquivo com o nome de init.sql

#init.sql
```sql
CREATE DATABASE IF NOT EXISTS API_CEP;
```

#### 4️⃣ Alterar parametros de conexao com o banco de dados no backend 
```application.properties
spring.application.name=addressAPI
spring.datasource.url=jdbc:mysql://***********:330*/API_CEP ---- endereço do banco de dados  e porta do banco de dados 
spring.datasource.driver-class-name=com.mysql.cj.jdbc.Driver
spring.datasource.username=***** --- usuario do banco de dados 
spring.datasource.password=***** --- senha do banco de dados
```

##Caso utilizar o docker passado

```application.properties
spring.datasource.url=jdbc:mysql://localhost:3307/API_CEP
spring.datasource.driver-class-name=com.mysql.cj.jdbc.Driver
spring.datasource.username=root
spring.datasource.password=mysql
api.prefix=/api/v1
```

#### 4️⃣ Executar o backend 
```bash
cd addressAPI
./mvnw spring-boot:run
```


#### 5️⃣ Executar o frontend
```bash
cd AddressFront
npm install
npm run dev
```


###$📄 Licença
Este projeto é de uso livre para fins educacionais e profissionais.


