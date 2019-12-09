package com.example.awesomechatroomz.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;

import com.example.awesomechatroomz.R;
import com.example.awesomechatroomz.activities.fragments.UserChatInputFragment;
import com.example.awesomechatroomz.components.DaggerLoginComponent;
import com.example.awesomechatroomz.components.LoginComponent;
import com.example.awesomechatroomz.implementations.ActiveChatManager;
import com.example.awesomechatroomz.implementations.ChatManager;
import com.example.awesomechatroomz.implementations.ImageManager;
import com.example.awesomechatroomz.implementations.LoginManager;
import com.example.awesomechatroomz.models.ChatRoom;
import com.example.awesomechatroomz.models.TextMessage;
import com.example.awesomechatroomz.models.User;
import com.example.awesomechatroomz.modules.RoomModule;

import java.io.IOException;
import java.util.concurrent.Executors;

import javax.inject.Inject;

public class ChatActivity extends AppCompatActivity implements UserChatInputFragment.OnFragmentInteractionListener {
    private LoginComponent comp;

    @Inject
    ActiveChatManager chatManager;

    @Inject
    LoginManager loginManager;

    private ChatRoom room;

    @Inject
    ImageManager imgManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);

        this.comp = DaggerLoginComponent.builder().application(getApplication()).roomModule(new RoomModule(getApplication())).build();
        this.comp.inject(this);


        loginManager.AttemptAutoLogin(new LoginManager.LoginCallback() {
            @Override
            public void OnFinished(User user) {
                room = new ChatRoom(user);
                room.setName(getIntent().getStringExtra("chat_room"));

                chatManager.setActiveChatRoom(room);
            }
        });
    }


    @Override
    public void onFragmentInteraction(Uri uri) {

    }

    @Override
    public void onTextSend(String text) {
        chatManager.sendMessage(text);
    }

    @Override
    public void onImageUploadRequest(final Uri imageUri) {
        try {
            chatManager.sendImage(imageUri);
        } catch (IOException e) {
            new AlertDialog.Builder(this).setTitle("Image Message Failed!").setMessage("Something unexpected happened while uploading your picture..\nPlease try again.").show();
            e.printStackTrace();
        }
    }

    @Override
    public void onCameraUploadRequest(Bitmap imageBitmap) {
        try {
            chatManager.sendImage(imageBitmap);
        } catch (IOException e) {
            new AlertDialog.Builder(this).setTitle("Image Message Failed!").setMessage("Something unexpected happened while uploading your picture..\nPlease try again.").show();
            e.printStackTrace();
        }
    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }
}
