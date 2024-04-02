package org.bolyuk.bktgbotlib.bot;

import org.bolyuk.bktgbotlib.ui.UIcontroller;

import java.util.ArrayList;
import java.util.HashMap;

public class UserState {
    public String uid;
    public int last_msg=-1;
    ArrayList<Integer> msg_stack = new ArrayList<>();

    public ArrayList<String> menus = new ArrayList<>();
    public HashMap<String, HashMap<String, String>> cache = new HashMap<>();

    transient public UIcontroller ui = new UIcontroller(this);
    transient public BotInstance bot;

    public UserState(){}

    public UserState(String uid, BotInstance bot){
        this.uid=uid;
        this.bot=bot;
    }

    public UserState attachBot(BotInstance bot){
        this.bot=bot;
        return this;
    }

    public HashMap<String, String> getCacheFrom(String menu_name){
        if(!cache.containsKey(menu_name))
            cache.put(menu_name, new HashMap<>());
        return cache.get(menu_name);
    }

    public String getCache(String i){
        String name = ui.menu_name();
        if(!cache.containsKey(name))
            return null;
        return cache.get(name).get(i);
    }

    public void setCache(String i, String value){
        String name = ui.menu_name();
        if(cache.get(name) == null)
            cache.put(name, new HashMap<>());
        cache.get(name).put(i, value);
    }

    public void clearCache(){
        String name = ui.menu_name();
        cache.put(name, new HashMap<>());
    }

    public <T> T to(Class<T> clazz){
        return (T)this;
    }
}
