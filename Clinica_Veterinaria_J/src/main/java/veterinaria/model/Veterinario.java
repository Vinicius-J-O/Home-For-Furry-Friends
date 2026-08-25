package veterinaria.model;

public class Veterinario {
    private int id;
    private String nome;
    private String crmv;
    private String telefone;
    private String especialidade;

    public Veterinario() {}

    public Veterinario(int id, String nome, String crmv, String telefone, String especialidade) {
        this.id = id;
        this.nome = nome;
        this.crmv = crmv;
        this.telefone = telefone;
        this.especialidade = especialidade;
    }

    public Veterinario(String nome, String crmv, String telefone, String especialidade) {
        this.nome = nome;
        this.crmv = crmv;
        this.telefone = telefone;
        this.especialidade = especialidade;
    }

    // Getters e Setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }

    public String getCrmv() { return crmv; }
    public void setCrmv(String crmv) { this.crmv = crmv; }

    public String getTelefone() { return telefone; }
    public void setTelefone(String telefone) { this.telefone = telefone; }

    public String getEspecialidade() { return especialidade; }
    public void setEspecialidade(String especialidade) { this.especialidade = especialidade; }

    @Override
    public String toString() {
        return this.nome + " (CRMV: " + this.crmv + ")";
    }
}