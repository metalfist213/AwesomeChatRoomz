package com.example.awesomechatroomz.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.example.awesomechatroomz.ChatApplication;
import com.example.awesomechatroomz.R;
import com.example.awesomechatroomz.components.HelloWorldComponent;
import com.example.awesomechatroomz.components.IHelloWorld;
import com.facebook.AccessToken;
import com.facebook.AccessTokenTracker;
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
import com.google.firebase.database.FirebaseDatabase;

import java.util.Arrays;

import javax.inject.Inject;


public class MainActivity extends AppCompatActivity {

    HelloWorldComponent worldComponent;
    CallbackManager callbackManager;

    @Inject
    IHelloWorld world;

    HelloWorldComponent comp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        this.comp = ((ChatApplication) getApplicationContext()).helloWorldComponent;

        this.comp.inject(this);

        this.comp.getWorld().say();
        world.say();

        /*
        GoogleSignInOptions options = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN).
                requestIdToken(getString(R.string.default_google_server)).requestEmail().build();

        GoogleSignInClient client = GoogleSignIn.getClient(this, options);
        Intent signInIntent = client.getSignInIntent();
        startActivityForResult(signInIntent, 1);
         */
        LoginManager.getInstance().logOut();

        callbackManager = CallbackManager.Factory.create();

        LoginButton loginButton = (LoginButton) findViewById(R.id.login_button);
        loginButton.setPermissions("email");
        // If you are using in a fragment, call loginButton.setFragment(this);

        // Callback registration
        loginButton.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                // App code
                System.out.println("SUCCESSSSSSsss...!");


                System.out.println("Hello: "+Profile.getCurrentProfile().getFirstName()+"!");
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
        FirebaseDatabase.getInstance().getReference().child("test").setValue("Test Value!!!1!");
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        callbackManager.onActivityResult(requestCode, resultCode, data);
        super.onActivityResult(requestCode, resultCode, data);
    }
}
