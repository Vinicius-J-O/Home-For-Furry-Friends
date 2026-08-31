package veterinaria.model;

import java.sql.Date;

public class Pet {
    private int id;
    private int tutorId;
    private String nome;
    private String especie;
    private String raca;
    private String sexo;
    private Date dataNascimento;
    private double peso;

    // Construtor vazio: cria um Pet "em branco" pra preencher depois com os setters.
    public Pet() {}

    // Construtor completo (com id), usado quando o pet já existe no banco de dados.
    public Pet(int id, int tutorId, String nome, String especie, String raca, String sexo, Date dataNascimento, double peso) {
        this.id = id;
        this.tutorId = tutorId;
        this.nome = nome;
        this.especie = especie;
        this.raca = raca;
        this.sexo = sexo;
        this.dataNascimento = dataNascimento;
        this.peso = peso;
    }

    // Construtor sem id, usado quando estamos cadastrando um pet novo
    // (o banco de dados é que vai gerar o id automaticamente).
    public Pet(int tutorId, String nome, String especie, String raca, String sexo, Date dataNascimento, double peso) {
        this.tutorId = tutorId;
        this.nome = nome;
        this.especie = especie;
        this.raca = raca;
        this.sexo = sexo;
        this.dataNascimento = dataNascimento;
        this.peso = peso;
    }

    // Getters e Setters, eles leem e alteram cada campo individualmente.
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public int getTutorId() { return tutorId; }
    public void setTutorId(int tutorId) { this.tutorId = tutorId; }

    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }

    public String getEspecie() { return especie; }
    public void setEspecie(String especie) { this.especie = especie; }

    public String getRaca() { return raca; }
    public void setRaca(String raca) { this.raca = raca; }

    public String getSexo() { return sexo; }
    public void setSexo(String sexo) { this.sexo = sexo; }

    public Date getDataNascimento() { return dataNascimento; }
    public void setDataNascimento(Date dataNascimento) { this.dataNascimento = dataNascimento; }

    public double getPeso() { return peso; }
    public void setPeso(double peso) { this.peso = peso; }

    // Define como o Pet aparece quando é exibido como texto, por exemplo,
    // na lista suspensa (JComboBox) da tela de Vacinas, onde o usuário escolhe
    // para qual pet está registrando a vacina.
    @Override
    public String toString() {
        return this.nome + " (" + this.especie + ")";
    }
}
