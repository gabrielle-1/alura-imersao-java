package aula02;

import aula01.JsonParser;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class ExtratorConteudoNasa implements ExtratorConteudo{

    public List<Conteudo> extraiConteudo(String json){

        var parser = new JsonParser();
        List<Conteudo> conteudos = new ArrayList<>();
        List<Map<String, String>> listaDeAtributos = parser.parse(json);

        //popular a lista de conteúdos
        for(Map<String, String> atributo : listaDeAtributos){
            String titulo = atributo.get("title");
            String urlImagem = atributo.get("url");

            var conteudo = new Conteudo(titulo, urlImagem);

            conteudos.add(conteudo);
        }
        return conteudos;
    }
}
