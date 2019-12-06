package com.example.awesomechatroomz.implementations;

import android.content.Intent;

import com.example.awesomechatroomz.interfaces.ILoginMethod;
import com.example.awesomechatroomz.models.User;

import javax.inject.Inject;

public class FacebookLoginMethod implements ILoginMethod {

    @Inject
    public FacebookLoginMethod() {

    }

    @Override
    public User onActivityResult(int requestCode, int resultCode, Intent data) {
        return new User();
    }
}
