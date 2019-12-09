package com.example.awesomechatroomz.models;

import java.util.Date;

public class ImageMessage extends Message {

    private String imageUrl;

    public ImageMessage() {
        super();
        messageType = Message.IMAGE;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    @Override
    public String toString() {
        return "Image message: "+imageUrl+" from "+getSender();
    }
}
