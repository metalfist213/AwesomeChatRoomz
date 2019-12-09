package com.example.awesomechatroomz.models;

import java.util.Date;

public class TextMessage extends Message {

    private String message;

    public TextMessage() {
        super();
        messageType = Message.TEXT;
    }

    public TextMessage(String message) {
        super();
        this.message = message;
    }
    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    @Override
    public String toString() {
        return "Message: " + message + "\nFrom:" + getSender();
    }
}
