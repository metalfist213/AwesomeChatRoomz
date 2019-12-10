package com.example.awesomechatroomz.components;

import android.app.Application;

import com.example.awesomechatroomz.ChatApplication;
import com.example.awesomechatroomz.activities.ChatActivity;
import com.example.awesomechatroomz.activities.ChatMenuActivity;
import com.example.awesomechatroomz.activities.MainActivity;
import com.example.awesomechatroomz.activities.SplashScreenActivity;
import com.example.awesomechatroomz.implementations.FacebookLoginMethod;
import com.example.awesomechatroomz.models.LoggedInUser;
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
import dagger.android.support.AndroidSupportInjection;
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
    //public LoggedInUser getLoggedInUser();
    void inject(ChatApplication app);
    /*
    public void inject(MainActivity activity);
    public void inject(ChatMenuActivity activity);
    public void inject(SplashScreenActivity activity);
    public void inject(ChatActivity activity);
*/
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
