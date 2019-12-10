package com.example.awesomechatroomz.modules;

import com.example.awesomechatroomz.activities.fragments.UserChatInputFragment;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;

@Module
public abstract class FragmentModule {
    @ContributesAndroidInjector
    abstract UserChatInputFragment contributeChatInputFragment();
}
