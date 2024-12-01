package br.edu.ifba.caminho.estrategia;

public interface Estrategia <Origem, Mapa, Distancias> {
    
    public Distancias encontrarMenorCaminho(Mapa mapa, Origem origem);
}
