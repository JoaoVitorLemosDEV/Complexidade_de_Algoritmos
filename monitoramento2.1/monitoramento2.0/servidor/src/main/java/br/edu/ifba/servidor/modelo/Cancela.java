package br.edu.ifba.servidor.modelo;

import java.util.Objects;

public class Cancela implements Comparable<Cancela> {
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

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Cancela cancela = (Cancela) obj;
        return Objects.equals(identificacao, cancela.identificacao);
    }

    @Override
    public int hashCode() {
        return Objects.hash(identificacao);
    }

    @Override
    public int compareTo(Cancela outra) {
        return this.identificacao.compareTo(outra.identificacao);
    }
}
