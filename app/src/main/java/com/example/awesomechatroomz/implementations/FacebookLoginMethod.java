package com.example.awesomechatroomz.implementations;

import android.content.Intent;

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
    private User facebookUser;
    private FacebookException exception;

    @Inject
    public FacebookLoginMethod() {
        /* FACEBOOK */
        LoginManager.getInstance().logOut();

        callbackManager = CallbackManager.Factory.create();
    }


    @Override
    public void prepare(AppCompatActivity activity) {
        LoginButton loginButton = activity.findViewById(R.id.login_button);
        loginButton.setPermissions("email");

        loginButton.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                facebookUser = new User();
                facebookUser.setAvatarURL(Profile.getCurrentProfile().getProfilePictureUri(250, 250).toString());
                facebookUser.setId(Profile.getCurrentProfile().getId());
                facebookUser.setName(Profile.getCurrentProfile().getFirstName());
            }

            @Override
            public void onCancel() {
                // App code
            }

            @Override
            public void onError(FacebookException exception) {
                System.out.println("Error HAPPENED!!!1!");
                FacebookLoginMethod.this.exception = exception;
            }
        });
    }

    @Override
    public User onActivityResult(int requestCode, int resultCode, Intent data) {
        callbackManager.onActivityResult(requestCode, resultCode, data);

        if(exception!=null) throw exception;

        return facebookUser;
    }
}
