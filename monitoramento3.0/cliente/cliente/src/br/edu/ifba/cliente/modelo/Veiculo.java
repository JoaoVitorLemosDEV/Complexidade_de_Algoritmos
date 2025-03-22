package br.edu.ifba.cliente.modelo;

import br.edu.ifba.cliente.cancela.Cancela;

public class Veiculo {
    private static int contador = 1;
    private int id;
    private String placa;
    private Cancela cancela;  

    public Veiculo(String placa) {
        this.id = contador++;
        this.placa = placa;
    }

    public int getId() {
        return id;
    }

    public String getPlaca() {
        return placa;
    }

    public Cancela getCancela() {
        return cancela;
    }

    public void setCancela(Cancela cancela) {
        this.cancela = cancela;  
    }

    @Override
    public String toString() {
        return "Veículo { Carro=" + id + ", placa='" + placa + "', Cancela=" + cancela.getIdentificacao() + " }";
    }
}