package org.bolyuk.bktgbotlib.ui.layouts;

import com.pengrad.telegrambot.model.request.InlineKeyboardMarkup;
import org.bolyuk.bktgbotlib.bot.Response;
import org.bolyuk.bktgbotlib.ui.views.View;

import java.util.ArrayList;

public abstract class Layout {
   public abstract InlineKeyboardMarkup getKeyboard();
   public abstract ArrayList<ArrayList<View>> getParsedLayout();
   public abstract void onLayoutClicked(Response r);

}
