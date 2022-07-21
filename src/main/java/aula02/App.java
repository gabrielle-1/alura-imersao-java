package aula02;


import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.List;

public class App {
    public static void main(String[] args) throws IOException, InterruptedException, IllegalAccessException {


        //NASA
        String url = "https://raw.githubusercontent.com/alura-cursos/imersao-java/api/NASA-APOD.json";
        ExtratorConteudoNasa extrator = new ExtratorConteudoNasa();

        //IMDB
        //String url = "https://imdb-api.com/en/API/Top250Movies/k_0ojt0yvm";
        //ExtratorConteudoIMDB extrator = new ExtratorConteudoIMDB();

        // fazer uma conexão HTTP e buscar os top 250 filmes -> recebe uma url e devolve um body String
        var http = new ClienteHttp();
        String json = http.buscaDados(url);

        // extrair só os dados que interessam (titulo, poster, classificação)
        var extratorConteudoNasa = new ExtratorConteudoNasa();
        List<Conteudo> conteudos = extratorConteudoNasa.extraiConteudo(json);

        //exibir e manipular os dados
        var geradoraDeFigurinhas = new GeradorDeFigurinhas();

        for(Conteudo conteudo : conteudos){
            String urlImagem =
                    //conteudo.get("image")
                    conteudo.getUrlImagem();

            String titulo = conteudo.getTitulo();

            InputStream inputStream = new URL(conteudo.getUrlImagem()).openStream();
            String nomeArquivo =  titulo + ".png";
            geradoraDeFigurinhas.cria(inputStream, nomeArquivo);
            System.out.printf(titulo);

        }

    }


}
