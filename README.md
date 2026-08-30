# 🐾 Home for Furry Friends — Sistema Veterinário

> **Sistema Desktop de Gerenciamento Veterinário**  
> Projeto prático de desenvolvimento para centralização e controle de atendimentos, tutores, pets, vacinas e procedimentos clínicos.

---

## 📋 Sumário
- [Visão Geral](#-visão-geral)
- [Problema vs. Solução](#-problema-vs-solução)
- [Funcionalidades Principais](#-funcionalidades-principais)
- [Tecnologias Utilizadas](#-tecnologias-utilizadas)
- [Arquitetura do Sistema](#-arquitetura-do-sistema)
- [Modelo de Dados & Entidades](#-modelo-de-dados--entidades)
- [Regras de Negócio & Validações](#-regras-de-negócio--validações)
- [Fluxo Principal de Atendimento](#-fluxo-principal-de-atendimento)
- [Guia de Identidade Visual (UI)](#-guia-de-identidade-visual-ui)
- [Escopo e Limitações](#-escopo-e-limitações)
- [Como Executar o Projeto](#-como-executar-o-projeto)

---

## 🌟 Visão Geral

A **Home for Furry Friends** é uma clínica veterinária destinada ao atendimento de cães, gatos e outros animais domésticos. O sistema **Veterinaria** foi concebido como uma aplicação desktop Java para modernizar a rotina operacional da clínica, substituindo o uso de fichas físicas por um banco de dados relacional seguro e organizado.

### Benefícios Esperados
- **Centralização:** Todos os dados da clínica em um único ambiente.
- **Histórico Acessível:** Agilidade na consulta ao histórico médico, exames e vacinações.
- **Redução de Erros:** Validações de entrada que reduzem inconsistências de cadastro.
- **Eficiência:** Eliminação gradual do uso de papéis e fichas físicas.

---

## 🛑 Problema vs. 💡 Solução

| Cenário Anterior (Manual) | Solução com o Sistema Veterinaria |
| :--- | :--- |
| Perda ou dificuldade de localização de prontuários. | Histórico médico digital unificado por pet. |
| Dificuldade para acompanhar o histórico de vacinação. | Controle de doses aplicadas e agendamento de próximas doses. |
| Perda de vínculo entre tutor e pet. | Relacionamento $1:N$ bem estruturado entre Tutor e Pets. |
| Falta de controle financeiro dos atendimentos. | Registro detalhado de valores de consultas, exames e procedimentos. |
| Falta de identificação do veterinário responsável. | Vínculo explícito do CRMV/Veterinário em cada atendimento. |

---

## ✨ Funcionalidades Principais

- **Gerenciamento de Tutores (CRUD):** Cadastro completo com validações de CPF e contatos.
- **Gerenciamento de Pets (CRUD):** Registro de animais vinculados diretamente a um tutor responsável.
- **Gerenciamento de Veterinários (CRUD):** Controle de profissionais habilitados e seus CRMVs.
- **Registro de Atendimentos:**
  - Diagnósticos e observações clínicas.
  - Vínculo de múltiplos procedimentos executados (com quantidades e valores).
  - Vínculo de múltiplos exames (com resultados e valores).
  - Cálculo e manutenção do valor final consolidado do atendimento.
- **Controle de Vacinação:** Registro de vacinas aplicadas e data prevista para próxima dose.

---

## 🛠️ Tecnologias Utilizadas

| Tecnologia | Finalidade |
| :--- | :--- |
| **Java** | Linguagem principal da aplicação |
| **Java Swing** | Construção da Interface Gráfica de Usuário (GUI) Desktop |
| **MySQL** | Sistema Gerenciador de Banco de Dados Relacional (SGBD) |
| **JDBC** | API de conectividade entre a aplicação Java e o banco MySQL |
| **SQL** | Criação, manipulação e estruturação de dados |

---

## 🏗️ Arquitetura do Sistema

O sistema adota uma arquitetura em camadas simples, promovendo a separação entre a interface com o usuário, as regras de domínio e a camada de persistência:

```
┌──────────────────────────────────────────────────┐
│                Java Swing (GUI)                  │
└──────────────────────────────────────────────────┘
                         ↓
┌──────────────────────────────────────────────────┐
│              Classes Java / Domínio              │
└──────────────────────────────────────────────────┘
                         ↓
┌──────────────────────────────────────────────────┐
│             JDBC / Acesso ao Banco               │
└──────────────────────────────────────────────────┘
                         ↓
┌──────────────────────────────────────────────────┐
│               MySQL / Banco de Dados             │
└──────────────────────────────────────────────────┘
```

---

## 📊 Modelo de Dados & Entidades

### Entidades do Domínio

1. **TUTOR:** `id` (PK), `nome`, `cpf`, `telefone`, `email`, `endereco`
2. **PET:** `id` (PK), `tutor_id` (FK), `nome`, `especie`, `raca`, `sexo`, `data_nascimento`, `peso`
3. **VETERINARIO:** `id` (PK), `nome`, `crmv`, `telefone`, `especialidade`
4. **ATENDIMENTO:** `id` (PK), `pet_id` (FK), `veterinario_id` (FK), `data`, `hora`, `descricao`, `diagnostico`, `valor`
5. **PROCEDIMENTO:** `id` (PK), `nome`, `descricao`, `valor`
6. **EXAME:** `id` (PK), `nome`, `descricao`, `valor`
7. **VACINA:** `id` (PK), `pet_id` (FK), `nome`, `data_aplicacao`, `proxima_dose`
8. **ATENDIMENTO_PROCEDIMENTO:** `atendimento_id` (PK/FK), `procedimento_id` (PK/FK), `quantidade`, `valor`
9. **ATENDIMENTO_EXAME:** `atendimento_id` (PK/FK), `exame_id` (PK/FK), `resultado`, `valor`

### Relacionamentos e Multiplicidades

- **Tutor → Pet:** $1 : 0..*$ *(Um tutor pode possuir múltiplos pets)*
- **Pet → Atendimento:** $1 : 0..*$ *(Um pet pode passar por múltiplos atendimentos)*
- **Veterinário → Atendimento:** $1 : 0..*$ *(Um veterinário pode realizar múltiplos atendimentos)*
- **Pet → Vacina:** $1 : 0..*$ *(Um pet pode ter vários registros de vacina)*
- **Atendimento ← Procedimento:** $N : N$ *(Implementado via tabela associativa `ATENDIMENTO_PROCEDIMENTO`)*
- **Atendimento ← Exame:** $N : N$ *(Implementado via tabela associativa `ATENDIMENTO_EXAME`)*

---

## 📐 Regras de Negócio & Validações

### Regras de Negócio (RN)
- **RN01:** Cada tutor possui ID único; o CPF é utilizado para identificação.
- **RN02:** Um tutor pode ter vários pets; cada pet pertence obrigatoriamente a um tutor.
- **RN03:** Cada pet possui um identificador único.
- **RN04/RN05:** Cada atendimento está associado a exatamente um pet e um veterinário responsável.
- **RN07/RN08:** Um atendimento pode conter múltiplos exames e procedimentos.
- **RN09/RN10:** As tabelas associativas guardam o histórico de valores e resultados praticados no momento do atendimento.
- **RN11:** O atendimento deve consolidar o valor total final.

### Validações Obrigatórias
- **Tutor:** Nome e CPF obrigatórios. Validação de formato de e-mail quando informado.
- **Pet:** Nome, espécie e tutor obrigatórios; `peso` não pode ser negativo.
- **Veterinário:** Nome e CRMV obrigatórios.
- **Atendimento:** Pet, veterinário, data e hora obrigatórios; `valor` não pode ser negativo.
- **Procedimentos / Exames:** Nome obrigatório; `valor` não pode ser negativo.
- **Vacina:** Pet, nome da vacina e data de aplicação obrigatórios.

---

## 🔄 Fluxo Principal de Atendimento

```
[01] Cadastro do Tutor → [02] Cadastro do Pet → [03] Vínculo Pet → Tutor                                                                    
                                                              ↓
[06] Finalização ← [05] Registro de Atendimento ← [04] Seleção do Veterinário
       │                  ├── Diagnóstico
       ↓                  ├── Procedimentos
[07] Histórico do Pet     ├── Exames
                          └── Valores
```

1. **Cadastro do Tutor:** Inclusão/consulta dos dados do responsável.
2. **Cadastro do Pet:** Registro dos dados do animal.
3. **Vínculo:** Associação do pet ao seu tutor.
4. **Seleção do Veterinário:** Escolha do profissional responsável pela consulta.
5. **Atendimento:** Inclusão de sintomas, diagnóstico, exames realizados, procedimentos e cálculo de custos.
6. **Finalização:** Confirmação e gravação do atendimento.
7. **Consulta ao Histórico:** Acesso ao histórico completo de saúde do pet.

---

## 🎨 Guia de Identidade Visual (UI)

Para a construção das telas Java Swing, adota-se a seguinte paleta de cores:

| Elemento | Cor | Hexadecimal |
| :--- | :--- | :--- |
| **Cor Principal** | Verde Veterinário | `#2E7D6B` |
| **Principal Escura** | Verde Escuro | `#205B4E` |
| **Destaque** | Azul Claro | `#5BB8C5` |
| **Fundo Principal** | Branco Gelo | `#F7FAF9` |
| **Cards / Painéis** | Branco | `#FFFFFF` |
| **Texto Principal** | Cinza Escuro | `#263238` |
| **Texto Secundário** | Cinza | `#607D7B` |
| **Bordas** | Cinza Claro | `#D9E5E2` |
| **Sucesso** | Verde | `#43A047` |
| **Erro / Excluir** | Vermelho | `#D9534F` |
| **Aviso** | Laranja | `#F4A261` |

---

## 🚫 Escopo e Limitações

### Escopo Inicial
- CRUDs completos para Tutores, Pets, Veterinários e Atendimentos.
- Interface desktop em Java Swing com conexão via JDBC ao MySQL.

### Fora do Escopo Inicial (Versões Futuras)
- Aplicativo Mobile e Sistema Web.
- Telemedicina e agendamento/pagamento online.
- Controle de estoque de medicamentos.
- Folha de pagamento e gestão RH.
- Envio de SMS / Notificações.
- Diagnóstico automatizado por Inteligência Artificial.

---

## 📄 Licença e Considerações Finais

Documentação referente à **Etapa 1** do projeto prático de desenvolvimento do sistema desktop **Home for Furry Friends**. Servirá como guia técnico para implementação do código-fonte e apresentação acadêmica.
