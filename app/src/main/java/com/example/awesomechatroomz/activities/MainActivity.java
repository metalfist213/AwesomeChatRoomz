package com.example.awesomechatroomz.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.example.awesomechatroomz.R;
import com.example.awesomechatroomz.components.DaggerLoginComponent;
import com.example.awesomechatroomz.components.HelloWorldComponent;
import com.example.awesomechatroomz.components.LoginComponent;
import com.example.awesomechatroomz.implementations.FacebookLoginMethod;
import com.example.awesomechatroomz.implementations.GoogleLoginMethod;
import com.example.awesomechatroomz.implementations.LoginManager;
import com.example.awesomechatroomz.interfaces.AsyncTaskCallback;
import com.example.awesomechatroomz.interfaces.IHelloWorld;
import com.example.awesomechatroomz.models.User;
import com.facebook.CallbackManager;
import com.facebook.FacebookException;
import com.google.android.gms.common.api.ApiException;

import java.util.List;

import javax.inject.Inject;


public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";
    HelloWorldComponent worldComponent;

    LoginComponent comp;

    @Inject
    FacebookLoginMethod facebookLogin;
    @Inject
    GoogleLoginMethod googleLoginMethod;

    @Inject
    IHelloWorld test;

    @Inject
    LoginManager loginManager;

    private List<CallbackManager> callbackManagers;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //this.callbackManagers = new List<CallbackManager>();
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        this.comp = DaggerLoginComponent.builder().build();

        this.comp.inject(this);

        facebookLogin.prepare(this);
        googleLoginMethod.prepare(this);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        User user = null;
        try {
            user = facebookLogin.onActivityResult(requestCode, resultCode, data);
        } catch(FacebookException e) {
            displayConnectionError("Facebook Connect", "Could not connect to Facebook. Are you connected to the internet?");
            Log.d(TAG, "onActivityResult: FacebookException happened. Check if you're connected to the internet.\n"+e);
        }

        if(user==null) {
            try {
                user = googleLoginMethod.onActivityResult(requestCode, resultCode, data);
            } catch (ApiException e) {
                displayConnectionError("Google Connect", "Could not connect to Google. Are you connected to the internet?");
                Log.d(TAG, "onActivityResult: FacebookException happened. Check if you're connected to the internet.\n"+e);
            }
        }

        if(user!=null) {
            comp.getLoggedInUser().setUser(user);
            login(user);
        }

        super.onActivityResult(requestCode, resultCode, data);
    }

    private void login(User user) {

        loginManager.updateUserInformation(user, new AsyncTaskCallback() {
            @Override
            public void OnPreExecute() {
                super.OnPreExecute();
            }

            @Override
            public void onPostExecute() {
                Intent chatMenu = new Intent(MainActivity.this, ChatMenuActivity.class);
                startActivity(chatMenu);
            }
        });
    }

    private void displayConnectionError(String title, String message) {
        new AlertDialog.Builder(this).setTitle(title).setMessage(message).show();
    }
}
