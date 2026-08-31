package veterinaria.model;

public class Veterinario {
    private int id;
    private String nome;
    private String crmv;
    private String telefone;
    private String especialidade;

    // Construtor vazio.
    public Veterinario() {}

    // Construtor completo (com id), pra veterinários que já existem no banco.
    public Veterinario(int id, String nome, String crmv, String telefone, String especialidade) {
        this.id = id;
        this.nome = nome;
        this.crmv = crmv;
        this.telefone = telefone;
        this.especialidade = especialidade;
    }

    // Construtor sem id, pra cadastrar um veterinário novo.
    public Veterinario(String nome, String crmv, String telefone, String especialidade) {
        this.nome = nome;
        this.crmv = crmv;
        this.telefone = telefone;
        this.especialidade = especialidade;
    }

    // Getters e Setters.
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

    // Como o Veterinário aparece na lista suspensa (JComboBox) da tela de Atendimento,
    // onde o usuário escolhe qual veterinário realizou a consulta.
    @Override
    public String toString() {
        return this.nome + " (CRMV: " + this.crmv + ")";
    }
}
