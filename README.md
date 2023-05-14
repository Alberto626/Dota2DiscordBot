# Discord Dota 2 Bot
Discord Bot that using Java with the use of this [Java library](https://github.com/DV8FromTheWorld/JDA)

This bot is to keep track of Dota 2 related information such as match information.

Currently, using 2 Commands, More commands such as player's last match or best hero

/match 
* Give the slash command a valid match ID  from Dota 2 and you will get basic information such as heros used, kills, deaths, and team winner
* ADD GIF HERE

/fart 
* Send a simple reply with "you just farted"

## Setup
1. After downloading this, you must set up a txt file inside the resources folder called token.txt which will contain your VERY IMPORTANT TOKEN
2. Since there are many excellent tutorials on of how to create a discord bot use [This](https://youtu.be/LFsxkWME7M0?t=584)
3. After getting your Token from the Bot section in Discord Developer Portal, you must add it to your token.txt file.
4. Now if this is going to be in your im your discord server, you must change  jda.getGuildById(**YOUR GUILD ID**); meaning get your channels specific id and change that function in Discord Bot java file
5. Now its ready to run. Run your DiscordBot main.