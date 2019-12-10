package com.example.awesomechatroomz.modules;


import android.app.Application;
import android.content.Context;

import com.example.awesomechatroomz.ChatApplication;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class AppModule {
    @Provides
    public Context provideApplication(Application app) {
        return app;
    }


}
