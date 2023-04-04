package org.bolyuk.bktgbotlib.ui.views;

import com.pengrad.telegrambot.model.request.InlineKeyboardButton;
import org.bolyuk.bktgbotlib.bot.UserState;

import java.util.Timer;
import java.util.TimerTask;

public class Button extends View {
    String onDelay_label;
    UserState user;

    TimerTask task = new TimerTask() {
        @Override
        public void run() {
            user.ui.menuInit();
        }
    };

    public Button set_onDelayLabel(String text){
        this.onDelay_label=text;
        return this;
    }

    public long getLastDelay(){
        String buf = user.getCache(id+"_last_delay");

        if(buf != null)
            return Long.parseLong(buf);

        return 0l;
    }

    public long getDelay(){
        if(user == null)
            return 0;
        String buf = user.getCache(id+"_delay");

        if(buf != null)
            return Long.parseLong(buf);

        return 0l;
    }

    public Button setUser(UserState user){
        this.user=user;
        return this;
    }

    public Button setDelay(Long delay){
        user.setCache(id+"_delay", delay.toString());
        return this;
    }

    public Button setLastDelay(Long last_delay){
        user.setCache(id+"_last_delay", last_delay.toString());
        return this;
    }

    public Button setDelay(long delay, String onDelay_label,UserState user){
        setDelay(delay);
        this.onDelay_label=onDelay_label;
        this.user=user;

        return this;
    }

    @Override
    public void checkClicked(String text) {
        if(id != null && id.equals(text)) {
            Long time = System.currentTimeMillis();
            if(getDelay() == 0 || getDelay()+getLastDelay() <= time) {
                if (onClick != null)
                    onClick.onClick(this);
                if(getDelay() > 0) {
                    setLastDelay(time);
                    task.run();
                    new Timer().schedule(task, getDelay());
                }
            }
        }
    }

    @Override
    public InlineKeyboardButton toInlineButton() {
        String text = label;
        if(getDelay() != 0 && getDelay()+getLastDelay() > System.currentTimeMillis()) {
            text = onDelay_label;
            new Timer().schedule(task, getDelay());
        }
        return new InlineKeyboardButton(text).callbackData(id);
    }

    public Button(String label, String id, ViewClick onClicked){
        super(label, id, onClicked);
    }

    public Button(String label,String id){
        super(label, id, null);
    }

}
