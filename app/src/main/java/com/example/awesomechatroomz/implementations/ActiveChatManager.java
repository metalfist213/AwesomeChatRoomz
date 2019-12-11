package com.example.awesomechatroomz.implementations;

import android.util.Log;

import com.example.awesomechatroomz.models.ChatRoom;
import com.example.awesomechatroomz.models.LoggedInUser;
import com.google.firebase.database.DatabaseReference;

import java.util.ArrayList;
import java.util.List;

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
