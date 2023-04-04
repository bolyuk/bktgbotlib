package org.bolyuk.bktgbotlib.ui.layouts;

import com.pengrad.telegrambot.model.request.InlineKeyboardButton;
import com.pengrad.telegrambot.model.request.InlineKeyboardMarkup;
import org.bolyuk.bktgbotlib.bot.Response;
import org.bolyuk.bktgbotlib.ui.views.View;

import java.util.ArrayList;
import java.util.Arrays;

public class HList extends Layout{
    ArrayList<View> views = new ArrayList<>();
    @Override
    public InlineKeyboardMarkup getKeyboard() {
            InlineKeyboardMarkup k = new InlineKeyboardMarkup();

            InlineKeyboardButton[] r_list = new InlineKeyboardButton[views.size()];

            for (int j = 0; j < views.size(); j++) {
                r_list[j]=views.get(j).toInlineButton();
            }
            k.addRow(r_list);
        return k;
    }

    @Override
    public ArrayList<ArrayList<View>> getParsedLayout() {
        ArrayList<ArrayList<View>> result =  new ArrayList<>();
        result.add(views);
        return result;
    }

    @Override
    public void onLayoutClicked(Response r) {
        if(r.isCallback())
            for (View view : views) view.checkClicked(r.callBack());
    }

    public Layout add(View... views) {
        this.views.addAll(Arrays.asList(views));
        return this;
    }

    public Layout del(int i) {
        views.remove(i);
        return this;
    }

    public Layout clear(){
        views.clear();
        return this;
    }


    public View get(int i) {
        return views.get(i);
    }

    public int size() {
        return views.size();
    }

    public Layout insert(int i, View... views) {
        for(int j=0; j < views.length; j++)
            this.views.add(j+i, views[j]);
        return this;
    }


    public Layout replace(int i, View... views) {
        for(int j=0; j < views.length; j++)
            this.views.set(j+i, views[j]);
        return this;
    }
}
