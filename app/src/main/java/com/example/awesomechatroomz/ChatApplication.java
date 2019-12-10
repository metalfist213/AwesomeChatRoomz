package com.example.awesomechatroomz;

import android.app.Activity;
import android.app.Application;

import com.example.awesomechatroomz.components.DaggerLoginComponent;
import com.example.awesomechatroomz.components.LoginComponent;
import com.example.awesomechatroomz.modules.AppModule;
import com.example.awesomechatroomz.modules.RoomModule;

import javax.inject.Inject;

import dagger.android.AndroidInjector;
import dagger.android.DaggerApplication;
import dagger.android.DispatchingAndroidInjector;
import dagger.android.HasAndroidInjector;

public class ChatApplication extends DaggerApplication implements HasAndroidInjector {

    @Inject
    DispatchingAndroidInjector<Activity> dispatchingAndroidInjector;

    public DispatchingAndroidInjector<Activity> getDispatchingAndroidInjector() {
        return dispatchingAndroidInjector;
    }

    private LoginComponent comp;

    @Override
    protected AndroidInjector<? extends DaggerApplication> applicationInjector() {
        comp = DaggerLoginComponent.builder().application(this).roomModule(new RoomModule(this)).build();
        return comp;
    }
}
