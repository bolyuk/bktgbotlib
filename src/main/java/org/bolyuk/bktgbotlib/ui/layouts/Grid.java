package org.bolyuk.bktgbotlib.ui.layouts;

import com.pengrad.telegrambot.model.request.InlineKeyboardMarkup;
import org.bolyuk.bktgbotlib.bot.Response;
import org.bolyuk.bktgbotlib.ui.views.View;

import java.util.ArrayList;

@Deprecated
public class Grid extends Layout{

    View[][] views;

    public Grid(int row, int columns){
        views = new View[row][columns];
    }


    @Override
    public InlineKeyboardMarkup getKeyboard() {
        return null;
    }

    @Override
    public ArrayList<ArrayList<View>> getParsedLayout() {
        return null;
    }

    @Override
    public void onLayoutClicked(Response r) {

    }
}
