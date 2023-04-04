package org.bolyuk.bktgbotlib.bot;

import java.util.HashMap;

public class CommandHandler {
    public HashMap<String, CommandProvider> commandProvider = new HashMap<>();

    public boolean canHandle(String command){
        return commandProvider.containsKey(command);
    }

    public void handle(String command, String[] body, Response response){
        commandProvider.get(command).commandHandle(command,body,response);
    }

    public CommandHandler setCommand(String command, CommandProvider provider){
        commandProvider.put(command,provider);
        return this;
    }

    public CommandHandler delCommand(String command){
        commandProvider.remove(command);
        return this;
    }

    public interface CommandProvider{
        void commandHandle(String command, String[] body, Response response);
    }
}
