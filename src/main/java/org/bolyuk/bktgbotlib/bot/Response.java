package org.bolyuk.bktgbotlib.bot;

import com.pengrad.telegrambot.model.Chat;
import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.AnswerCallbackQuery;

public class Response {
    Update u;
    boolean isCallback=false;

    public Response(Update u){
        this.u=u;
        isCallback = u.callbackQuery() != null;
    }

    public boolean isPrivateChat(){
        if(isCallback)
            return true;
        else
            return u.message().chat().type().equals(Chat.Type.Private);
    }

    public boolean isCallback(){
        return isCallback;
    }

    public boolean hasDocument(){
        return u.message().document() != null;
    }

    public String getMimeType(){
        return u.message().document().mimeType();
    }

    public String getDocumentId(){
        return u.message().document().fileId();
    }

    public String fromId(){
        if(isCallback)
            return String.valueOf(u.callbackQuery().from().id());
        else
            return String.valueOf(u.message().from().id());
    }

    public String chatId(){
        return String.valueOf(u.message().chat().id());
    }

    public String text(){
        return u.message().text();
    }

    public int id(){
        return u.message().messageId();
    }

    public String[] callBackArr(){
        return callBack().split(" ");
    }

    public String callBackArr(int i){
        return callBackArr()[i];
    }

    public String callBack(){
        return u.callbackQuery().data();
    }
}
