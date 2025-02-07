CREATE TABLE cep (
                     id BIGINT AUTO_INCREMENT PRIMARY KEY,
                     cep VARCHAR(8) NOT NULL UNIQUE,
                     logradouro VARCHAR(255),
                     complemento VARCHAR(255),
                     unidade VARCHAR(50) NULL,
                     bairro VARCHAR(255),
                     localidade VARCHAR(255),
                     uf CHAR(2) NOT NULL,
                     estado VARCHAR(255),
                     regiao VARCHAR(255),
                     ibge INT,
                     gia VARCHAR(50) NULL,
                     ddd INT,
                     siafi VARCHAR(50),
                     inserted_at DATETIME
);