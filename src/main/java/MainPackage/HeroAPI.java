package MainPackage;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.*;
import java.net.URL;
import java.util.Scanner;

public class HeroAPI {
    public static void main(String args[]) throws IOException {//This function is about saving the api results to a txt file
        HeroAPI hero = new HeroAPI();
        File file = new File("src/main/resources/hero_names.txt");
        Scanner s = new Scanner(file);
        URL url = new URL("https://api.opendota.com/api/heroes");
        JsonNode json = hero.getJson(url);
        FileWriter writer = new FileWriter(file);
        for(int x = 0; x < json.size(); x++) {
            String str = json.get(x).get("id") + ":" + json.get(x).get("localized_name").asText(); //something like 1:Anti-Mage
            writer.write(str);
            if(x != json.size() -1) {
                writer.write("\n");
            }
        }
        writer.close();
        System.out.println(json.get(0).get("id"));//This is to ONLY to test if it works

    }
    public JsonNode getJson(URL url) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        return mapper.readTree(url);
    }
}
