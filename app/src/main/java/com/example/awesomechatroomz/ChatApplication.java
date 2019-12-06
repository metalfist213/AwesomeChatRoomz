package com.example.awesomechatroomz;

import android.app.Application;

import com.example.awesomechatroomz.components.DaggerHelloWorldComponent;
import com.example.awesomechatroomz.components.HelloWorldComponent;

public class ChatApplication extends Application {
    public HelloWorldComponent helloWorldComponent = DaggerHelloWorldComponent.create();
}
