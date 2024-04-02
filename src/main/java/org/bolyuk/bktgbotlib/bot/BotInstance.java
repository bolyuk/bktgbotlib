package org.bolyuk.bktgbotlib.bot;

import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.UpdatesListener;
import com.pengrad.telegrambot.model.Update;
import org.bolyuk.bktgbotlib.Debug;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

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

    private void doUpdate(Response response) {
        String command = null;
        String[] command_body = new String[0];

        if (!response.isCallback() && response.text() != null && response.text().startsWith("/")) {
            String regex = " ";
            if (response.text().startsWith("/start"))
                regex = "=";

            ArrayList<String> buf = new ArrayList<String>(List.of(response.text().split(regex)));

            System.out.println(buf.toString());

            command = buf.get(0);
            buf.remove(0);
            command_body = buf.toArray(new String[0]);
        }

        if (!Objects.equals(response.chatId(), response.fromId())) {
            doPublicChatUpdate(response, command, command_body);
            Debug.d("public chat handler");
            return;
        }
        Debug.d("private chat handler");
        doPrivateChatUpdate(response, command, command_body);
        }

        public void doPublicChatUpdate(Response response, String command, String[] command_body){

            if(provider.chatProvider == null){
                if(command != null && commandHandler.canHandle(command))
                    commandHandler.handle(command, command_body, response);
                return;
            }

           UserState chat = provider.chatProvider.getUser(response.chatId(), this);

            if(provider.userProvider == null)
                return;

            UserState user = provider.userProvider.getUser(response.fromId(), this);

            BotMenu menu = chat.ui.getMenu();

            if(tryDoCommand(menu, command, command_body, response)) {
                Debug.d("no menu and no command. exit");
                return;
            }

            menu.menuBuilder(chat);
            if(menu instanceof ChatMenu)
                ((ChatMenu)menu).pre_execution(chat, user, response);
            else
                menu.pre_execution(user, response);

        }

        public void doPrivateChatUpdate(Response response, String command, String[] command_body){

          if(provider.userProvider == null){
              if(command != null && commandHandler.canHandle(command))
                  commandHandler.handle(command, command_body, response);
              return;
          }

            UserState user = provider.userProvider.getUser(response.fromId(), this);
            BotMenu menu = user.ui.getMenu();

            if(tryDoCommand(menu, command, command_body, response)) {
                Debug.d("no menu and no command. exit");
                return;
            }

            menu.menuBuilder(user);
            menu.pre_execution(user, response);
        }

        public boolean tryDoCommand(BotMenu menu, String command, String[] command_body, Response response){
            boolean isCommand = command != null;
            boolean isMenu = menu != null;

            if(!isMenu && !isCommand)
                return true;

            if(isCommand) {
                if(isMenu && menu.handler.canHandle(command)) {
                    menu.handler.handle(command, command_body, response);
                    return true;
                }
                if(commandHandler.canHandle(command)) {
                    commandHandler.handle(command, command_body, response);
                    return true;
                }
            }
            return false;
        }



}
