package br.edu.ifba.caminho;

import br.edu.ifba.caminho.estrategia.Estrategia;
import br.edu.ifba.caminho.impl.Dijkstra;

public class App {

    private static final int TOTAL_DE_CIDADES = 6;
    public static void main(String[] args) throws Exception {
        int[][] mapa = new int[TOTAL_DE_CIDADES][TOTAL_DE_CIDADES];
        mapa[0][1] = 2;
        mapa[0][2] = 4;
        mapa[1][2] = 1;
        mapa[1][3] = 7;
        mapa[2][3] = 3;
        mapa[2][4] = 2;
        mapa[3][4] = 5;
        mapa[4][5] = 3;

        Integer origem = 0;

        Estrategia<Integer, int[][], int[]> estrategia = new Dijkstra();
        int[] distancias = estrategia.encontrarMenorCaminho(mapa, origem);
        System.out.println("Distâncias entre a cidade " + origem + " e: ");
        for(int i = 0; i<distancias.length; i++){
            System.out.println(i + " = " + distancias[i]);
        }
    }
}
