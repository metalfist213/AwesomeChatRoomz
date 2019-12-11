package com.example.awesomechatroomz.components;

import com.example.awesomechatroomz.ChatApplication;
import com.example.awesomechatroomz.modules.ActivityModule;
import com.example.awesomechatroomz.modules.AppModule;
import com.example.awesomechatroomz.modules.PersistenceModule;
import com.example.awesomechatroomz.modules.RoomModule;
import com.example.awesomechatroomz.modules.ServiceModule;

import javax.inject.Singleton;

import dagger.BindsInstance;
import dagger.Component;
import dagger.android.AndroidInjectionModule;
import dagger.android.AndroidInjector;
import dagger.android.support.AndroidSupportInjectionModule;

@Singleton
@Component(modules={
        ActivityModule.class,
        RoomModule.class,
        PersistenceModule.class,
        AppModule.class,
        ServiceModule.class,
        AndroidInjectionModule.class,
        AndroidSupportInjectionModule.class})
public interface LoginComponent extends AndroidInjector<ChatApplication> {

    void inject(ChatApplication app);
    @Component.Builder
    interface Builder {
        @BindsInstance
        LoginComponent.Builder application(ChatApplication application);

        LoginComponent build();

        LoginComponent.Builder roomModule(RoomModule roomModule);
        LoginComponent.Builder persistenceModule(PersistenceModule roomModule);
        LoginComponent.Builder appModule(AppModule roomModule);
    }

    ChatApplication expose();
}
