package veterinaria.model;

import java.sql.Date;
import java.sql.Time;

public class Atendimento {
    private int id;
    private int petId;
    private int veterinarioId;
    private Date dataAtendimento;
    private Time horaAtendimento;
    private String descricao;
    private String diagnostico;
    private double valor;

    // Construtor vazio.
    public Atendimento() {}

    // Construtor completo (com id), existe no banco de dados.
    public Atendimento(int id, int petId, int veterinarioId, Date dataAtendimento, Time horaAtendimento, String descricao, String diagnostico, double valor) {
        this.id = id;
        this.petId = petId;
        this.veterinarioId = veterinarioId;
        this.dataAtendimento = dataAtendimento;
        this.horaAtendimento = horaAtendimento;
        this.descricao = descricao;
        this.diagnostico = diagnostico;
        this.valor = valor;
    }

    // Construtor sem id, registra um atendimento novo.
    public Atendimento(int petId, int veterinarioId, Date dataAtendimento, Time horaAtendimento, String descricao, String diagnostico, double valor) {
        this.petId = petId;
        this.veterinarioId = veterinarioId;
        this.dataAtendimento = dataAtendimento;
        this.horaAtendimento = horaAtendimento;
        this.descricao = descricao;
        this.diagnostico = diagnostico;
        this.valor = valor;
    }

    // Getters e Setters de cada campo.
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public int getPetId() { return petId; }
    public void setPetId(int petId) { this.petId = petId; }

    public int getVeterinarioId() { return veterinarioId; }
    public void setVeterinarioId(int veterinarioId) { this.veterinarioId = veterinarioId; }

    public Date getDataAtendimento() { return dataAtendimento; }
    public void setDataAtendimento(Date dataAtendimento) { this.dataAtendimento = dataAtendimento; }

    public Time getHoraAtendimento() { return horaAtendimento; }
    public void setHoraAtendimento(Time horaAtendimento) { this.horaAtendimento = horaAtendimento; }

    public String getDescricao() { return descricao; }
    public void setDescricao(String descricao) { this.descricao = descricao; }

    public String getDiagnostico() { return diagnostico; }
    public void setDiagnostico(String diagnostico) { this.diagnostico = diagnostico; }

    public double getValor() { return valor; }
    public void setValor(double valor) { this.valor = valor; }
}
