package br.edu.ifba.servidor.operacoes;

import java.util.List;
import java.util.Map;
import br.edu.ifba.servidor.modelo.Cancela;
import br.edu.ifba.servidor.modelo.Veiculo;

public interface Operacoes<Monitorado, Sensor> {

    public void gravarPlaca(Monitorado monitorado, Sensor sensor);

    public Map<Cancela, List<Veiculo>> recuperarRegistros();

    boolean combinarVeiculos();
}