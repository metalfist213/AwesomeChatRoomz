package com.example.awesomechatroomz.implementations;

import android.graphics.Bitmap;
import android.media.Image;
import android.net.Uri;

import androidx.annotation.NonNull;

import com.example.awesomechatroomz.models.ChatRoom;
import com.example.awesomechatroomz.models.IMessage;
import com.example.awesomechatroomz.models.ImageMessage;
import com.example.awesomechatroomz.models.LoggedInUser;
import com.example.awesomechatroomz.models.TextMessage;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import java.io.IOException;
import java.util.UUID;
import java.util.concurrent.Executors;

import javax.inject.Inject;

public class ActiveChatManager {
    private static final String TAG = "ActiveChatManager";

    private DatabaseReference database;
    private ChatRoom activeChatRoom;

    private LoggedInUser loggedInUser;
    private ImageManager imageManager;

    @Inject
    public ActiveChatManager(DatabaseReference reference, LoggedInUser loggedInUser, ImageManager imageManager) {
        this.database = reference;
        this.loggedInUser = loggedInUser;
        this.imageManager = imageManager;
    }

    public void sendMessage(String message) {
        TextMessage textMessageModel = new TextMessage();
        textMessageModel.setMessage(message);
        textMessageModel.setSender(loggedInUser.getId());
        handleSetText(textMessageModel);
    }


    private void handleSetText(final TextMessage message) {
        final DatabaseReference chatrooms = database.child("chat_rooms");
        chatrooms.orderByChild("name").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for(DataSnapshot shot : dataSnapshot.getChildren()) {
                    if(shot.child("name").getValue().equals(activeChatRoom.getName())) {
                        shot.getRef().child("lastUpdated").setValue(message.getTime());
                        String key = shot.getRef().child("messages").push().getKey();
                        shot.getRef().child("messages/"+key).setValue(message);
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    private String getRandomID() {
        return UUID.randomUUID().toString();
    }

    public void sendImage(final Uri uri) throws IOException {
        final ImageMessage imageMessage = new ImageMessage();
        imageMessage.setImageUrl("messages/"+getRandomID());

        Executors.newSingleThreadExecutor().submit(new Runnable() {
            @Override
            public void run() {
                try {
                    imageManager.PutFile(imageMessage.getImageUrl(), uri).addOnCompleteListener(new OnCompleteListener<Uri>() {
                        @Override
                        public void onComplete(@NonNull Task<Uri> task) {
                            handleSendImage(imageMessage);
                        }
                    });
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
    }
    public void sendImage(final Bitmap bit) throws IOException {
        final ImageMessage imageMessage = new ImageMessage();
        imageMessage.setImageUrl("messages/"+getRandomID());

        Executors.newSingleThreadExecutor().submit(new Runnable() {
            @Override
            public void run() {
                try {
                    imageManager.PutFile(imageMessage.getImageUrl(), bit).addOnCompleteListener(new OnCompleteListener<Uri>() {
                        @Override
                        public void onComplete(@NonNull Task<Uri> task) {
                            handleSendImage(imageMessage);
                        }
                    });
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    private void handleSendImage(final ImageMessage message) {
        final DatabaseReference chatrooms = database.child("chat_rooms");
        chatrooms.orderByChild("name").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for(DataSnapshot shot : dataSnapshot.getChildren()) {
                    if(shot.child("name").getValue().equals(activeChatRoom.getName())) {
                        shot.getRef().child("lastUpdated").setValue(message.getTime());
                        String key = shot.getRef().child("messages").push().getKey();
                        shot.getRef().child("messages/"+key).setValue(message);
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    public ChatRoom getActiveChatRoom() {
        return activeChatRoom;
    }

    public void setActiveChatRoom(ChatRoom activeChatRoom) {
        this.activeChatRoom = activeChatRoom;
    }
}
