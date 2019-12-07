package com.example.awesomechatroomz.activities;

import android.os.Bundle;

import com.example.awesomechatroomz.components.DaggerLoginComponent;
import com.example.awesomechatroomz.components.LoginComponent;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.util.Log;
import android.view.View;

import com.example.awesomechatroomz.R;

public class ChatMenuActivity extends AppCompatActivity {
    private static final String TAG = "ChatMenuActivity";
    
    LoginComponent comp;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat_menu);
        this.comp = DaggerLoginComponent.builder().build();

        this.comp.inject(this);
        Log.d(TAG, "onCreate: "+comp.getLoggedInUser());

    }

}
