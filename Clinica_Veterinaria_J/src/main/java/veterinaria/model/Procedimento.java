package veterinaria.model;

public class Procedimento {
    private int id;
    private String nome;
    private String descricao;
    private double valor;

    // Construtor vazio.
    public Procedimento() {}

    // Construtor completo (com id), procedimento que já existe no banco.
    public Procedimento(int id, String nome, String descricao, double valor) {
        this.id = id;
        this.nome = nome;
        this.descricao = descricao;
        this.valor = valor;
    }

    // Construtor sem id, para cadastrar um procedimento novo.
    public Procedimento(String nome, String descricao, double valor) {
        this.nome = nome;
        this.descricao = descricao;
        this.valor = valor;
    }

    // Getters e Setters.
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }

    public String getDescricao() { return descricao; }
    public void setDescricao(String descricao) { this.descricao = descricao; }

    public double getValor() { return valor; }
    public void setValor(double valor) { this.valor = valor; }

    // Como o Procedimento aparece quando é exibido como texto.
    @Override
    public String toString() {
        return this.nome + " - R$ " + this.valor;
    }
}
