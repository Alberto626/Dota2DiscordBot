package MainPackage;

import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.entities.Activity;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.interactions.commands.OptionType;
import net.dv8tion.jda.api.requests.GatewayIntent;

import javax.security.auth.login.LoginException;

public class DiscordBot {

    public static void main(String[] args) throws LoginException, InterruptedException {
        JDA jda = JDABuilder.createDefault(Token.TOKEN)//GitIgnored Token class
                .setActivity(Activity.playing("Your Mother"))
                .enableIntents(GatewayIntent.MESSAGE_CONTENT)
                .addEventListeners(new MessageResponses(), new SlashCommands())
                .build().awaitReady();
        Guild guild = jda.getGuildById("1095507859745288287");//exclusively to test out if commands work using guild since this is vastly faster
        //this is also server id
        if(guild != null) {
            guild.upsertCommand("fart", "fart really hard")
                    .queue();
            guild.upsertCommand("match", "API TESTING")
                    .addOption(OptionType.INTEGER, "gameid", "value of the game", true)// this makes sure my numbers are whole number
                    .queue();
        }

    }
}
