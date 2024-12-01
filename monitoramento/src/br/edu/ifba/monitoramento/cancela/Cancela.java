package br.edu.ifba.monitoramento.cancela;

public class Cancela {
    private String identificacao;

    public Cancela(String identificacao) {
        this.identificacao = identificacao;
    }

    public String getIdentificacao() {
        return identificacao;
    }

    @Override
    public String toString() {
        return "Cancela " + identificacao;
    }

}

