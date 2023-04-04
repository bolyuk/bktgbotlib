package org.bolyuk.bktgbotlib.ui.views;

import com.pengrad.telegrambot.model.request.InlineKeyboardButton;


public abstract class View {
    public String label;

     String id;
     ViewClick onClick;

    public View(String label){
        this.label=label;
    }

    public View(String label, String id){
        this.label=label;
        this.id=id;
    }

    public View(String label, String id, ViewClick onClick){
        this.label=label;
        this.id=id;
        this.onClick=onClick;
    }

    public InlineKeyboardButton toInlineButton(){
        return new InlineKeyboardButton(label).callbackData(id);
    }

    public void checkClicked(String text){
        if(id != null && id.equals(text) && onClick != null)
                onClick.onClick(this);
    }

    public <T extends View> T toView(Class<T> Class){
        return (T)this;
    }

    public void setOnClickListener(ViewClick listener){
        this.onClick=listener;
    }

    public interface ViewClick{
        public void onClick(View view);
    }
}
