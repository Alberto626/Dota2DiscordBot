package MainPackage;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.dv8tion.jda.api.interactions.commands.OptionMapping;
import net.dv8tion.jda.api.utils.FileUpload;

import java.io.File;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
public class SlashCommands extends ListenerAdapter {
    @Override
    public void onSlashCommandInteraction(SlashCommandInteractionEvent event) {
        if(event.getName().equals("fart")) {
            String x = "you just farted";
            event.deferReply().queue();
            event.getHook().sendMessage(x).queue(); //setEphemeral is to only send command only to you to prevent discord cloggin messages
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
                File file = new File("src/main/resources/heros/1.png");//FIX THIS
                event.getHook().sendMessageEmbeds(eb.build())
                        .addFiles(FileUpload.fromData(file))
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

            event.getHook().sendMessage("This works").queue();
        }
        if(event.getName().equals("testpic")) {
            event.deferReply().queue();
            event.getHook().sendMessage("hello world").queue();
        }

    }
    public EmbedBuilder builder(JsonNode json) {// LOOK up embeds
        EmbedBuilder eb = new EmbedBuilder();
        eb.setTitle("Dota 2 Match: " + json.get("match_id"));// this will show the game id// Example is [radiant victory](LINK)
        eb.setDescription("");//Find the winner
        eb.addField("Radiant", "", true);//heroes
        eb.addField("Kills","", true);
        eb.addField("Deaths", "", true);
        eb.addField("Assist", "", true);
        eb.addField("Net worth","", true);
        eb.addField("Dmg Dealt","", true);
        eb.addField("Player","",true);

        eb.addField("VS","", false);

        eb.addField("Dire", "", true);//heroes
        eb.addField("Kills","", true);
        eb.addField("Deaths", "VS", true);
        eb.addField("Assist", "", true);
        eb.addField("Net worth","", true);
        eb.addField("Dmg Dealt","", true);
        eb.addField("Player","",true);
        eb.setImage("https://www.dotabuff.com/assets/heroes/pudge-c0a4fce1b8a478ae8da6d61b8d514d070dc58b179abf8eee78f1ff217d14e46c.jpg");
        return eb;
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
