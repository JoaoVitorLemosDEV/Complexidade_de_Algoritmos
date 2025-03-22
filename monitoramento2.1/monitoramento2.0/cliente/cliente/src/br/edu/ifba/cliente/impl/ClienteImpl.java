package br.edu.ifba.cliente.impl;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;

import br.edu.ifba.cliente.cancela.Cancela;
import br.edu.ifba.cliente.modelo.Veiculo;
import br.edu.ifba.cliente.sensoriamento.Sensoriamento;
import br.edu.ifba.cliente.comunicacao.Cliente;

public class ClienteImpl implements Cliente, Runnable {

    private static final int TOTAL_DE_LEITURAS = 5;
    private static final String URL_SERVIDOR = "http://localhost:8080/";
    private static final String URL_VEICULO = URL_SERVIDOR + "veiculo/";

    private static final int TAMANHO_MAXIMO_HISTORICO = 50;

    private Sensoriamento<Veiculo> sensoriamento = null;

    private Queue<Veiculo> historicoDeLeituras = new LinkedList<>();

    @Override
    public void configurar(Sensoriamento<Veiculo> sensoriamento) {
        this.sensoriamento = sensoriamento;
    }

    // Método para combinar veículos 
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
        URI uri = new URI(URL_VEICULO + veiculo.getId() + "/"
                + URLEncoder.encode(veiculo.getPlaca(), StandardCharsets.UTF_8.toString())
                + "/" + cancela.getIdentificacao());

        URL url = uri.toURL();
        HttpURLConnection conexao = (HttpURLConnection) url.openConnection();
        conexao.setRequestMethod("GET");

        if (conexao.getResponseCode() != 200) {
            throw new Exception("Servidor de veículos não encontrado");
        }

        InputStreamReader in = new InputStreamReader(conexao.getInputStream());
        BufferedReader br = new BufferedReader(in);
        String resposta = br.readLine();

        conexao.disconnect();
        return resposta;
    }

    // Adiciona um novo veículo ao histórico e mantém o limite de 50 registros
    private void adicionarHistorico(Veiculo veiculo) {
        if (historicoDeLeituras.size() >= TAMANHO_MAXIMO_HISTORICO) {
            historicoDeLeituras.poll();  // Remove o veículo mais antigo
        }
        historicoDeLeituras.offer(veiculo);  // Adiciona o novo veículo
    }

    @Override
    public void run() {
    // 10 cancelas
    List<Cancela> cancelas = new ArrayList<>();
    for (int i = 1; i <= 10; i++) {
        cancelas.add(new Cancela(String.valueOf(i)));
    }

    // Gerando veículos com as 10 cancelas e associando cada veículo a uma cancela
    List<Veiculo> veiculos = sensoriamento.gerar(TOTAL_DE_LEITURAS, cancelas);

    // Atribuindo cada cancela ao respectivo veículo
    for (int i = 0; i < veiculos.size(); i++) {
        veiculos.get(i).setCancela(cancelas.get(i % cancelas.size()));  // Associa uma cancela a cada veículo
    }

    // Adicionando os veículos ao histórico
    for (Veiculo veiculo : veiculos) {
        adicionarHistorico(veiculo);
    }

    // Combinando os veículos no histórico
    List<List<Veiculo>> combinacoes = combinarVeiculos(new ArrayList<>(historicoDeLeituras));

    // Enviar para o servidor 
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
