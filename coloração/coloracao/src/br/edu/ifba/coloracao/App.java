package br.edu.ifba.coloracao;

import java.util.ArrayList;
import java.util.List;

import br.edu.ifba.coloracao.estrategia.Estrategia;
import br.edu.ifba.coloracao.impl.EstrategiaImpl;

public class App {

    enum Paises {
        BRASIL(0, "Brasil"),
        ARGENTINA(1, "Argentina"),
        URUGUAI(2, "Uruguai"),
        PARAGUAI(3, "Paraguai"),
        COLOMBIA(4, "Colômbia"),
        PERU(5, "Peru"),
        BOLIVIA(6, "Bolívia"),
        CHILE(7, "Chile"),
        EQUADOR(8, "Equador"),
        VENEZUELA(9, "Venezuela"),
        GUIANA(10, "Guiana"),
        GUIANA_FRANCESA(11, "Guiana Francesa"),
        SURINAME(12, "Suriname");
        

        public int vertice;
        public String nome;

        private Paises(int vertice, String nome){
            this.vertice = vertice;
            this.nome = nome;
        }
    }

    private static final int TOTAL_DE_PAISES = 13;
    private static List<List<Integer>> mapa = new ArrayList<List<Integer>>();
    
    private static final void conectarPaises(Paises pais, Paises outroPais){
        mapa.get(pais.vertice).add(outroPais.vertice);
        mapa.get(outroPais.vertice).add(pais.vertice);

    }
    
    private static final void iniciarMapa(){
        for(int i = 0;i< TOTAL_DE_PAISES; i++ ){
            mapa.add(new ArrayList<Integer>());
        }
    }
    public static void main(String[] args) throws Exception {
        iniciarMapa();

        conectarPaises(Paises.BRASIL, Paises.GUIANA_FRANCESA);
        conectarPaises(Paises.BRASIL, Paises.SURINAME);
        conectarPaises(Paises.BRASIL, Paises.GUIANA);
        conectarPaises(Paises.BRASIL, Paises.VENEZUELA);
        conectarPaises(Paises.BRASIL, Paises.COLOMBIA);
        conectarPaises(Paises.BRASIL, Paises.PERU);
        conectarPaises(Paises.BRASIL, Paises.BOLIVIA);
        conectarPaises(Paises.BRASIL, Paises.URUGUAI);
        conectarPaises(Paises.BRASIL, Paises.PARAGUAI);
        conectarPaises(Paises.BRASIL, Paises.ARGENTINA);
        conectarPaises(Paises.BOLIVIA, Paises.PARAGUAI);
        conectarPaises(Paises.COLOMBIA, Paises.EQUADOR);
        conectarPaises(Paises.COLOMBIA, Paises.VENEZUELA);
        conectarPaises(Paises.COLOMBIA, Paises.PERU);
        conectarPaises(Paises.EQUADOR, Paises.PERU);
        conectarPaises(Paises.PERU, Paises.BOLIVIA);
        conectarPaises(Paises.PERU, Paises.CHILE);
        conectarPaises(Paises.CHILE, Paises.BOLIVIA);
        conectarPaises(Paises.CHILE, Paises.ARGENTINA);        
        conectarPaises(Paises.ARGENTINA, Paises.BOLIVIA);
        conectarPaises(Paises.ARGENTINA, Paises.PARAGUAI);        
        conectarPaises(Paises.ARGENTINA, Paises.URUGUAI);        
        conectarPaises(Paises.SURINAME, Paises.GUIANA);
        conectarPaises(Paises.SURINAME, Paises.GUIANA_FRANCESA);
        conectarPaises(Paises.GUIANA, Paises.VENEZUELA);

        EstrategiaImpl estrategia = new EstrategiaImpl();
        String[] cores = estrategia.getCores(mapa, TOTAL_DE_PAISES);
        for (int i=0; i<cores.length;i++){
            System.out.println("Cor do país " + Paises.values()[i].nome + ": " + cores[i]);
        }
    }
}
