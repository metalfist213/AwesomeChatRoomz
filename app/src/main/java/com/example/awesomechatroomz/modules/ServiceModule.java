package com.example.awesomechatroomz.modules;

import com.example.awesomechatroomz.services.ChatRoomSubscriptionService;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;

@Module
public abstract class ServiceModule {
    @ContributesAndroidInjector
    public abstract ChatRoomSubscriptionService providePollService();
}
