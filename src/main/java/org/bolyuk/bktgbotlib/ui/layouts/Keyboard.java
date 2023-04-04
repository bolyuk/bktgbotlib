package org.bolyuk.bktgbotlib.ui.layouts;

import com.pengrad.telegrambot.model.request.InlineKeyboardButton;
import com.pengrad.telegrambot.model.request.InlineKeyboardMarkup;
import org.bolyuk.bktgbotlib.bot.Response;
import org.bolyuk.bktgbotlib.ui.views.Label;
import org.bolyuk.bktgbotlib.ui.views.View;

import java.util.ArrayList;
import java.util.function.Consumer;

public class Keyboard extends Layout{

    ArrayList<HList> views = new ArrayList<>();

    public Keyboard addRow(){
        views.add(new HList());
        return this;
    }

    public Keyboard addRow(View... views){
        this.views.add((HList) new HList().add(views));
        return this;
    }

    public Keyboard addRow(HList views){
        this.views.add(views);
        return this;
    }

    public HList row(int i){
        return views.get(i);
    }

    public int size(){
        return views.size();
    }

    public Keyboard add(Layout layout){
        ArrayList<ArrayList<View>> l = layout.getParsedLayout();

        l.forEach(list -> views.add((HList)new HList().add(list.toArray(new View[0]))));

        return this;
    }

    public Keyboard addRow(Layout layout){
        addRow();
        add(layout);
        return this;
    }

    public Keyboard set(int row, int column, Layout layout){
        //row = x , column = y;
        ArrayList<ArrayList<View>> l = layout.getParsedLayout();
        while(views.size() < column)
            views.add(new HList());

        l.forEach(new Consumer<ArrayList<View>>() {
            @Override
            public void accept(ArrayList<View> list) {
                HList current = views.get(column);

                while(current.size() < row)
                    current.add(new Label(" "));

                current.replace(column, list.toArray(new View[0]));
            }
        });
        return this;
    }



    @Override
    public InlineKeyboardMarkup getKeyboard() {
        InlineKeyboardMarkup k = new InlineKeyboardMarkup();

        views.forEach(hList -> {
            ArrayList<InlineKeyboardButton> b = new ArrayList<>();
            hList.views.forEach(view -> b.add(view.toInlineButton()));
            k.addRow(b.toArray(new InlineKeyboardButton[0]));
        });
        return k;
    }

    @Override
    public ArrayList<ArrayList<View>> getParsedLayout() {
        ArrayList<ArrayList<View>> result = new ArrayList<>();

        views.forEach(hList -> result.add(hList.views));

        return result;
    }

    @Override
    public void onLayoutClicked(Response r) {
        views.forEach(hList -> hList.onLayoutClicked(r));
    }
}
