package MainPackage;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.dv8tion.jda.api.interactions.commands.OptionMapping;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import java.util.ArrayList;
import java.util.List;

public class SlashCommands extends ListenerAdapter {
    @Override
    public void onSlashCommandInteraction(SlashCommandInteractionEvent event) {
        if(event.getName().equals("fart")) {
            String x = "you just farted";
            event.deferReply().queue();
            event.getHook().sendMessage(x).queue(); //setEphemeral is to only send command only to you to prevent discord clogging messages
        }
        if(event.getName().equals("match")) {
            event.deferReply().queue();
            OptionMapping option = event.getOption("gameid");
            if(option == null) {//Just in case something breaks,
                event.reply("ID was not provided").queue();
                return;
            }

            try {// test out the url endpoint
                String gameid = Long.toString(option.getAsLong());
                URL url = new URL("https://api.opendota.com/api/matches/" + gameid);
                if(checkForStatusCode(url) != 200) {//validation
                    throw new IOException();
                }
                JsonNode json = getJson(url);
                EmbedBuilder eb = builder(json);
                event.getHook().sendMessageEmbeds(eb.build())
                        .queue();
            }
            catch(MalformedURLException ex) {// add more catches to find specific errors
                event.getHook().sendMessage("Invalid API").queue();
                return;
            }
            catch(IOException ex) {
                event.getHook().sendMessage("Cannot convert to json or 404").queue();
                return;
            }
            catch(RuntimeException ex) {
                event.getHook().sendMessage("Something is wrong with the request").queue();
                return;
            }
        }
        if(event.getName().equals("testpic")) {
            event.deferReply().queue();
            event.getHook().sendMessage("hello world").queue();
        }

    }
    public EmbedBuilder builder(JsonNode json) {// LOOK up embeds
        EmbedBuilder eb = new EmbedBuilder();
        eb.setTitle("Dota 2 Match: " + json.get("match_id"));// this will show the game id// Example is [radiant victory](LINK)
        eb.setDescription(getVictor(json) + " Victory");//Find the winner
        List<String> radiant = matchInfo(json, 0);
        List<String> dire = matchInfo(json, 1);
        eb.addField("Radiant", radiant.get(0), true);//heroes
        eb.addField("Kills", radiant.get(1), true);
        eb.addField("Deaths", radiant.get(2), true);
        eb.addField("Assist", radiant.get(3), true);
        eb.addField("Net worth",radiant.get(4), true);
        eb.addField("Dmg Dealt",radiant.get(5), true);
        eb.addField("Player",radiant.get(6),true);

        eb.addField("VS","", false);

        eb.addField("Dire", dire.get(0), true);//heroes
        eb.addField("Kills",dire.get(1), true);
        eb.addField("Deaths", dire.get(2), true);
        eb.addField("Assist", dire.get(3), true);
        eb.addField("Net worth",dire.get(4), true);
        eb.addField("Dmg Dealt",dire.get(5), true);
        eb.addField("Player",dire.get(6),true);
        eb.setImage("https://liquipedia.net/commons/images/8/85/Lifestealer_Large.png");
        return eb;
    }
    public String getVictor(JsonNode json) {
        return json.get("radiant_win").asBoolean() ? "Radiant": "Dire";
    }
    public String heroes(JsonNode json) {//TODO find a way to get hero names with just the id

        return "";
    }
    //File starts at main
    public List<String> matchInfo(JsonNode json, int rd) {//rd is radiant or dire
        JsonNode jn = json.get("players");
        StringBuilder heroes = new StringBuilder();//0
        StringBuilder kills = new StringBuilder();
        StringBuilder deaths = new StringBuilder();
        StringBuilder assist = new StringBuilder();
        StringBuilder netWorth = new StringBuilder();
        StringBuilder dmg = new StringBuilder();
        StringBuilder player = new StringBuilder();

        if(rd == 0) {//radiant
            for(int x = 0; x < jn.size()/2; x++) {//radiant heroes
                if(x == jn.size()/2 -1) {//if last iteration
                    heroes.append(jn.get(x).get("hero_id"));
                    kills.append(jn.get(x).get("kills"));
                    deaths.append(jn.get(x).get("deaths"));
                    assist.append(jn.get(x).get("assists"));
                    netWorth.append(jn.get(x).get("net_worth"));
                    dmg.append(jn.get(x).get("hero_damage"));
                    player.append(jn.get(x).get("personaname"));//yes this is correct
                    continue;
                }
                heroes.append(jn.get(x).get("hero_id") + "\n");
                kills.append(jn.get(x).get("kills") + "\n");
                deaths.append(jn.get(x).get("deaths") + "\n");
                assist.append(jn.get(x).get("assists") + "\n");
                netWorth.append(jn.get(x).get("net_worth") + "\n");
                dmg.append(jn.get(x).get("hero_damage") + "\n");
                player.append(jn.get(x).get("personaname") + "\n");//yes this is correct
            }
        }
        else {//dire
            for(int x = jn.size()/2; x < jn.size(); x++) {//dire heros
                if(x == jn.size() -1) {
                    heroes.append(jn.get(x).get("hero_id"));
                    kills.append(jn.get(x).get("kills"));
                    deaths.append(jn.get(x).get("deaths"));
                    assist.append(jn.get(x).get("assists"));
                    netWorth.append(jn.get(x).get("net_worth"));
                    dmg.append(jn.get(x).get("hero_damage"));
                    player.append(jn.get(x).get("personaname"));//yes this is correct
                    continue;
                }
                heroes.append(jn.get(x).get("hero_id") + "\n");
                kills.append(jn.get(x).get("kills") + "\n");
                deaths.append(jn.get(x).get("deaths") + "\n");
                assist.append(jn.get(x).get("assists") + "\n");
                netWorth.append(jn.get(x).get("net_worth") + "\n");
                dmg.append(jn.get(x).get("hero_damage") + "\n");
                player.append(jn.get(x).get("personaname") + "\n");//yes this is correct
            }
        }
        List<String> list = new ArrayList<>();
        list.add(heroes.toString());
        list.add(kills.toString());
        list.add(deaths.toString());
        list.add(assist.toString());
        list.add(netWorth.toString());
        list.add(dmg.toString());
        list.add(player.toString());
        return list;

    }
    public JsonNode getJson(URL url) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        return mapper.readTree(url);
    }
    public int checkForStatusCode(URL url) throws IOException {//validate the http response
        HttpURLConnection http = (HttpURLConnection) url.openConnection();
        int status = http.getResponseCode();
        http.disconnect();
        return status;
    }

}
