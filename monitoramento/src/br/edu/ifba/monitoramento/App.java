package br.edu.ifba.monitoramento;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import br.edu.ifba.monitoramento.cancela.Cancela;
import br.edu.ifba.monitoramento.impl.OperacoesImpl;
import br.edu.ifba.monitoramento.veiculos.Veiculo;

public class App {
    private static final String[] PREFIXOS = {"ABC", "XYZ", "JKL", "DEF", "GHI"};
    private static final Random random = new Random();

    public static void main(String[] args) {
        // Criar cancelas de 1 a 10
        // A complexidade é O(N)
        List<Cancela> cancelas = new ArrayList<>();
        for (int i = 1; i <= 10; i++) {
            cancelas.add(new Cancela("" + i));
        }

        // Criando os registros de veículos
        // A complexidade é O(N)
        Map<Cancela, List<Veiculo>> registros = new LinkedHashMap<>(); // Usar LinkedHashMap para manter a ordem
        for (Cancela cancela : cancelas) {
            registros.put(cancela, new ArrayList<>());
        }

        // Simulando a entrada de veículos com placas aleatórias
        // A complexidade é O(N^2)
        for (Cancela cancela : cancelas) {
            int numVeiculos = random.nextInt(5) + 1; // Entre 1 e 5 veículos por cancela
            for (int j = 0; j < numVeiculos; j++) {
                String placa = gerarPlacaAleatoria();
                Veiculo veiculo = new Veiculo(placa);
                registros.get(cancela).add(veiculo);
            }
        }

        // Criando a instância de operações
        OperacoesImpl operacoes = new OperacoesImpl();

        // d1. imprimindo a lista de cancelas monitoradas
        System.out.println("Lista de Cancelas monitoradas:");
        operacoes.imprimir(cancelas);

        // d2. imprimindo os registros
        System.out.println("Registros antes da ordenação:");
        operacoes.imprimir(registros);

        // d3. ordenando os veículos
        Map<Cancela, List<Veiculo>> registrosOrdenados = operacoes.ordenar(registros);
        
        // Imprimindo os registros de veículos ordenados
        System.out.println("\nRegistros de veículos ordenados:");
        operacoes.imprimir(registrosOrdenados);

        // d4. combinando veículos
        List<List<Veiculo>> combinacoes = operacoes.combinarVeiculos(registros);
        System.out.println("\nCombinações de veículos:");
        for (List<Veiculo> combinacao : combinacoes) {
            System.out.println("Combinação: " + combinacao);
        }
    }

    // Método para gerar placas aleatórias
    // A complexidade é O(1)
    private static String gerarPlacaAleatoria() {
        String prefixo = PREFIXOS[random.nextInt(PREFIXOS.length)];
        int numero = random.nextInt(10000); // Números de 0000 a 9999
        return prefixo + "-" + String.format("%04d", numero);
    }
}