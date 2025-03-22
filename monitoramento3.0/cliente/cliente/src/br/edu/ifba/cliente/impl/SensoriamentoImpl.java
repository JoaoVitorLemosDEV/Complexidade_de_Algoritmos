package br.edu.ifba.cliente.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import br.edu.ifba.cliente.cancela.Cancela;
import br.edu.ifba.cliente.modelo.Veiculo;
import br.edu.ifba.cliente.sensoriamento.Sensoriamento;

public class SensoriamentoImpl implements Sensoriamento<Veiculo> {

    private static final String[] PREFIXOS = {"ABC", "XYZ", "AKL", "DEF", "AHI"};


    // Complexidade O(N)
    @Override
    public List<Veiculo> gerar(int totalLeituras, List<Cancela> cancelas) {
        List<Veiculo> veiculos = new ArrayList<>();
        Random random = new Random();

        for (int i = 0; i < totalLeituras; i++) {
            
            String placa = gerarPlacaAleatoria(random);
            Veiculo veiculo = new Veiculo(placa);
            
            
            Cancela cancela = cancelas.get(random.nextInt(cancelas.size()));
            veiculo.setCancela(cancela); 

            veiculos.add(veiculo);
        }

        return veiculos;
    }

    // Complexidade O(1)
    public String gerarPlacaAleatoria(Random random) {
        String prefixo = PREFIXOS[random.nextInt(PREFIXOS.length)];
        int numero = random.nextInt(10000);  
        return prefixo + "-" + String.format("%04d", numero);
    }
}
