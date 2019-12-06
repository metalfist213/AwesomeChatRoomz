package com.example.awesomechatroomz.models;


import com.example.awesomechatroomz.modules.LoggedUserModule;

import javax.inject.Inject;
import javax.inject.Singleton;


public class LoggedInUser extends User {
    @Inject
    public LoggedInUser() {

    }
}
