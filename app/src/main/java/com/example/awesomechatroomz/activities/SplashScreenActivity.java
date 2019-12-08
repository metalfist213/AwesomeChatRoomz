package com.example.awesomechatroomz.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Bundle;
import android.transition.ChangeBounds;
import android.transition.Fade;
import android.view.Window;

import com.example.awesomechatroomz.R;
import com.example.awesomechatroomz.components.DaggerLoginComponent;
import com.example.awesomechatroomz.components.LoginComponent;
import com.example.awesomechatroomz.implementations.LoginManager;
import com.example.awesomechatroomz.models.User;
import com.example.awesomechatroomz.modules.RoomModule;

import javax.inject.Inject;

public class SplashScreenActivity extends AppCompatActivity {

    private LoginComponent comp;

    @Inject
    LoginManager loginManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_splash_screen);
        this.comp = DaggerLoginComponent.builder().application(getApplication()).roomModule(new RoomModule(getApplication())).build();

        this.comp.inject(this);

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
}
