package br.edu.ifba.cliente.impl;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.security.InvalidKeyException;
import java.security.KeyFactory;
import java.security.NoSuchAlgorithmException;
import java.security.PublicKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.X509EncodedKeySpec;
import java.util.*;

import br.edu.ifba.cliente.cancela.Cancela;
import br.edu.ifba.cliente.modelo.Veiculo;
import br.edu.ifba.cliente.sensoriamento.Sensoriamento;
import br.edu.ifba.cliente.comunicacao.Cliente;
import com.fasterxml.jackson.databind.ObjectMapper;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;

public class ClienteImpl implements Cliente, Runnable {

    private static final int TOTAL_DE_LEITURAS = 5;
    private static final String URL_SERVIDOR = "http://localhost:8080/";
    private static final String URL_VEICULO = URL_SERVIDOR + "veiculo/";

    private static final String ALGORITMO_DE_ENCRIPTACAO = "RSA";
    private static final String CAMINHO_CHAVE_PUBLICA = "cliente/chave/publica.chv";

    private PublicKey chave = getChavePublica();

    private static final int TAMANHO_MAXIMO_HISTORICO = 50;

    private Sensoriamento<Veiculo> sensoriamento = null;

    private Queue<Veiculo> historicoDeLeituras = new LinkedList<>();

    public ClienteImpl() throws IOException, NoSuchAlgorithmException, InvalidKeySpecException {
    }

    @Override
    public void configurar(Sensoriamento<Veiculo> sensoriamento) {
        this.sensoriamento = sensoriamento;
    }

    private PublicKey getChavePublica() throws IOException, NoSuchAlgorithmException, InvalidKeySpecException {
        File arquivo = new File(CAMINHO_CHAVE_PUBLICA);
        FileInputStream stream = new FileInputStream(arquivo);
        byte[] bytes = stream.readAllBytes();
        stream.close();

        X509EncodedKeySpec spec = new X509EncodedKeySpec(bytes);
        KeyFactory kf = KeyFactory.getInstance(ALGORITMO_DE_ENCRIPTACAO);
        PublicKey chavePublica = kf.generatePublic(spec);

        return chavePublica;
    }

    private byte[] encriptar(String json) throws NoSuchAlgorithmException, NoSuchPaddingException,
            InvalidKeyException, InvalidKeySpecException, IOException, IllegalBlockSizeException, BadPaddingException {
        Cipher cipher = Cipher.getInstance(ALGORITMO_DE_ENCRIPTACAO);
        cipher.init(Cipher.ENCRYPT_MODE, chave);

        byte[] encriptado = cipher.doFinal(json.getBytes());

        return encriptado;
    }

    // Método de complexidade O(N^2)
    private List<List<Veiculo>> combinarVeiculos(List<Veiculo> veiculos) {
        List<List<Veiculo>> combinacoes = new ArrayList<>();

        for (int i = 0; i < veiculos.size(); i++) {
            for (int j = i + 1; j < veiculos.size(); j++) {
                List<Veiculo> combinacao = new ArrayList<>();
                combinacao.add(veiculos.get(i));
                combinacao.add(veiculos.get(j));
                combinacoes.add(combinacao);
            }
        }

        return combinacoes;
    }

    @Override
    public String enviar(Cancela cancela, Veiculo veiculo) throws Exception {
        Map<String, String> json = new HashMap<>();
        json.put("id", String.valueOf(veiculo.getId()));
        json.put("placa", veiculo.getPlaca());
        json.put("cancelaId", cancela.getIdentificacao());

        ObjectMapper mapeador = new ObjectMapper();
        URL url = new URL(
                URL_VEICULO
                        + new String(Base64.getUrlEncoder().encode(encriptar(mapeador.writeValueAsString(json)))));
        // acessa a url para enviar a biometria
        HttpURLConnection conexao = (HttpURLConnection) url.openConnection();
        conexao.setRequestMethod("GET");
        if (conexao.getResponseCode() != 200) {
            throw new RuntimeException("erro de conexão, código de resposta = "
                    + conexao.getResponseCode());
        }
        // recebe resposta do servidor
        InputStreamReader in = new InputStreamReader(conexao.getInputStream());
        BufferedReader br = new BufferedReader(in);
        String resposta = br.readLine();
        // desconecta do servidor
        conexao.disconnect();

        return resposta;
    }

    // Adiciona um novo veículo ao histórico e mantém o limite de 50 registros
    // Complexidade O(1)
    private void adicionarHistorico(Veiculo veiculo) {
        if (historicoDeLeituras.size() >= TAMANHO_MAXIMO_HISTORICO) {
            historicoDeLeituras.poll();  // Remove o veículo mais antigo
        }
        historicoDeLeituras.offer(veiculo);  // Adiciona o novo veículo
    }

    @Override
    public void run() {
        // 10 cancelas
        // Complexidade O(N)
        List<Cancela> cancelas = new ArrayList<>();
        for (int i = 1; i <= 10; i++) {
            cancelas.add(new Cancela(String.valueOf(i)));
        }

        // Gerando veículos com as 10 cancelas e associando cada veículo a uma cancela
        List<Veiculo> veiculos = sensoriamento.gerar(TOTAL_DE_LEITURAS, cancelas);

        // Atribuindo cada cancela ao respectivo veículo
        // Complexidade O(N)
        for (int i = 0; i < veiculos.size(); i++) {
            veiculos.get(i).setCancela(cancelas.get(i % cancelas.size()));  // Associa uma cancela a cada veículo
        }

        // Adicionando os veículos ao histórico
        // Complexidade O(N)
        for (Veiculo veiculo : veiculos) {
            adicionarHistorico(veiculo);
        }

        // Combinando os veículos no histórico
        List<List<Veiculo>> combinacoes = combinarVeiculos(new ArrayList<>(historicoDeLeituras));

        // Enviar para o servidor
        // Complexidade O(N^2)
        for (List<Veiculo> combinacao : combinacoes) {
            for (Veiculo veiculo : combinacao) {
                if (veiculo.getPlaca().startsWith("A")) {  // Filtro de placa, apenas placas que começam com "A"
                    try {
                        String resposta = enviar(veiculo.getCancela(), veiculo);  // Envia os dados
                        System.out.println(resposta.equals("ok")
                                ? "Leitura enviada do veículo: " + veiculo + " pela cancela " + veiculo.getCancela()
                                : "Falha no envio da leitura");
                        Thread.sleep(500);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                } else {
                    System.out.println("Leitura ignorada, placa não começa com A");
                }
            }
        }
    }
}
