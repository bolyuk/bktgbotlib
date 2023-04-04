package org.bolyuk.bktgbotlib.examples;

import org.bolyuk.bktgbotlib.bot.BotInstance;
import org.bolyuk.bktgbotlib.bot.Provider;
import org.bolyuk.bktgbotlib.bot.UserStatable;
import org.bolyuk.bktgbotlib.bot.UserState;

import java.util.HashMap;

public class ExampleUserStateProvider implements Provider.UserStateProvider {
    HashMap<String, UserStatable> users = new HashMap<>();
    @Override
    public UserStatable getUser(String uid, BotInstance bot) {
        if(!users.containsKey(uid))
            users.put(uid, new UserState(uid, bot));
        return users.get(uid);
    }
}
