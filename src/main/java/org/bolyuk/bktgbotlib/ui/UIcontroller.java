package org.bolyuk.bktgbotlib.ui;

import com.pengrad.telegrambot.request.DeleteMessage;
import com.pengrad.telegrambot.request.EditMessageText;
import com.pengrad.telegrambot.request.SendMessage;
import org.bolyuk.bktgbotlib.bot.BotMenu;
import org.bolyuk.bktgbotlib.bot.UserState;
import org.bolyuk.bktgbotlib.ui.layouts.Layout;

import java.util.ArrayList;

public class UIcontroller {
    public transient UserState user;

    public UIcontroller(){}

    public UIcontroller(UserState user){
        this.user=user;
    }

    public void addMenu(BotMenu menu){
        user.menus.add(menu.getClass().getName());
        menu.menuBuilder(user);
        menu.initialization(user, null);
    }

    public void replaceMenu(BotMenu menu){
        user.menus.remove(user.menus.size()-1);
        addMenu(menu);
    }

    public void dismissMenu(){
        user.menus.remove(user.menus.size()-1);
    }

    public void menuInit(){
        BotMenu menu = user.ui.getMenu();
        menu.menuBuilder(user);
        menu.initialization(user, null);
    }

    public void menuReInit(){
        user.last_msg = -1;
    }




    public void send(String text){
        user.last_msg =  user.bot.execute(new SendMessage(user.uid, text)).message().messageId();
    }


    public void send(String text, Layout layout){
        user.last_msg = user.bot.execute(new SendMessage(user.uid, text).replyMarkup(layout.getKeyboard())).message().messageId();
    }


    public void reply(String text, int msg_id){
        user.last_msg =  user.bot.execute(new SendMessage(user.uid, text).replyToMessageId(msg_id)).message().messageId();
    }


    public void reply(String text, Layout layout, int msg_id){
        user.last_msg =  user.bot.execute(new SendMessage(user.uid, text).replyToMessageId(msg_id).replyMarkup(layout.getKeyboard())).message().messageId();
    }


    public void resend(String text){
        if(user.last_msg != -1)
            user.bot.execute(new EditMessageText(user.uid, user.last_msg, text));
        else
            send(text);
    }

    public void resend(String text, Layout layout){
        if(user.last_msg != -1)
           user.bot.execute(new EditMessageText(user.uid, user.last_msg, text).replyMarkup(layout.getKeyboard()));
        else
            send(text,layout);
    }

    public void delete(int id){
        user.bot.execute(new DeleteMessage(user.uid, id));
    }

    public BotMenu getMenu(){
        try {
           return  (BotMenu) Class.forName(menu_name()).getConstructor().newInstance();
        }catch (Exception e){
            return null;
        }
    }

    public String menu_name(){
        return user.menus.get(user.menus.size()-1);
    }
}
