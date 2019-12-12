package com.example.awesomechatroomz.domain;

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
    private List<ActiveChatInstance> subscribedTo;
    private DatabaseReference reference;
    private LoggedInUser loggedInUser;
    private ImageManager imageManager;

    @Inject
    public ActiveChatManager(DatabaseReference reference, LoggedInUser loggedInUser, ImageManager imageManager) {
        this.subscribedTo = new ArrayList<>();
        this.reference = reference;
        this.loggedInUser = loggedInUser;
        this.imageManager = imageManager;
    }

    public boolean alreadySubscribedTo(ActiveChatInstance room) {
        return subscribedTo.contains(room);
    }

    /**
     * Creates a new instance. Has the responsibility of communication to the database.
     * @param room
     * @return newly created instances.
     */
    public ActiveChatInstance create(ChatRoom room) {
        Log.d(TAG, "create() called with: room = [" + room + "]");
        ActiveChatInstance instance = new ActiveChatInstance(reference, loggedInUser, imageManager);
        instance.setActiveChatRoom(room);
        return instance;
    }

    /**
     * Gets the list of chat instances, the user has subscribed to.
     * @return
     */
    public List<ActiveChatInstance> getSubscribedTo() {
        return subscribedTo;
    }
}
