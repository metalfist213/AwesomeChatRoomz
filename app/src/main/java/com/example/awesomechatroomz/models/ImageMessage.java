package com.example.awesomechatroomz.models;

import java.util.Date;

public class ImageMessage implements IMessage {

    private String sender;
    private long time;
    private String imageUrl;

    public ImageMessage() {
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
        return IMessage.IMAGE;
    }

    @Override
    public long getTime() {
        return time;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }
}
