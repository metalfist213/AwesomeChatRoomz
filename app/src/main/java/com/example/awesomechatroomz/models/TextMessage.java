package com.example.awesomechatroomz.models;

public class TextMessage extends Message {

    private String message;

    public TextMessage() {
        super();
        messageType = Message.TEXT;
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
