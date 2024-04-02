package org.bolyuk.bktgbotlib.bot;

public abstract class ChatMenu extends BotMenu{

    @Override
    public void execution(UserState user, Response response) {
        execution(null, user, response);
    }

    public void pre_execution(UserState chatState, UserState user, Response response) {
        if(response.text() != null && user.getCache("silent_mode").equals("1"))
            chatState.ui.delete(response.id());
        else
            execution(chatState, user, response);
    }

    public abstract void execution(UserState chatState, UserState userState, Response r);
}
