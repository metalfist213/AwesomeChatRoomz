package com.example.awesomechatroomz.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.net.Uri;
import android.os.Bundle;

import com.example.awesomechatroomz.R;
import com.example.awesomechatroomz.activities.fragments.UserChatInputFragment;

public class ChatActivity extends AppCompatActivity implements UserChatInputFragment.OnFragmentInteractionListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);
        System.out.println(getIntent().getStringExtra("chat_room"));


    }


    @Override
    public void onFragmentInteraction(Uri uri) {

    }

    @Override
    public void onTextSend(String text) {

    }

    @Override
    public void onImageUploadRequest() {

    }

    @Override
    public void onCameraUploadRequest() {

    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }
}
