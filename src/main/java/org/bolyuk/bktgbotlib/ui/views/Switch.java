package org.bolyuk.bktgbotlib.ui.views;

import com.pengrad.telegrambot.model.request.InlineKeyboardButton;
import org.bolyuk.bktgbotlib.bot.UserState;

public class Switch extends View {
    UserState user;
    String selected_text;

    SwitchListener _onStateChanged;

    public Switch(String label, String id, SwitchListener listener,  UserState user) {
        super(label, id, null);
        this._onStateChanged=listener;
        this.user=user;
    }

    public Switch(String label, String id,  UserState user) {
        super(label, id, null);
        this.user=user;
    }


    public Switch setSwitchListener(SwitchListener listener){
        this._onStateChanged=listener;
        return this;
    }

    public Switch setSelectedText(String text){
        this.selected_text=text;
        return this;
    }

    public boolean getState(){
       String state = user.getCache(id);
       return state != null && state.equals("true");
    }

    public void checkClicked(String text){
        if(id.equals(text)) {
            setState(!getState());
            if (_onStateChanged != null)
                _onStateChanged.onStateChanged(getState());
        }
    }

    public InlineKeyboardButton toInlineButton(){
        String used_text = (getState() && selected_text != null)?selected_text:label;

        return new InlineKeyboardButton(used_text).callbackData(this.id);
    }

    public Switch setState(boolean value){
        user.setCache(id, value?"true":"false");
        return this;
    }

    public interface SwitchListener {
        public void onStateChanged(boolean state);
    }
}
