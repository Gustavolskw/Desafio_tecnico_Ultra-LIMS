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

#### 3️⃣ Alterar parametros de conexao com o banco de dados no backend 
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


# 📌 Desafio 2: Implementação e Comparação de Algoritmos de Ordenação

## 📄 Estrutura do Código

O código está dividido em quatro partes principais:

### 1️⃣ Implementação dos Algoritmos de Ordenação
Foram implementados três algoritmos clássicos de ordenação:

- **QuickSort** → Utiliza o conceito de divisão e conquista para ordenar os elementos.
- **MergeSort** → Divide recursivamente a lista ao meio e depois mescla as partes ordenadas.
- **BubbleSort** → Percorre a lista repetidamente, trocando elementos adjacentes fora de ordem.

---

### 2️⃣ Geração de Listas de Teste
Foram geradas diferentes listas para avaliar a eficiência dos algoritmos em diversos cenários:

- **Lista Ordenada** → Representa o melhor caso para alguns algoritmos.
- **Lista Reversamente Ordenada** → Simula o pior caso de ordenação.
- **Lista Pequena** → Contém entre **10 e 100 elementos**.
- **Lista Média** → Contém entre **1.000 e 10.000 elementos**.
- **Lista Grande** → Contém **100.000 elementos ou mais**.
- **Lista com Muitos Duplicados** → Testa o impacto de valores repetidos na ordenação.

---

### 3️⃣ Execução e Medição de Tempo
Cada algoritmo foi executado e seu **tempo de execução medido em nanossegundos**, convertendo os resultados para **milissegundos (ms)** para facilitar a comparação.

A medição foi feita utilizando a função:
```java
long startTime = System.nanoTime();
sortFunction.sort(arr);
long endTime = System.nanoTime();
System.out.printf("%s: %.6f ms%n", name, (endTime - startTime) / 1e6);
```


####Execução

```bash
 cd Desafio2/
javac *.java
java SortingTest
```

####Saida

```java
--- Teste com Lista Pequena ---
QuickSort: 0.219939 ms
MergeSort: 0.013305 ms
BubbleSort: 0.006301 ms

--- Teste com Lista Média ---
QuickSort: 0.522994 ms
MergeSort: 0.709171 ms
BubbleSort: 4.088623 ms

--- Teste com Lista Grande ---
QuickSort: 9.359748 ms
MergeSort: 13.604571 ms
BubbleSort: 9218.686175 ms

--- Teste com Lista Ordenada ---
QuickSort: 0.480295 ms
MergeSort: 0.061054 ms
BubbleSort: 0.000932 ms

--- Teste com Lista Reversamente Ordenada ---
QuickSort: 0.339231 ms
MergeSort: 0.053770 ms
BubbleSort: 0.238474 ms

--- Teste com Lista com Muitos Duplicados ---
QuickSort: 0.042069 ms
MergeSort: 0.070541 ms
BubbleSort: 1.935815 ms

```




####📄 Licença
Este projeto é de uso livre para fins educacionais e profissionais.


