package br.edu.ifba.servidor.impl;

import br.edu.ifba.servidor.modelo.Cancela;
import br.edu.ifba.servidor.modelo.Veiculo;
import br.edu.ifba.servidor.operacoes.Operacoes;

import java.util.*;

public class OperacoesImpl implements Operacoes<Veiculo, Cancela> {

    private Map<Cancela, List<Veiculo>> registros = new TreeMap<>();

    @Override
    public void gravarPlaca(Veiculo veiculo, Cancela cancela) {
        // Lógica para gravar a associação entre o veículo e a cancela no sistema
        List<Veiculo> veiculos = registros.getOrDefault(cancela, new ArrayList<>());
        veiculos.add(veiculo);
        registros.put(cancela, veiculos);
    }


    // Método para recuperar os registros reais
    public Map<Cancela, List<Veiculo>> recuperarRegistros() {
        return registros;
    }


    //tem q mudar aqui pra 2 for ou meter o maior migue do mundo
    @Override
    public boolean combinarVeiculos() {
        List<List<Veiculo>> combinacoes = new ArrayList<>();

        for (List<Veiculo> listaVeiculos : registros.values()) {
            for (int i = 0, n = listaVeiculos.size(); i < n; i++) {
                for (int j = i + 1; j < n; j++) {
                    combinacoes.add(Arrays.asList(listaVeiculos.get(i), listaVeiculos.get(j)));
                }
            }
        }

        System.out.println(combinacoes);

        return false;
    }


}
