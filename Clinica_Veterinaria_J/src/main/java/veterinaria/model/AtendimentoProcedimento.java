package veterinaria.model;

public class AtendimentoProcedimento {
    private int atendimentoId;
    private int procedimentoId;
    private int quantidade;
    private double valor;

    public AtendimentoProcedimento() {}

    public AtendimentoProcedimento(int atendimentoId, int procedimentoId, int quantidade, double valor) {
        this.atendimentoId = atendimentoId;
        this.procedimentoId = procedimentoId;
        this.quantidade = quantidade;
        this.valor = valor;
    }

    // Getters e Setters.
    public int getAtendimentoId() { return atendimentoId; }
    public void setAtendimentoId(int atendimentoId) { this.atendimentoId = atendimentoId; }

    public int getProcedimentoId() { return procedimentoId; }
    public void setProcedimentoId(int procedimentoId) { this.procedimentoId = procedimentoId; }

    public int getQuantidade() { return quantidade; }
    public void setQuantidade(int quantidade) { this.quantidade = quantidade; }

    public double getValor() { return valor; }
    public void setValor(double valor) { this.valor = valor; }
}
