package org.bolyuk.bktgbotlib.bot;

public abstract class BotMenu {

    public CommandHandler handler = new CommandHandler();

    public abstract void menuBuilder(UserStatable user);
    public abstract void initialization(UserStatable user, Response response);
    public abstract void execution(UserStatable user, Response response);

    public final void cancelGlobalCommand(String command){
        handler.setCommand(command, new CommandHandler.CommandProvider() {
            @Override
            public void commandHandle(String command, String[] body, Response response) {

            }
        });
    }
}
