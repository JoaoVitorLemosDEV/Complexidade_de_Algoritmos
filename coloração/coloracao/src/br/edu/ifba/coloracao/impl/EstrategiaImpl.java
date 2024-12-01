package br.edu.ifba.coloracao.impl;

import java.util.Arrays;
import java.util.List;

import br.edu.ifba.coloracao.estrategia.Estrategia;

public class EstrategiaImpl implements Estrategia<int[], List<List<Integer>>> {

    private static final String[] CORES = new String[] {"branco","preto", "cinza", "azul", "amarelo", "laranja", "rosa", "verde", "magenta", "vermelho", "ciano", "roxo", "turquesa","bege"};

    @Override
    public int[] colorir(List<List<Integer>> mapa, int numeroDeRegioes) {
        
        int[] cores = new int[numeroDeRegioes];

        Arrays.fill(cores, -1);
        cores[0] = 0;
       
        for (int i = 0; i < numeroDeRegioes; i++) {
            boolean[] coresUsadas = new boolean[numeroDeRegioes];

            for (int vizinho: mapa.get(i)) {
                if (cores[vizinho] != -1) {
                    coresUsadas[cores[vizinho]] = true;
                }
            }

            for (int cor = 0; cor < numeroDeRegioes; cor++) {
                if (!coresUsadas[cor]) {
                    cores[i] = cor;

                    break;
                }
            }
        }

        return cores;
    }

    public String[] getCores(List<List<Integer>> mapa, int numeroDeRegioes){
        int[] coloracoes = colorir(mapa, numeroDeRegioes);

        String[] cores = new String[coloracoes.length];
        int cor = 0;
        for(int coloracao: coloracoes){
            if(coloracao >= 0){
                cores[cor] = CORES[coloracao];
            }
            else{
                cores[cor] = "nenhuma";
            }
            cor++;
        }
        return cores;
    }
    
}
