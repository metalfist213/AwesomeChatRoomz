package com.example.awesomechatroomz.models;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class ChatRoom {
    private long lastUpdated;
    private String name;
    private User user;
    private List<String> usersId;
    private String description;
    private ArrayList messages;
    private HashMap<String, User> userPool; // To save user information one place. Won't load the same image more than once.

    public HashMap<String, User> getUserPool() {
        return userPool;
    }

    public ChatRoom(User user) {
        this.messages = new ArrayList();
        this.user = user;
        this.userPool = new HashMap<>();
    }

    public ChatRoom() {
        this.messages = new ArrayList();
    }

    public long getLastUpdated() {
        return lastUpdated;
    }

    public void setLastUpdated(long lastUpdated) {
        this.lastUpdated = lastUpdated;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<String> getUsersId() {
        return usersId;
    }

    public void setUsersId(List<String> usersId) {
        this.usersId = usersId;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public String toString() {
        return this.name+" "+this.description;
    }

    public List<Message> getMessages() {
        return messages;
    }

    public void setMessages(ArrayList<Message> messages) {
        this.messages = messages;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
        this.userPool.put(user.getId(), user);
    }
}
