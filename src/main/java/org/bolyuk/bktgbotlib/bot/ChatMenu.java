package org.bolyuk.bktgbotlib.bot;

public abstract class ChatMenu extends BotMenu{

    @Override
    public void execution(UserState user, Response response) {
        execution(null, user, response);
    }

    public abstract void execution(UserState chatState, UserState userState, Response r);
}
