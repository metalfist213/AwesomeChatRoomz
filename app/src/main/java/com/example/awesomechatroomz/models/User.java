package com.example.awesomechatroomz.models;

import javax.inject.Inject;

public class User {
    private String avatarURL;
    private String name;
    private String email;

    @Inject
    public User() {

    }

    public String getAvatarURL() {
        return avatarURL;
    }

    public void setAvatarURL(String avatarURL) {
        this.avatarURL = avatarURL;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
