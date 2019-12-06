package com.example.awesomechatroomz.components;

import com.example.awesomechatroomz.activities.MainActivity;
import com.example.awesomechatroomz.implementations.FacebookLoginMethod;

import dagger.Component;

@Component
public interface LoginComponent {
    //public void inject(MainActivity activity);
    public FacebookLoginMethod getFacebookLoginMethod();
}
