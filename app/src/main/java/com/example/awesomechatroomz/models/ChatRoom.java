package com.example.awesomechatroomz.models;

import java.util.List;

public class ChatRoom {
    private long lastUpdated;
    private String name;
    private List<String> usersId;
    private String description;

    public ChatRoom() {
        this.usersId = usersId;
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
}
