package org.bolyuk.bktgbotlib.bot;

import com.pengrad.telegrambot.model.request.InlineKeyboardMarkup;
import com.pengrad.telegrambot.request.EditMessageText;
import com.pengrad.telegrambot.request.SendMessage;
import org.bolyuk.bktgbotlib.ui.layouts.Layout;

public class MessageRequest {
    UserState state;

    String text=null;
    InlineKeyboardMarkup layout = null;

    public MessageRequest(UserState state){
        this.state=state;
    }

    public MessageRequest setText(Object text){
        this.text=text.toString();
        return this;
    }

    public MessageRequest setLayout(Layout layout){
        this.layout=layout.getKeyboard();
        return this;
    }

    public MessageRequest setLayout(InlineKeyboardMarkup layout){
        this.layout=layout;
        return this;
    }

    public int push(){
        SendMessage m = new SendMessage(state.uid, text);
        if(layout!= null)
            m.replyMarkup(layout);
        int msg_id = state.bot.execute(m).message().messageId();
      return msg_id;
    }

    public void send(){
        state.last_msg = push();
    }

    public void resendStack(int i){
        if(state.msg_stack.size() > i)
           _resend(state.msg_stack.get(i));
        else
            state.msg_stack.add(push());
    }

    public void resend(){
        _resend(state.last_msg);
    }

    private void _resend(int id){
        if(state.last_msg != -1) {
            EditMessageText e = new EditMessageText(state.uid, id, text);
            if(layout!= null)
                e.replyMarkup(layout);
            state.bot.execute(e);
        }else {
            send();
        }
    }

    public static MessageRequest fromId(BotInstance bot, String id){
        return new MessageRequest(bot.provider.userProvider.getUser(id, bot));
    }
}
