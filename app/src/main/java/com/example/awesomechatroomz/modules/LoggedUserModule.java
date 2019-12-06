package com.example.awesomechatroomz.modules;

import com.example.awesomechatroomz.implementations.HelloWorld;
import com.example.awesomechatroomz.interfaces.IHelloWorld;
import com.example.awesomechatroomz.models.LoggedInUser;
import com.example.awesomechatroomz.models.User;

import dagger.Binds;
import dagger.Module;
import dagger.Provides;

@Module
public class LoggedUserModule {
    private static User currentlyLoggedUser;

    @Provides
    public User bindLoggedInUser(LoggedInUser user) {
        if(currentlyLoggedUser!=null) return this.currentlyLoggedUser;
        currentlyLoggedUser = user;
        return user;
    }

    @Provides
    public IHelloWorld bindTestString() {
        return new HelloWorld();
    }
}
