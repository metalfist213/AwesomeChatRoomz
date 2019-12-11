package com.example.awesomechatroomz.modules;

import com.example.awesomechatroomz.activities.ChatActivity;
import com.example.awesomechatroomz.activities.ChatMenuActivity;
import com.example.awesomechatroomz.activities.LoginScreenActivity;
import com.example.awesomechatroomz.activities.SplashScreenActivity;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;

@Module
public abstract class ActivityModule {
    @ContributesAndroidInjector
    abstract SplashScreenActivity contributeSplashScreenActivity();
    @ContributesAndroidInjector
    abstract LoginScreenActivity contributeMainActivity();
    @ContributesAndroidInjector
    abstract ChatMenuActivity contributeChatMenuActivity();
    @ContributesAndroidInjector(modules = {FragmentModule.class})
    abstract ChatActivity contributeChatActivity();
}
