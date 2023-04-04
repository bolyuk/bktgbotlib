package org.bolyuk.bktgbotlib.bot;

public interface UserStatable {
    UserState get();

   default  <T extends UserStatable> T to(Class<T> clazz){
        return (T)this;
    }
}
