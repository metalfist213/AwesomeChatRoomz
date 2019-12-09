package com.example.awesomechatroomz.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.app.AlertDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.provider.MediaStore;

import com.example.awesomechatroomz.R;
import com.example.awesomechatroomz.activities.fragments.UserChatInputFragment;
import com.example.awesomechatroomz.adapters.ChatMessagesAdapter;
import com.example.awesomechatroomz.adapters.ChatRoomsAdapter;
import com.example.awesomechatroomz.components.DaggerLoginComponent;
import com.example.awesomechatroomz.components.LoginComponent;
import com.example.awesomechatroomz.implementations.ActiveChatInstance;
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

    ActiveChatInstance chatInstance;

    @Inject
    LoginManager loginManager;

    private ChatRoom room;

    @Inject
    ImageManager imgManager;

    @Inject
    ChatMessagesAdapter adapter;

    private RecyclerView recyclerView;

    private SwipeRefreshLayout swipeRefreshLayout;
    private LinearLayoutManager layoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);

        recyclerView = findViewById(R.id.chat_activity_recyclerView);
        swipeRefreshLayout = findViewById(R.id.chat_activity_swipeLayout);
        layoutManager = new LinearLayoutManager(this);

        this.comp = DaggerLoginComponent.builder().application(getApplication()).roomModule(new RoomModule(getApplication())).build();
        this.comp.inject(this);


        loginManager.AttemptAutoLogin(new LoginManager.LoginCallback() {
            @Override
            public void OnFinished(User user) {
                room = new ChatRoom(user);
                room.setName(getIntent().getStringExtra("chat_room"));
                chatInstance = chatManager.create(room);
                adapter.setActiveInstance(chatInstance);
                System.out.println("Attempt Login");

                //Adapter must subscribe from main thread.
                new Handler(getApplicationContext().getMainLooper()).post(new Runnable() {
                    @Override
                    public void run() {
                        adapter.prepare();
                    }
                });
            }
        });

        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                adapter.getOlderMessages(new ChatMessagesAdapter.ChatMessagesAdapterListener() {
                    @Override
                    public void onGetOlderDone() {
                        swipeRefreshLayout.setRefreshing(false);
                    }
                });
            }
        });


        recyclerView.setLayoutManager(layoutManager);

        recyclerView.setAdapter(adapter);
    }


    @Override
    public void onFragmentInteraction(Uri uri) {

    }

    @Override
    public void onTextSend(String text) {
        chatInstance.sendMessage(text);
    }

    @Override
    public void onImageUploadRequest(final Uri imageUri) {
        try {
            chatInstance.sendImage(imageUri);
        } catch (IOException e) {
            new AlertDialog.Builder(this).setTitle("Image Message Failed!").setMessage("Something unexpected happened while uploading your picture..\nPlease try again.").show();
            e.printStackTrace();
        }
    }

    @Override
    public void onCameraUploadRequest(Bitmap imageBitmap) {
        try {
            chatInstance.sendImage(imageBitmap);
        } catch (IOException e) {
            new AlertDialog.Builder(this).setTitle("Image Message Failed!").setMessage("Something unexpected happened while uploading your picture..\nPlease try again.").show();
            e.printStackTrace();
        }
    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }
}
