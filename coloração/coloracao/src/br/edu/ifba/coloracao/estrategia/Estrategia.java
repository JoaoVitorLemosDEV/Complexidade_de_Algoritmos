package br.edu.ifba.coloracao.estrategia;

public interface Estrategia<Cores, Mapa> {

    public Cores colorir(Mapa mapa, int numeroDeRegioes);
}
