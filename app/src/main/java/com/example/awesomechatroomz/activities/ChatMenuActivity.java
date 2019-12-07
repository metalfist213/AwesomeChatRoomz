package com.example.awesomechatroomz.activities;

import android.os.Bundle;

import com.example.awesomechatroomz.components.DaggerLoginComponent;
import com.example.awesomechatroomz.components.LoginComponent;
import com.example.awesomechatroomz.modules.RoomModule;
import com.example.awesomechatroomz.room.SavedInstancesDatabase;
import com.example.awesomechatroomz.room.entities.SavedInstance;
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
        this.comp = DaggerLoginComponent.builder().application(getApplication()).roomModule(new RoomModule(getApplication())).build();

        this.comp.inject(this);
        Log.d(TAG, "onCreate: "+comp.getLoggedInUser());
/*
        SavedInstance i = new SavedInstance();
        i.setDatabaseId(this.comp.getLoggedInUser().getId());
        i.setName(this.comp.getLoggedInUser().getName());
        SavedInstancesDatabase.getInstance(this).savedInstanceDao().deleteAll();
        SavedInstancesDatabase.getInstance(this).savedInstanceDao().insert(i); */
    }

}
