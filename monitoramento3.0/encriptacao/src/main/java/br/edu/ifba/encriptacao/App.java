package br.edu.ifba.encriptacao;

import java.security.KeyPair;

import br.edu.ifba.encriptacao.aleatoriedade.GeradorDeAleatoriedadeReal;
import br.edu.ifba.encriptacao.chaves.GeradorDeChaves;
import br.edu.ifba.encriptacao.encriptador.Encriptador;
import br.edu.ifba.encriptacao.impl.EncriptadorImpl;
import br.edu.ifba.encriptacao.impl.GeradorDeChavesImpl;

public class App {

    private static final String CAMINHO_DO_VIDEO = "video/Peixe.mp4";
    private static final String ALGORITMO_DE_ENCRIPTACAO = "RSA";

    private static final String CAMINHO_CHAVE_PRIVADA = "../servidor/chave/privada.chv";
    private static final String CAMINHO_CHAVE_PUBLICA = "../cliente/cliente/chave/publica.chv";

    public static void main(String[] args) throws Exception {
        GeradorDeChaves<GeradorDeAleatoriedadeReal> geradorDeChaves = new GeradorDeChavesImpl();
        geradorDeChaves.inicializar(new GeradorDeAleatoriedadeReal(CAMINHO_DO_VIDEO), ALGORITMO_DE_ENCRIPTACAO);

        for (int i = 0; i < 10; i++) {
            System.out.println("******* gerando um novo par de chaves #" + (i + 1) + " *******");
            KeyPair chaves = geradorDeChaves.gerarChaves();

            Encriptador encriptador = new EncriptadorImpl(chaves, ALGORITMO_DE_ENCRIPTACAO);
            String encriptado = encriptador.encriptar("complexidade de algoritmos");
            System.out.println("encriptado: " + encriptado);

            String desencriptado = encriptador.desencriptar(encriptado);
            System.out.println("desencriptado: " + desencriptado);
        }

        geradorDeChaves.gerarChaves(CAMINHO_CHAVE_PRIVADA, CAMINHO_CHAVE_PUBLICA);
        geradorDeChaves.finalizar();
    }
}

