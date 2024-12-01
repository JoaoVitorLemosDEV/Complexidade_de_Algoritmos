package br.edu.ifba.monitoramento.operacoes;

import java.util.List;
import java.util.Map;

public interface Operacoes<Cancela, Veiculo> {

    // implementando d.1
    void imprimir(List<Cancela> cancelas);

    // implementando d.2
    void imprimir(Map<Cancela, List<Veiculo>> registros);

    // implementando d.3
    Map<Cancela, List<Veiculo>> ordenar(Map<Cancela, List<Veiculo>> registros);

    // implementando d.4
    public List<List<Veiculo>> combinarVeiculos(Map<Cancela, List<Veiculo>> registros);
}
