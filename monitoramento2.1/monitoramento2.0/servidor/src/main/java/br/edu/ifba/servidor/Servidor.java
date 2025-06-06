package br.edu.ifba.servidor;

import org.glassfish.grizzly.http.server.HttpServer;
import org.glassfish.jersey.grizzly2.httpserver.GrizzlyHttpServerFactory;
import org.glassfish.jersey.server.ResourceConfig;

import java.io.IOException;
import java.net.URI;

public class Servidor {
    public static final String BASE_URI = "http://localhost:8080/";

    public static HttpServer startServer() {
        // Atualizando o pacote de recursos para o pacote correto de Veiculo e Cancela
        final ResourceConfig rc = new ResourceConfig().packages("br.edu.ifba.servidor");

        // Criando o servidor HTTP
        return GrizzlyHttpServerFactory.createHttpServer(URI.create(BASE_URI), rc);
    }

    public static void main(String[] args) throws IOException {
        final HttpServer server = startServer();
        System.out.println("Aguardando dados de veículos e cancelas...");
        System.in.read();
        server.shutdownNow();
    }
}
