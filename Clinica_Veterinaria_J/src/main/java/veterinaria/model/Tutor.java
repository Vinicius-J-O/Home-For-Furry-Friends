package veterinaria.model;

public class Tutor {
    private int id;
    private String nome;
    private String cpf;
    private String telefone;
    private String email;
    private String endereco;

    // Esse é um "construtor vazio", ele permite criar um Tutor sem preencher nada de início,
    // e depois ir preenchendo campo por campo usando os métodos "set" (setNome, setCpf, etc).
    public Tutor() {}

    // Esse construtor recebe todos os dados de uma vez, incluindo o "id".
    // É usado, por exemplo, quando trazemos um tutor que já existe no banco de dados
    // (o id já foi definido pelo banco quando o registro foi criado).
    public Tutor(int id, String nome, String cpf, String telefone, String email, String endereco) {
        this.id = id;
        this.nome = nome;
        this.cpf = cpf;
        this.telefone = telefone;
        this.email = email;
        this.endereco = endereco;
    }

    // Este outro construtor não recebe o "id", ele é usado quando estamos criando
    // um tutor novo, que ainda não existe no banco (o banco que vai gerar o id
    // automaticamente quando salvarmos).
    public Tutor(String nome, String cpf, String telefone, String email, String endereco) {
        this.nome = nome;
        this.cpf = cpf;
        this.telefone = telefone;
        this.email = email;
        this.endereco = endereco;
    }

    // Getters e Setters
    public int getId() {return id;}
    public void setId(int id) {this.id = id;}
    
    public String getNome() {return nome;}
    public void setNome(String nome) {this.nome = nome;}

    public String getCpf() {return cpf;}
    public void setCpf(String cpf) {this.cpf = cpf;}

    public String getTelefone() {return telefone;}
    public void setTelefone(String telefone) {this.telefone = telefone;}

    public String getEmail() {return email;}
    public void setEmail(String email) {this.email = email;}

    public String getEndereco() {return endereco;}
    public void setEndereco(String endereco) {this.endereco = endereco;}

    // O método toString() define como um Tutor deve aparecer quando é
    // transformado em texto, por exemplo dentro de uma lista suspensa (JComboBox)
    // na tela de cadastro de Pets, onde o usuário escolhe o tutor do pet.
    // Sem isso, apareceria algo sem sentido tipo "veterinaria.model.Tutor@1a2b3c".
    @Override
    public String toString() {
        return this.nome + " (CPF: " + this.cpf + ")";
    }
}
