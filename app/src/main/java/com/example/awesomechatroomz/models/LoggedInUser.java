package com.example.awesomechatroomz.models;


import com.example.awesomechatroomz.implementations.LoginManager;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class LoggedInUser extends User {

    @Inject
    public LoggedInUser() {

    }
}
