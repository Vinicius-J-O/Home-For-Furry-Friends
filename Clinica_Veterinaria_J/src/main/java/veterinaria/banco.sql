CREATE DATABASE IF NOT EXISTS veterinaria;
USE veterinaria;

-- 1. Tabela TUTOR
CREATE TABLE IF NOT EXISTS tutor (
    id INT AUTO_INCREMENT PRIMARY KEY,
    nome VARCHAR(100) NOT NULL,
    cpf VARCHAR(14) UNIQUE NOT NULL,
    telefone VARCHAR(20),
    email VARCHAR(100),
    endereco VARCHAR(200)
);

-- 2. Tabela PET
CREATE TABLE IF NOT EXISTS pet (
    id INT AUTO_INCREMENT PRIMARY KEY,
    tutor_id INT NOT NULL,
    nome VARCHAR(100) NOT NULL,
    especie VARCHAR(50) NOT NULL,
    raca VARCHAR(50),
    sexo CHAR(1),
    data_nascimento DATE,
    peso DECIMAL,
    FOREIGN KEY (tutor_id) REFERENCES tutor(id) ON DELETE CASCADE
);

-- 3. Tabela VETERINARIO
CREATE TABLE IF NOT EXISTS veterinario (
    id INT AUTO_INCREMENT PRIMARY KEY,
    nome VARCHAR(100) NOT NULL,
    crmv VARCHAR(20) UNIQUE NOT NULL,
    telefone VARCHAR(20),
    especialidade VARCHAR(100)
);

-- 4. Tabela ATENDIMENTO
CREATE TABLE IF NOT EXISTS atendimento (
    id INT AUTO_INCREMENT PRIMARY KEY,
    pet_id INT NOT NULL,
    veterinario_id INT NOT NULL,
    data_atendimento DATE NOT NULL,
    hora_atendimento TIME NOT NULL,
    descricao TEXT,
    diagnostico TEXT,
    valor DECIMAL NOT NULL DEFAULT 0.0,
    FOREIGN KEY (pet_id) REFERENCES pet(id),
    FOREIGN KEY (veterinario_id) REFERENCES veterinario(id)
);

-- 5. Tabela PROCEDIMENTO
CREATE TABLE IF NOT EXISTS procedimento (
    id INT AUTO_INCREMENT PRIMARY KEY,
    nome VARCHAR(100) NOT NULL,
    descricao TEXT,
    valor DECIMAL NOT NULL DEFAULT 0.0
);

-- 6. Tabela EXAME
CREATE TABLE IF NOT EXISTS exame (
    id INT AUTO_INCREMENT PRIMARY KEY,
    nome VARCHAR(100) NOT NULL,
    descricao TEXT,
    valor DECIMAL NOT NULL DEFAULT 0.0
);

-- 7. Tabela VACINA
CREATE TABLE IF NOT EXISTS vacina (
    id INT AUTO_INCREMENT PRIMARY KEY,
    pet_id INT NOT NULL,
    nome VARCHAR(100) NOT NULL,
    data_aplicacao DATE NOT NULL,
    proxima_dose DATE,
    FOREIGN KEY (pet_id) REFERENCES pet(id) ON DELETE CASCADE
);

-- 8. Tabela Relacional ATENDIMENTO_PROCEDIMENTO (N:N)
CREATE TABLE IF NOT EXISTS atendimento_procedimento (
    atendimento_id INT NOT NULL,
    procedimento_id INT NOT NULL,
    quantidade INT NOT NULL DEFAULT 1,
    valor DOUBLE NOT NULL,
    PRIMARY KEY (atendimento_id, procedimento_id),
    FOREIGN KEY (atendimento_id) REFERENCES atendimento(id) ON DELETE CASCADE,
    FOREIGN KEY (procedimento_id) REFERENCES procedimento(id)
);

-- 9. Tabela Relacional ATENDIMENTO_EXAME (N:N)
CREATE TABLE IF NOT EXISTS atendimento_exame (
    atendimento_id INT NOT NULL,
    exame_id INT NOT NULL,
    resultado TEXT,
    valor DOUBLE NOT NULL,
    PRIMARY KEY (atendimento_id, exame_id),
    FOREIGN KEY (atendimento_id) REFERENCES atendimento(id) ON DELETE CASCADE,
    FOREIGN KEY (exame_id) REFERENCES exame(id)
);

SELECT * FROM tutor;
SELECT * FROM pet;
SELECT * FROM veterinario;
SELECT * FROM atendimento;
SELECT * FROM procedimento;
SELECT * FROM exame;
SELECT * FROM vacina;
SELECT * FROM atendimento_procedimento;
SELECT * FROM atendimento_exame;
