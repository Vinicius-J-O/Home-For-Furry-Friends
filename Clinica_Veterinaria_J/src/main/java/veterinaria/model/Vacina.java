package veterinaria.model;

import java.sql.Date;

public class Vacina {
    private int id;
    private int petId;
    private String nome;
    private Date dataAplicacao;
    private Date proximaDose;

    // Construtor vazio.
    public Vacina() {}

    // Construtor completo (com id), vacina que já existe no banco.
    public Vacina(int id, int petId, String nome, Date dataAplicacao, Date proximaDose) {
        this.id = id;
        this.petId = petId;
        this.nome = nome;
        this.dataAplicacao = dataAplicacao;
        this.proximaDose = proximaDose;
    }

    // Construtor sem id, para registrar uma vacina nova.
    public Vacina(int petId, String nome, Date dataAplicacao, Date proximaDose) {
        this.petId = petId;
        this.nome = nome;
        this.dataAplicacao = dataAplicacao;
        this.proximaDose = proximaDose;
    }

    // Getters e Setters.
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public int getPetId() { return petId; }
    public void setPetId(int petId) { this.petId = petId; }

    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }

    public Date getDataAplicacao() { return dataAplicacao; }
    public void setDataAplicacao(Date dataAplicacao) { this.dataAplicacao = dataAplicacao; }

    public Date getProximaDose() { return proximaDose; }
    public void setProximaDose(Date proximaDose) { this.proximaDose = proximaDose; }
}
