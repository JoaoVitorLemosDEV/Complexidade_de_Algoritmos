package br.edu.ifba.cliente.sensoriamento;

import java.util.List;

import br.edu.ifba.cliente.cancela.Cancela;

public interface Sensoriamento<Sensor> {
    List<Sensor> gerar(int totalLeituras, List<Cancela> cancelas); 
}
