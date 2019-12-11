package com.example.awesomechatroomz.models;

import android.net.Uri;

import com.google.firebase.database.Exclude;

public class ImageMessage extends Message {

    private String imageUrl;

    @Exclude
    private Uri imageUri;

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

    public Uri getImageUri() {
        return imageUri;
    }

    public void setImageUri(Uri imageUri) {
        this.imageUri = imageUri;
    }
}
