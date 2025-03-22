package br.edu.ifba.cliente.comunicacao;

import br.edu.ifba.cliente.cancela.Cancela;
import br.edu.ifba.cliente.modelo.Veiculo;
import br.edu.ifba.cliente.sensoriamento.Sensoriamento;

public interface Cliente {
    void configurar(Sensoriamento<Veiculo> sensoriamento);  

    String enviar(Cancela cancela, Veiculo veiculo) throws Exception; 
}
