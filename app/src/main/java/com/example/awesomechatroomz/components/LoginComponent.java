package com.example.awesomechatroomz.components;

import com.example.awesomechatroomz.activities.ChatMenuActivity;
import com.example.awesomechatroomz.activities.MainActivity;
import com.example.awesomechatroomz.implementations.FacebookLoginMethod;
import com.example.awesomechatroomz.models.LoggedInUser;
import com.example.awesomechatroomz.models.User;
import com.example.awesomechatroomz.modules.HelloWorldModule;
import com.example.awesomechatroomz.modules.LoggedUserModule;

import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component(modules={LoggedUserModule.class })
public interface LoginComponent {
    public User getLoggedInUser();
    public void inject(MainActivity activity);
    public void inject(ChatMenuActivity activity);
    public FacebookLoginMethod getFacebookLoginMethod();
}
