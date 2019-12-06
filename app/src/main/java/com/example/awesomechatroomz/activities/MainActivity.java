package com.example.awesomechatroomz.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.example.awesomechatroomz.ChatApplication;
import com.example.awesomechatroomz.R;
import com.example.awesomechatroomz.components.DaggerLoginComponent;
import com.example.awesomechatroomz.components.HelloWorldComponent;
import com.example.awesomechatroomz.components.LoginComponent;
import com.example.awesomechatroomz.implementations.FacebookLoginMethod;
import com.example.awesomechatroomz.implementations.GoogleLoginMethod;
import com.example.awesomechatroomz.interfaces.IHelloWorld;
import com.example.awesomechatroomz.models.User;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.Profile;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.firebase.database.FirebaseDatabase;

import java.util.List;

import javax.inject.Inject;


public class MainActivity extends AppCompatActivity {

    HelloWorldComponent worldComponent;

    LoginComponent comp;

    @Inject
    FacebookLoginMethod facebookLogin;
    @Inject
    GoogleLoginMethod googleLoginMethod;

    @Inject
    IHelloWorld test;

    private List<CallbackManager> callbackManagers;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //this.callbackManagers = new List<CallbackManager>();
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        this.comp = DaggerLoginComponent.builder().build();

        this.comp.inject(this);

        this.comp.getLoggedInUser().setEmail("Silverleaves13@gmail.com");
        System.out.println("EMAIL: "+this.comp.getLoggedInUser().getEmail());
        test.say();

        facebookLogin.prepare(this);
        googleLoginMethod.prepare(this);







        //FirebaseDatabase.getInstance().getReference().child("test").setValue("Test Value!!!1!");


    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        facebookLogin.onActivityResult(requestCode, resultCode, data);
        try {
            User usr = googleLoginMethod.onActivityResult(requestCode, resultCode, data);
            System.out.println("USER: "+usr.getName());
        } catch (ApiException e) {
            e.printStackTrace();
        }
        super.onActivityResult(requestCode, resultCode, data);
    }
}
