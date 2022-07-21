package aula01;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class App {
    public static void main(String[] args){

        Scanner scan = new Scanner(System.in);
        List<Map<String, String>> listaFilmes = buscarListaFilmes();

        exibirListaFilmes(listaFilmes);

        System.out.println("Gostaria de alterar a avaliação de algum filme?");
        int resposta = scan.nextInt();
        String avaliacao="", titulo = "";

        if(resposta == 1){

            scan.nextLine();//limpando o buffer
            System.out.print("Digite o título do filme:");
            titulo = scan.nextLine();
            System.out.print("Digite a nova avaliação do filme " + titulo + ":");
            avaliacao = scan.next();

            Map<String, String> filme = buscarFilme(titulo, listaFilmes);
            alterarAvaliacaoFilme(filme, avaliacao);

        }

    }

    public static List<Map<String, String>> buscarListaFilmes(){

        List<Map<String, String>> listaFilmes = null;

        //Fazer uma conexão HTTP e buscar os filmes
        String url = "https://raw.githubusercontent.com/alura-cursos/imersao-java/api/TopMovies.json";
        URI uri = URI.create(url);

        var client = HttpClient.newHttpClient();
        var request = HttpRequest.newBuilder(uri).GET().build();
        HttpResponse<String> response = null;
        try {
            response = client.send(request, HttpResponse.BodyHandlers.ofString());
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        String body = response.body();

        //Extrair os dados(parser) que vamos utilizar(título,imagem e classificação)
        var parser = new JsonParser();
        listaFilmes = parser.parse(body);

        return listaFilmes;
    }

    public static void exibirListaFilmes(List<Map<String, String>> listaFilmes){
        //Manipular e exibir os dados
        for(Map<String, String> filme : listaFilmes){
            System.out.println("\n\nFilme top \u001b[104m" + filme.get("rank") + "\u001b[m:");
            System.out.println("\u001b[1m " + filme.get("title") + " \u001b[m");
            System.out.println(filme.get("image") + "");

            String classificacao = (filme.get("imDbRating"));
            double classificacaoDouble = Double.parseDouble(classificacao);
            int classificacaoInteiro=(int)classificacaoDouble;

            for(int i=0; i<classificacaoInteiro; i++){
                System.out.print("\u001b[33m★\u001b[m");
            }
        }
    }

    private static Map<String, String> buscarFilme(String titulo, List<Map<String, String>> listaFilmes){

        Map<String, String> retorno = null;

        for(Map<String, String> filme : listaFilmes){

            if(filme.get("title").equalsIgnoreCase(titulo)){
                retorno =  filme;
            }
        }
        return retorno;
    }

    public static void alterarAvaliacaoFilme(Map<String, String> filme, String avaliacao){
        filme.put("imDbRating", avaliacao);
        System.out.println(filme);
    }

}
