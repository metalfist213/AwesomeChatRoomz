package com.example.awesomechatroomz.implementations;

import android.content.Intent;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;

import com.example.awesomechatroomz.R;
import com.example.awesomechatroomz.interfaces.ILoginMethod;
import com.example.awesomechatroomz.models.User;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.Profile;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;

import javax.inject.Inject;

public class FacebookLoginMethod implements ILoginMethod {
    private CallbackManager callbackManager;
    @Inject
    public FacebookLoginMethod() {
        /* FACEBOOK */
        LoginManager.getInstance().logOut();

        callbackManager = CallbackManager.Factory.create();
    }


    @Override
    public void prepare(AppCompatActivity activity) {
        LoginButton loginButton = (LoginButton) activity.findViewById(R.id.login_button);
        loginButton.setPermissions("email");

        loginButton.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                // App code
                System.out.println("SUCCESSSSSSsss...!");
            }

            @Override
            public void onCancel() {
                // App code
            }

            @Override
            public void onError(FacebookException exception) {
                // App code
            }
        });
    }

    @Override
    public User onActivityResult(int requestCode, int resultCode, Intent data) {
        callbackManager.onActivityResult(requestCode, resultCode, data);
        return new User();
    }
}
