package org.bolyuk.bktgbotlib.ui.layouts;

import com.pengrad.telegrambot.model.request.InlineKeyboardButton;
import com.pengrad.telegrambot.model.request.InlineKeyboardMarkup;
import org.bolyuk.bktgbotlib.ui.views.View;

import java.util.ArrayList;

public class VList extends HList{
    @Override
    public ArrayList<ArrayList<View>> getParsedLayout() {
       ArrayList<ArrayList<View>> result = new ArrayList<>();

       for(int i=0; i < views.size(); i++){
           result.add(new ArrayList<>());
           result.get(i).add(views.get(i));
       }

       return result;
    }

    @Override
    public InlineKeyboardMarkup getKeyboard() {
        InlineKeyboardMarkup k = new InlineKeyboardMarkup();

        for (View view : views) k.addRow(view.toInlineButton());
        return k;
    }
}
