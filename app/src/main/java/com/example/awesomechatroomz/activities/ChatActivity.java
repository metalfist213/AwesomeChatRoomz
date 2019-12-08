package com.example.awesomechatroomz.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;

import com.example.awesomechatroomz.R;
import com.example.awesomechatroomz.activities.fragments.UserChatInputFragment;
import com.example.awesomechatroomz.components.DaggerLoginComponent;
import com.example.awesomechatroomz.components.LoginComponent;
import com.example.awesomechatroomz.implementations.ChatManager;
import com.example.awesomechatroomz.models.ChatRoom;
import com.example.awesomechatroomz.models.TextMessage;
import com.example.awesomechatroomz.modules.RoomModule;

import javax.inject.Inject;

public class ChatActivity extends AppCompatActivity implements UserChatInputFragment.OnFragmentInteractionListener {
    private LoginComponent comp;

    @Inject
    ChatManager chatManager;

    private ChatRoom room;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);

        this.comp = DaggerLoginComponent.builder().application(getApplication()).roomModule(new RoomModule(getApplication())).build();
        this.comp.inject(this);

        room = new ChatRoom();
        room.setName(getIntent().getStringExtra("chat_room"));

    }


    @Override
    public void onFragmentInteraction(Uri uri) {

    }

    @Override
    public void onTextSend(String text) {
        TextMessage message = new TextMessage();
        message.setMessage(text);
        message.setSender("11005167327994222499");

        chatManager.sendMessage(room, message);
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
