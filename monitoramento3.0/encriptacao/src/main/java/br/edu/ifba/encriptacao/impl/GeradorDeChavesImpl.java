package br.edu.ifba.encriptacao.impl;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;

import br.edu.ifba.encriptacao.aleatoriedade.GeradorDeAleatoriedadeReal;
import br.edu.ifba.encriptacao.chaves.GeradorDeChaves;
import br.edu.ifba.encriptacao.excecoes.FalhaGeracaoDeChaves;

public class GeradorDeChavesImpl implements GeradorDeChaves<GeradorDeAleatoriedadeReal> {

    private static int TAMANHO_CHAVES_ENCRIPTACAO = 2048;

    private GeradorDeAleatoriedadeReal geradorDeAleatoriedade = null;
    private String algoritmoDeEncriptacao = null;

    @Override
    public void inicializar(GeradorDeAleatoriedadeReal geradorDeAleatoriedade, String algoritmoDeEncriptacao) {
        this.geradorDeAleatoriedade = geradorDeAleatoriedade;
        this.algoritmoDeEncriptacao = algoritmoDeEncriptacao;
    }

    @Override
    public KeyPair gerarChaves() throws FalhaGeracaoDeChaves {
        KeyPair chaves = null;

        try {
            KeyPairGenerator geradorDePares = KeyPairGenerator.getInstance(algoritmoDeEncriptacao);
            geradorDePares.initialize(TAMANHO_CHAVES_ENCRIPTACAO, geradorDeAleatoriedade);

            chaves = geradorDePares.generateKeyPair();
        } catch (NoSuchAlgorithmException e) {
            throw new FalhaGeracaoDeChaves("falha gerando chaves: " + e.getMessage());
        }

        return chaves;
    }

    @Override
    public void gerarChaves(String arquivoChavePrivada, String arquivoChavePublica) throws FalhaGeracaoDeChaves {
        KeyPair chaves = gerarChaves();

        byte[] bytes = chaves.getPublic().getEncoded();
        gravar(arquivoChavePublica, bytes);

        bytes = chaves.getPrivate().getEncoded();
        gravar(arquivoChavePrivada, bytes);
    }

    private void gravar(String arquivo, byte[] bytes) throws FalhaGeracaoDeChaves {
        FileOutputStream stream;
        try {
            File f = new File(arquivo);
            if (f.exists()) {
                f.delete();
            }

            stream = new FileOutputStream(f);
            stream.write(bytes);
            stream.close();
        } catch (FileNotFoundException e) {
            throw new FalhaGeracaoDeChaves("falha gerando chaves: " + e.getMessage());
        } catch (IOException e) {
            throw new FalhaGeracaoDeChaves("falha gerando chaves: " + e.getMessage());
        }
    }

    @Override
    public void finalizar() throws FalhaGeracaoDeChaves {
        geradorDeAleatoriedade.finalizar();
    }

}

