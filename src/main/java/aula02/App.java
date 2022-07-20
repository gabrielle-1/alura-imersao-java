package aula02;

import aula01.JsonParser;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URL;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.List;
import java.util.Map;

public class App {
    public static void main(String[] args){

        // fazer uma conexão HTTP e buscar os top 250 filmes
        //String url = "https://imdb-api.com/en/API/Top250Movies/k_0ojt0yvm";
        String url = "https://raw.githubusercontent.com/alura-cursos/imersao-java/api/TopMovies.json";
        URI endereco = URI.create(url);
        var client = HttpClient.newHttpClient();
        var request = HttpRequest.newBuilder(endereco).GET().build();
        HttpResponse<String> response = null;
        try {
            response = client.send(request, HttpResponse.BodyHandlers.ofString());
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        String body = response.body();

        // extrair só os dados que interessam (titulo, poster, classificação)
        var parser = new JsonParser();
        try {
            List<Map<String, String>> listaDeFilmes = parser.parse(body);

            //exibir e manipular os dados
            var geradoraDeFigurinhas = new GeradorDeFigurinhas();
            for(Map<String, String> filme : listaDeFilmes){
                String urlImagem = filme.get("image");
                String titulo = filme.get("title");

                InputStream inputStream = new URL(urlImagem).openStream();
                String nomeArquivo = titulo + ".png";
                geradoraDeFigurinhas.cria(inputStream, nomeArquivo);
                System.out.printf(titulo);

            }

        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        } catch (MalformedURLException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }


}
