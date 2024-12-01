package br.edu.ifba.monitoramento.veiculos;

public class Veiculo {
    private String placa;

    public Veiculo(String placa) {
        this.placa = placa;
    }

    public String getPlaca() {
        return placa;
    }

    @Override
    public String toString() {
        return "Veículo { placa='" + placa + "' }";
    }
}