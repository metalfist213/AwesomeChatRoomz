package com.example.awesomechatroomz.models;

import java.util.Calendar;
import java.util.Date;

public class TextMessage implements IMessage {

    private String sender;
    private String message;
    private long time;

    public TextMessage() {
        time = new Date().getTime();
    }

    @Override
    public void setSender(String userId) {
        this.sender = userId;
    }

    @Override
    public String getSender() {
        return sender;
    }

    @Override
    public int getMessageType() {
        return IMessage.TEXT;
    }

    @Override
    public long getTime() {
        return time;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
