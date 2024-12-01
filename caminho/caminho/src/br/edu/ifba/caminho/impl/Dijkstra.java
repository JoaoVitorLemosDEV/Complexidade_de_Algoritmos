package br.edu.ifba.caminho.impl;

import java.util.Arrays;
import java.util.PriorityQueue;

import br.edu.ifba.caminho.estrategia.Estrategia;

public class Dijkstra implements Estrategia<Integer, int[][], int[]>  {

    @Override
    public int[] encontrarMenorCaminho(int[][] mapa, Integer origem) {
        int[] distancias = new int[mapa.length];
        Arrays.fill(distancias, Integer.MAX_VALUE);

        distancias[origem] = 0;

        PriorityQueue<Integer> queue = new PriorityQueue<>((a, b) -> distancias[a] - distancias[b]);
        queue.offer(origem);

        while (!queue.isEmpty()) {
            int estrada = queue.poll();

            for (int i = 0; i < mapa.length; i++) {
                if (mapa[estrada][i] != 0) {
                    int distanciaAcumulada = distancias[estrada] + mapa[estrada][i];
                    if (distanciaAcumulada < distancias[i]) {
                        distancias[i] = distanciaAcumulada;
                        queue.offer(i);
                    }
                }
            }
        }

        return distancias;
    }
    
}
