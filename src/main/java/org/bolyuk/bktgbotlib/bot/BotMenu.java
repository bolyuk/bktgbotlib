package org.bolyuk.bktgbotlib.bot;

public abstract class BotMenu {

    public CommandHandler handler = new CommandHandler();

    public abstract void menuBuilder(UserState user);
    public abstract void initialization(UserState user, Response response);
    public abstract void execution(UserState user, Response response);

    public void pre_execution(UserState user, Response response){
        if(response.text() != null && user.getCache("silent_mode").equals("1"))
            user.ui.delete(response.id());
        else
            execution(user, response);
    }

    protected final void disableUserInput(UserState user, boolean value){
        user.setCache("silent_mode", value?"1":"0");
    }

    protected final void cancelGlobalCommand(String command){
        handler.setCommand(command, new CommandHandler.CommandProvider() {
            @Override
            public void commandHandle(String command, String[] body, Response response) {

            }
        });
    }
}
