package org.bolyuk.bktgbotlib.bot;

import java.util.HashMap;

public class Provider {
    public UserStateProvider userProvider;
    public UpdateProvider updateProvider;

    public Provider setDefaultUserStateProvider(UserStateProvider provider){
        userProvider =provider;
        return this;
    }

    public Provider setDefaultUpdateProvider(UpdateProvider provider){
        updateProvider =provider;
        return this;
    }

        public interface UserStateProvider{
         UserStatable getUser(String uid, BotInstance bot);
        }
        public interface UpdateProvider{
          void onUpdateRecieved(Response response);
        }

}
