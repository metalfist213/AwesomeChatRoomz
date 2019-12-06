package com.example.awesomechatroomz.components;

import com.example.awesomechatroomz.activities.MainActivity;
import com.example.awesomechatroomz.interfaces.IHelloWorld;
import com.example.awesomechatroomz.modules.HelloWorldModule;

import dagger.Component;

@Component(modules={HelloWorldModule.class })
public interface HelloWorldComponent {
    //public void inject(MainActivity activity);
    public IHelloWorld getWorld();
}
