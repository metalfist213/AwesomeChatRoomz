package com.example.awesomechatroomz.implementations;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.awesomechatroomz.models.ChatRoom;
import com.example.awesomechatroomz.models.IMessage;
import com.example.awesomechatroomz.models.TextMessage;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.database.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

public class ChatManager {
    private static final String TAG = "ChatManager";
    private DatabaseReference database;


    @Inject
    public ChatManager(DatabaseReference reference) {
        this.database = reference;
    }

    public void sendMessage(ChatRoom room, IMessage message) {
        switch(message.getMessageType()) {
            case IMessage.TEXT:
                handleSetText(room, (TextMessage) message);
                break;

            case IMessage.IMAGE:
                Log.e(TAG, "sendMessage: ", new IllegalArgumentException("Image send not implemented yet!"));
                handleSendImage(room, message);
                break;

            default:
                Log.e(TAG, "sendMessage: ", new IllegalArgumentException("Message type: "+message.getMessageType()+" not valid!"));
        }
    }

    private void handleSetText(final ChatRoom room, final TextMessage message) {
        final DatabaseReference chatrooms = database.child("chat_rooms");
        chatrooms.orderByChild("name").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for(DataSnapshot shot : dataSnapshot.getChildren()) {
                    if(shot.child("name").getValue().equals(room.getName())) {
                        shot.getRef().child("lastUpdated").setValue(message.getTime());
                        String key = shot.getRef().child("messages").push().getKey();
                        shot.getRef().child("messages/"+key).setValue(message);

                        return;
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    private void handleSendImage(ChatRoom room, IMessage message) {
    }

    public LiveData<List<ChatRoom>> getChatRooms() {
        final DatabaseReference chatrooms = database.child("chat_rooms");
        final MutableLiveData<List<ChatRoom>> chatRoomsList = new MutableLiveData<>();
        chatRoomsList.setValue(new ArrayList<ChatRoom>());

        chatrooms.orderByChild("lastUpdated").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                ChatRoom room;
                ArrayList<ChatRoom> chatRooms = new ArrayList<>();
                for (DataSnapshot shot : dataSnapshot.getChildren()) {
                    room = new ChatRoom();

                    room.setName(shot.child("name").getValue(String.class));
                    room.setDescription(shot.child("description").getValue(String.class));

                    Long lastUpdated = shot.child("lastUpdated").getValue(long.class);

                    room.setLastUpdated(lastUpdated);

                    chatRooms.add(0, room);
                }

                chatRoomsList.postValue(chatRooms);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


        return chatRoomsList;
    }
}
