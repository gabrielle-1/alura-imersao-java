package aula01;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class JsonParser {

    String regexOne = ".*\\[(.+)\\].*";
    String regexTwo = "\"(.+?)\":\"(.*?)\"";
    Pattern REGEX_ITEMS = Pattern.compile(regexOne);
    Pattern REGEX_ATRIBUTOS_JSON = Pattern.compile(regexTwo);

    public List<Map<String, String>> parse(String json) throws IllegalAccessException {
        Matcher matcher = REGEX_ITEMS.matcher(json);
        if(!matcher.find()){
            throw new IllegalAccessException("Os itens não foram encontrados!");
        }

        String[] items = matcher.group(1).split("\\},\\{");
        List<Map<String, String>> dados = new ArrayList<>();

        for(String item : items){
            Map<String, String> atributosItem = new HashMap<>();

            Matcher matcherAtributosJson = REGEX_ATRIBUTOS_JSON.matcher(item);
            while (matcherAtributosJson.find()){
                String atributo = matcherAtributosJson.group(1);
                String valor = matcherAtributosJson.group(2);
                atributosItem.put(atributo, valor);
            }

            dados.add(atributosItem);
        }

        return dados;
    }

}
