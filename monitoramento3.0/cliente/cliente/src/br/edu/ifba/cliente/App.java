package br.edu.ifba.cliente;

import java.util.List;
import java.util.ArrayList;
import br.edu.ifba.cliente.impl.ClienteImpl;
import br.edu.ifba.cliente.impl.SensoriamentoImpl;
import br.edu.ifba.cliente.cancela.Cancela;

public class App {
    private static final int TOTAL_DE_VEICULOS = 10;

    public static void main(String[] args) throws Exception {
        List<Thread> processos = new ArrayList<>();


        // Complexidade O(N)
        // Criando os veículos e configurando o cliente
        for (int i = 0; i < TOTAL_DE_VEICULOS; i++) {
            // Criando o objeto SensoriamentoImpl para gerar placas
            SensoriamentoImpl sensoriamento = new SensoriamentoImpl();
            // Criando um cliente e configurando-o
            ClienteImpl cliente = new ClienteImpl();
            cliente.configurar(sensoriamento);  // Chamando configurar corretamente

            // Criando uma thread para o cliente e associando à lista
            Thread processo = new Thread(cliente);
            processos.add(processo);
            processo.start();
        }

        System.out.println("Enviando informações dos veículos para o servidor...");

        // Complexidade O(N)
        // Aguardando a finalização de todos os processos
        for (Thread processo : processos) {
            processo.join();
        }

        System.out.println("Informações dos veículos enviadas, finalizando a execução.");
    }
}
