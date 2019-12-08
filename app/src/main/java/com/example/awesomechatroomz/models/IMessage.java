package com.example.awesomechatroomz.models;

public interface IMessage {
    public final static int TEXT = 0;
    public final static int IMAGE = 1;

    public void setSender(String userId);

    public String getSender();
    public int getMessageType();
    public long getTime();
}
