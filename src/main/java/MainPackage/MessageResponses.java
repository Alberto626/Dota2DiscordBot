package MainPackage;

import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

public class MessageResponses extends ListenerAdapter {
    @Override
    public void onMessageReceived(MessageReceivedEvent event) {
        if(event.getAuthor().isBot()) { //prevent chain reaction
            return;
        }
        String messageSent = event.getMessage().getContentRaw();
        if(messageSent.equalsIgnoreCase("rtz")) {
            event.getChannel().sendMessage("Too easy for RTZ").queue();
        }
    }
}
