package com.example.awesomechatroomz.models;

import android.net.Uri;

import com.google.firebase.database.Exclude;

import javax.inject.Inject;

public class User {
    private String avatarURL;
    @Exclude
    private Uri avatarURI;
    private String name;
    private String id;

    @Inject
    public User() {

    }

    public void setUser(User user) {
        this.avatarURL = user.avatarURL;
        this.name = user.name;
        this.id = user.id;
    }

    public String getAvatarURL() {
        return avatarURL;
    }

    public Uri getAvatarURI() {
        return avatarURI;
    }


    public void setAvatarURL(String avatarURL) {
        this.avatarURL = avatarURL;
    }

    public void setAvatarURI(Uri avatarURI) {
        this.avatarURI = avatarURI;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "Name: "+this.name+"\nID: "+this.id+"\nAvatarURL: "+avatarURL;
    }
}
