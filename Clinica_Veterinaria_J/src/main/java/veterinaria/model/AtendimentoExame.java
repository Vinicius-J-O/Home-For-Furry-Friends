package veterinaria.model;

public class AtendimentoExame {
    private int atendimentoId;
    private int exameId;
    private String resultado;
    private double valor;

    public AtendimentoExame() {}

    public AtendimentoExame(int atendimentoId, int exameId, String resultado, double valor) {
        this.atendimentoId = atendimentoId;
        this.exameId = exameId;
        this.resultado = resultado;
        this.valor = valor;
    }

    public int getAtendimentoId() { return atendimentoId; }
    public void setAtendimentoId(int atendimentoId) { this.atendimentoId = atendimentoId; }

    public int getExameId() { return exameId; }
    public void setExameId(int exameId) { this.exameId = exameId; }

    public String getResultado() { return resultado; }
    public void setResultado(String resultado) { this.resultado = resultado; }

    public double getValor() { return valor; }
    public void setValor(double valor) { this.valor = valor; }
}