package org.bolyuk.bktgbotlib.ui.views;

import com.pengrad.telegrambot.model.request.InlineKeyboardButton;

import java.util.UUID;

public class Label extends View{

    public Label(String label) {
        super(label);
        this.id = UUID.randomUUID().toString();
    }
}
