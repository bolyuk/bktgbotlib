package org.bolyuk.bktgbotlib.bot;

public class Provider {
    public UserStateProvider userProvider;
    public UpdateProvider updateProvider;

    public UserStateProvider chatProvider;

    public Provider setDefaultUserStateProvider(UserStateProvider provider){
        userProvider =provider;
        return this;
    }

    public Provider setDefaultChatStateProvider(UserStateProvider provider){
        chatProvider =provider;
        return this;
    }

    public Provider setDefaultUpdateProvider(UpdateProvider provider){
        updateProvider =provider;
        return this;
    }

        public interface UserStateProvider{
         UserState getUser(String uid, BotInstance bot);
        }
        public interface UpdateProvider{
          void onUpdateRecieved(Response response);
        }

}
