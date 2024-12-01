package br.edu.ifba.monitoramento.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import br.edu.ifba.monitoramento.cancela.Cancela;
import br.edu.ifba.monitoramento.veiculos.Veiculo;
import br.edu.ifba.monitoramento.operacoes.Operacoes;

public class OperacoesImpl implements Operacoes<Cancela, Veiculo> {

    /**
     * Método que imprime informações de todas as cancelas fornecidas.
     * 
     * A complexidade deste método é linear, O(N).
     */
    @Override
    public void imprimir(List<Cancela> cancelas) {
        for (Cancela cancela : cancelas) {
            System.out.println(cancela);
        }
    }

    /**
     * Método que imprime os registros de veículos associados a cada cancela.
     * 
     * A complexidade deste método é quadrática, O(N^2).
     */
    @Override
    public void imprimir(Map<Cancela, List<Veiculo>> registros) {
        for (Map.Entry<Cancela, List<Veiculo>> entry : registros.entrySet()) {
            Cancela cancela = entry.getKey();
            List<Veiculo> veiculos = entry.getValue();
            System.out.println("Registro da cancela " + cancela.getIdentificacao() + ":");
            for (Veiculo veiculo : veiculos) {
                System.out.println(veiculo);
            }
        }
    }

    /**
     * Método que ordena a lista de veículos de cada cancela por suas placas.
     * 
     * A complexidade deste método é O(N).
     */
    @Override
public Map<Cancela, List<Veiculo>> ordenar(Map<Cancela, List<Veiculo>> registros) {
    // Implementação para ordenar registros manualmente usando Bubble Sort
    for (List<Veiculo> veiculos : registros.values()) {
        bubbleSort(veiculos);
    }
    return registros;
}

/**
 * Método para ordenar uma lista de veículos usando o algoritmo Bubble Sort.
 * Complexidade: O(N^2)
 */
private void bubbleSort(List<Veiculo> veiculos) {
    int n = veiculos.size();
    boolean trocou;
    for (int i = 0; i < n - 1; i++) {
        trocou = false;
        for (int j = 0; j < n - i - 1; j++) {
            if (veiculos.get(j).getPlaca().compareTo(veiculos.get(j + 1).getPlaca()) > 0) {
                // Trocar os elementos
                Veiculo temp = veiculos.get(j);
                veiculos.set(j, veiculos.get(j + 1));
                veiculos.set(j + 1, temp);
                trocou = true;
            }
        }
        // Se não houve troca na iteração, a lista já está ordenada
        if (!trocou) break;
    }
}

    /**
     * Para cada cancela, obtemos a lista de veículos
     * A complexidade é O(N³), onde N é o número total de veículos, pois combinamos pares de veículos
     * Este método pode gerar uma situação de brute force, especialmente com um número elevado de veículos,
     * levando a um aumento significativo no tempo de processamento, pois é necessário verificar todas as combinações possíveis.
     */
    @Override
    public List<List<Veiculo>> combinarVeiculos(Map<Cancela, List<Veiculo>> registros) {
        List<List<Veiculo>> combinacoes = new ArrayList<>();
        // Para cada cancela, obtemos a lista de veículos
        for (List<Veiculo> listaVeiculos : registros.values()) {
            int n = listaVeiculos.size();
            // Criando combinações de dois veículos
            for (int i = 0; i < n; i++) {
                for (int j = i + 1; j < n; j++) {
                    List<Veiculo> combinacao = new ArrayList<>();
                    combinacao.add(listaVeiculos.get(i));
                    combinacao.add(listaVeiculos.get(j));
                    // Adicionando a combinação à lista
                    combinacoes.add(combinacao);
                }
            }
        }
        return combinacoes;
    }
}