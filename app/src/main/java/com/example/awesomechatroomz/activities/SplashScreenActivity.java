package com.example.awesomechatroomz.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.Window;

import com.example.awesomechatroomz.R;
import com.example.awesomechatroomz.components.LoginComponent;
import com.example.awesomechatroomz.implementations.LoginManager;
import com.example.awesomechatroomz.models.User;

import javax.inject.Inject;

import dagger.android.AndroidInjection;
import dagger.android.AndroidInjector;
import dagger.android.DaggerActivity;
import dagger.android.DispatchingAndroidInjector;
import dagger.android.HasAndroidInjector;

public class SplashScreenActivity extends DaggerActivity implements HasAndroidInjector {

    private LoginComponent comp;

    @Inject
    LoginManager loginManager;

    @Inject
    DispatchingAndroidInjector<Object> activityDispatchingAndroidInjector;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        AndroidInjection.inject(this);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_splash_screen);

        this.loginManager.AttemptAutoLogin(new LoginManager.LoginCallback() {
            @Override
            public void OnFinished(User user) {
                if(user!=null) {
                    Intent chatMenu = new Intent(SplashScreenActivity.this, ChatMenuActivity.class);
                    startActivity(chatMenu);
                    overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
                } else {

                    Intent chatMenu = new Intent(SplashScreenActivity.this, MainActivity.class);
                    startActivity(chatMenu);
                    overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
                }
            }
        });
    }
    public AndroidInjector androidInjector() {
        return activityDispatchingAndroidInjector;
    }
}
