package org.bolyuk.bktgbotlib.bot;

import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.UpdatesListener;
import com.pengrad.telegrambot.model.Update;

import java.util.ArrayList;
import java.util.List;

public class BotInstance extends TelegramBot {
    public Provider provider;
    public CommandHandler commandHandler = new CommandHandler();

    public BotInstance(String botToken, Provider provider) {
        super(botToken);
        this.provider=provider;
        setUpdatesListener(new UpdatesListener() {
            @Override
            public int process(List<Update> list) {
                for(Update u : list)
                    try{
                        if(provider.updateProvider == null)
                            doUpdate(new Response(u));
                        else
                            provider.updateProvider.onUpdateRecieved(new Response(u));
                    } catch (Exception e){
                        e.printStackTrace();
                    }
                return UpdatesListener.CONFIRMED_UPDATES_ALL;
            }
        });
    }

    private void doUpdate(Response response){
        UserStatable current_user;

        String command = null;
        String[] command_body =new String[0];

        if(!response.isCallback() && response.text() != null && response.text().startsWith("/")) {
            String regex=" ";
            if(response.text().startsWith("/start"))
                regex="=";

            ArrayList<String> buf = new ArrayList<String>(List.of(response.text().split(regex)));

            System.out.println(buf.toString());

            command= buf.get(0);
            buf.remove(0);
            command_body = buf.toArray(new String[0]);
        }

        if(provider.userProvider != null) {
            current_user = provider.userProvider.getUser(response.fromId(), this);
            try {
                BotMenu menu = current_user.get().ui.getMenu();
                if(menu != null) {
                    menu.menuBuilder(current_user);
                    if (command != null) {
                        if (menu.handler.canHandle(command))
                            menu.handler.handle(command, command_body, response);
                        else if (commandHandler.canHandle(command))
                            commandHandler.handle(command, command_body, response);
                        else
                            menu.execution(current_user, response);
                    } else {
                        menu.execution(current_user, response);
                    }
                } else if (commandHandler.canHandle(command))
                    commandHandler.handle(command, command_body, response);

            } catch (Exception e) {
                e.printStackTrace();
            }
        } else if(commandHandler.canHandle(command))
            commandHandler.handle(command, command_body, response);
    }



}
