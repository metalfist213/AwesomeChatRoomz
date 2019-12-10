package com.example.awesomechatroomz.models;

import com.google.firebase.database.Exclude;

import java.util.Date;

public abstract class Message {
    public final static int TEXT = 0;
    public final static int IMAGE = 1;

    private String sender;
    int messageType;
    private long time;

    @Exclude
    private String id;

    Message() {
        time = new Date().getTime();
    }

    public void setSender(String sender) {
        this.sender = sender;
    }

    public String getSender() {
        return sender;
    }

    public int getMessageType() {
        return messageType;
    }

    public long getTime() {
        return time;
    }

    @Exclude
    public Date getDate() {
        Date date = new Date();
        date.setTime(time);
        return date;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
