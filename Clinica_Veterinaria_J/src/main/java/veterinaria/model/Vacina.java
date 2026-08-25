package veterinaria.model;

import java.sql.Date;

public class Vacina {
    private int id;
    private int petId;
    private String nome;
    private Date dataAplicacao;
    private Date proximaDose;

    public Vacina() {}

    public Vacina(int id, int petId, String nome, Date dataAplicacao, Date proximaDose) {
        this.id = id;
        this.petId = petId;
        this.nome = nome;
        this.dataAplicacao = dataAplicacao;
        this.proximaDose = proximaDose;
    }

    public Vacina(int petId, String nome, Date dataAplicacao, Date proximaDose) {
        this.petId = petId;
        this.nome = nome;
        this.dataAplicacao = dataAplicacao;
        this.proximaDose = proximaDose;
    }

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