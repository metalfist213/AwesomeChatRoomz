package com.example.awesomechatroomz.implementations;

import android.graphics.Bitmap;
import android.net.Uri;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.awesomechatroomz.models.ChatRoom;
import com.example.awesomechatroomz.models.ImageMessage;
import com.example.awesomechatroomz.models.LoggedInUser;
import com.example.awesomechatroomz.models.Message;
import com.example.awesomechatroomz.models.TextMessage;
import com.example.awesomechatroomz.models.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.UUID;
import java.util.concurrent.Executors;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class ActiveChatManager {
    private static final String TAG = "ActiveChatManager";
    private List<ActiveChatInstance> chatInstanceList;
    private List<ActiveChatInstance> subscribedTo;
    private DatabaseReference reference;
    private LoggedInUser loggedInUser;
    private ImageManager imageManager;

    @Inject
    public ActiveChatManager(DatabaseReference reference, LoggedInUser loggedInUser, ImageManager imageManager) {
        chatInstanceList = new ArrayList<>();
        this.subscribedTo = new ArrayList<>();
        this.reference = reference;
        this.loggedInUser = loggedInUser;
        this.imageManager = imageManager;
    }

    public boolean alreadySubscribedTo(ActiveChatInstance room) {
        return subscribedTo.contains(room);
    }

    public ActiveChatInstance create(ChatRoom room) {
        Log.d(TAG, "create() called with: room = [" + room + "]");
        ActiveChatInstance instance = new ActiveChatInstance(reference, loggedInUser, imageManager);
        instance.setActiveChatRoom(room);
        return instance;
    }

    public ActiveChatInstance getChatInstance(ChatRoom room) {
        for(ActiveChatInstance activeChatInstance : chatInstanceList) { // Iterative search - not many instances will be running anyways.
            if(activeChatInstance.getActiveChatRoom().equals(room)) {
                return activeChatInstance;
            }
        }
        return null;
    }

    public List<ActiveChatInstance> getSubscribedTo() {
        return subscribedTo;
    }
}
