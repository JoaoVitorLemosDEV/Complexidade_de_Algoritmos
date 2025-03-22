package br.edu.ifba.servidor;

import br.edu.ifba.servidor.impl.OperacoesImpl;
import br.edu.ifba.servidor.modelo.Cancela;
import br.edu.ifba.servidor.modelo.Veiculo;
import br.edu.ifba.servidor.operacoes.Operacoes;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@Path("veiculo")
public class Rotas {

    Map<Cancela, List<Veiculo>> registros = new LinkedHashMap<>();


    // singleton => padrão de instancia única
    private static Operacoes<Veiculo, Cancela> operacoes = new OperacoesImpl();

    public static Operacoes<Veiculo, Cancela> getOperacoes() {
        if (operacoes == null) {
            operacoes = new OperacoesImpl();  // Implementação das operações para veículos e cancelas
        }

        return operacoes;
    }

    @GET
    @Path("informacoes")
    @Produces(MediaType.TEXT_PLAIN)
    public String getInformacoes() {
        return "Serviço que processa informações de veículos e cancelas, versão 1.0";
    }

    @GET
    @Path("{id}/{placa}/{cancelaId}")
    @Produces(MediaType.TEXT_PLAIN)
    public String gravarPlaca(
            @PathParam("id") String id,
            @PathParam("placa") String placa,
            @PathParam("cancelaId") String cancelaId) {

        System.out.println("ID: " + id + ", Placa: " + placa + ", Cancela: " + cancelaId);
        Cancela cancela = new Cancela(cancelaId);
        Veiculo veiculo = new Veiculo(cancelaId, placa);
        veiculo.setCancela(cancela);
        operacoes.gravarPlaca(veiculo, cancela);

        return "ok";
    }

    @GET
    @Path("combinar")
    @Produces(MediaType.TEXT_PLAIN)
    public void combinacoes(List<List<Veiculo>> combinacoes) {
        System.out.println(operacoes.combinarVeiculos());
    }
}