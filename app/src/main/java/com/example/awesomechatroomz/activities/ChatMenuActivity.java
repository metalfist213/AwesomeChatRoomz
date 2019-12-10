package com.example.awesomechatroomz.activities;

import android.content.Intent;
import android.os.Bundle;

import com.example.awesomechatroomz.adapters.ChatRoomsAdapter;
import com.example.awesomechatroomz.components.DaggerLoginComponent;
import com.example.awesomechatroomz.components.LoginComponent;
import com.example.awesomechatroomz.implementations.LoginManager;
import com.example.awesomechatroomz.models.ChatRoom;
import com.example.awesomechatroomz.modules.RoomModule;
import com.example.awesomechatroomz.room.SavedInstancesDatabase;
import com.example.awesomechatroomz.room.entities.SavedInstance;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.util.Log;
import android.view.View;

import com.example.awesomechatroomz.R;

import javax.inject.Inject;

import dagger.android.AndroidInjection;
import dagger.android.AndroidInjector;
import dagger.android.DaggerActivity;
import dagger.android.DispatchingAndroidInjector;
import dagger.android.HasAndroidInjector;

public class ChatMenuActivity extends DaggerActivity implements HasAndroidInjector {
    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager layoutManager;
    @Inject
    DispatchingAndroidInjector<Object> activityDispatchingAndroidInjector;
    @Inject
    ChatRoomsAdapter adapter;

    @Inject
    LoginManager loginManager;

    private static final String TAG = "ChatMenuActivity";

    LoginComponent comp;
    private SwipeRefreshLayout swipeRefreshLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        AndroidInjection.inject(this);
        super.onCreate(savedInstanceState);
        overridePendingTransition(android.R.anim.fade_out, android.R.anim.fade_in);

        setContentView(R.layout.activity_chat_menu);
        recyclerView = findViewById(R.id.chat_room_recycler_view);
        swipeRefreshLayout = findViewById(R.id.chat_room_swipe_refresh);

        layoutManager = new LinearLayoutManager(this);

        setupRecyclerView();
        setupSwipeRefreshView();



/*
        SavedInstance i = new SavedInstance();
        i.setDatabaseId(this.comp.getLoggedInUser().getId());
        i.setName(this.comp.getLoggedInUser().getName());
        SavedInstancesDatabase.getInstance(this).savedInstanceDao().deleteAll();
        SavedInstancesDatabase.getInstance(this).savedInstanceDao().insert(i); */
    }

    private void setupRecyclerView() {
        recyclerView.setLayoutManager(layoutManager);
        adapter.setOnClickListener(new ChatRoomsAdapter.ChatRoomEvent() {
            @Override
            public void onChatRoomClicked(ChatRoom room) {
                Intent chatMenu = new Intent(ChatMenuActivity.this, ChatActivity.class);
                chatMenu.putExtra("chat_room", room.getName());
                startActivity(chatMenu);
            }
        });

        recyclerView.setAdapter(adapter);
    }

    private void setupSwipeRefreshView() {
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                adapter.refresh(new ChatRoomsAdapter.ChatRoomRefreshEvent() {
                    @Override
                    public void refresh() {
                        swipeRefreshLayout.setRefreshing(false);
                    }
                });
            }
        });
    }
    public AndroidInjector androidInjector() {
        return activityDispatchingAndroidInjector;
    }

}
