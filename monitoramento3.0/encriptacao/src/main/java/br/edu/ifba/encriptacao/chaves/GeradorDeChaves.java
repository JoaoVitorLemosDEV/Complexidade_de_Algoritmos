package br.edu.ifba.encriptacao.chaves;

import java.security.KeyPair;
import java.security.SecureRandom;

import br.edu.ifba.encriptacao.excecoes.FalhaGeracaoDeChaves;

public interface GeradorDeChaves<GeradorDeAleatoriedade extends SecureRandom> {

    public void inicializar(GeradorDeAleatoriedade geradorDeAleatoriedade, String algoritmoDeEncriptacao);

    public KeyPair gerarChaves() throws FalhaGeracaoDeChaves;

    public void gerarChaves(String arquivoChavePrivada, String arquivoChavePublica) throws FalhaGeracaoDeChaves;

    public void finalizar() throws FalhaGeracaoDeChaves;

}

