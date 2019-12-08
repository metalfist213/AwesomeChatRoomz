package com.example.awesomechatroomz.activities;

import android.os.Bundle;

import com.example.awesomechatroomz.adapters.ChatRoomsAdapter;
import com.example.awesomechatroomz.components.DaggerLoginComponent;
import com.example.awesomechatroomz.components.LoginComponent;
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

public class ChatMenuActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager layoutManager;

    @Inject
    ChatRoomsAdapter adapter;

    private static final String TAG = "ChatMenuActivity";

    LoginComponent comp;
    private SwipeRefreshLayout swipeRefreshLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        overridePendingTransition(android.R.anim.fade_out, android.R.anim.fade_in);

        setContentView(R.layout.activity_chat_menu);
        recyclerView = findViewById(R.id.chat_room_recycler_view);
        swipeRefreshLayout = findViewById(R.id.chat_room_swipe_refresh);

        layoutManager = new LinearLayoutManager(this);

        this.comp = DaggerLoginComponent.builder().application(getApplication()).roomModule(new RoomModule(getApplication())).build();
        this.comp.inject(this);
        setupRecyclerView();
        setupSwipeRefreshView();

        System.out.println(layoutManager.canScrollVertically());
        Log.d(TAG, "onCreate: " + comp.getLoggedInUser());


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
                adapter.refresh();
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

}
