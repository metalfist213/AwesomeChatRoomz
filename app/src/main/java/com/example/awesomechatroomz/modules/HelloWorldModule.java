package com.example.awesomechatroomz.modules;

import com.example.awesomechatroomz.interfaces.IHelloWorld;
import com.example.awesomechatroomz.implementations.HelloWorld;

import dagger.Binds;
import dagger.Module;

@Module
public abstract class HelloWorldModule {
    @Binds
    public abstract IHelloWorld bindHelloWorld(HelloWorld world);
}
