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
import java.util.Objects;
import java.util.UUID;
import java.util.concurrent.Executors;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class ActiveChatManager {
    private static final String TAG = "ActiveChatManager";
    private DatabaseReference chatRoomPath;

    private DatabaseReference database;
    private ChatRoom activeChatRoom;

    private LoggedInUser loggedInUser;
    private ImageManager imageManager;

    MutableLiveData<ChatRoom> liveData;

    @Inject
    public ActiveChatManager(DatabaseReference reference, LoggedInUser loggedInUser, ImageManager imageManager) {
        this.database = reference;
        this.loggedInUser = loggedInUser;
        this.imageManager = imageManager;
        this.liveData = new MutableLiveData<>();
        this.liveData.postValue(new ChatRoom()); //Empty room for initialization.
    }

    public LiveData<ChatRoom> getChatRoomData() {
        return liveData;
    }

    public void loadAmountMessages(int amount) {

        chatRoomPath.child("messages").orderByKey().limitToLast(amount).endAt(activeChatRoom.getMessages().get(0).getId()).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                //Make list as a buffer.
                ArrayList<DataSnapshot> results = new ArrayList<>();
                for (DataSnapshot shot : dataSnapshot.getChildren()) {
                    results.add(0, shot);
                }
                results.remove(0); //Remove the already existing element from the list.

                //Pass each in modified order.
                for(DataSnapshot res : results) {
                    System.out.println(res);
                    saveMessageFromSnapshot(res, false);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    public interface ChatMessagesEvent {
        public void onMessagesChanged();
    }

    private void saveMessageFromSnapshot(DataSnapshot dataSnapshot, boolean first) {
        Message message = null;
        if (Objects.equals(dataSnapshot.child("messageType").getValue(int.class), Message.TEXT)) {
            message = dataSnapshot.getValue(TextMessage.class);
        } else {
            message = dataSnapshot.getValue(ImageMessage.class);
        }

        message.setId(dataSnapshot.getKey());

        if (first)
            activeChatRoom.getMessages().add(message);
        else
            activeChatRoom.getMessages().add(0, message);

        updateUserPool(message);


        if(!activeChatRoom.getUserPool().containsKey(message.getSender())) {
            updateUserPool(message);
        } else {
            liveData.postValue(activeChatRoom);
        }
        //POST FOR UI
    }

    private void updateUserPool(Message message) {
        database.child("users/"+message.getSender()).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                final User user = dataSnapshot.getValue(User.class);
                activeChatRoom.getUserPool().put(user.getId(), user);

                try {
                    imageManager.downloadFile("avatars/" + user.getId(), new ImageManager.URLRequestListener() {
                        @Override
                        public void onSuccess(Uri uri) {
                            user.setAvatarURI(uri);
                            liveData.postValue(activeChatRoom);
                        }
                    });
                } catch (IOException e) {
                    Log.e(TAG, "onDataChange: ", e);
                    e.printStackTrace();
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    private void listenForChanges() {
        chatRoomPath.child("messages").orderByChild("time").limitToLast(1).addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                saveMessageFromSnapshot(dataSnapshot, true);
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    public void sendMessage(String message) {
        TextMessage textMessageModel = new TextMessage();
        textMessageModel.setMessage(message);
        handleSendMessage(textMessageModel);
    }


    private void handleSendMessage(final Message message) {
        final DatabaseReference chatrooms = database.child("chat_rooms");
        message.setSender(activeChatRoom.getUser().getId());
        chatrooms.orderByChild("name").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot shot : dataSnapshot.getChildren()) {
                    if (shot.child("name").getValue().equals(activeChatRoom.getName())) {

                        shot.getRef().child("lastUpdated").setValue(message.getTime());
                        String key = shot.getRef().child("messages").push().getKey();
                        shot.getRef().child("messages/" + key).setValue(message);
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
        imageMessage.setImageUrl("messages/" + getRandomID());

        Executors.newSingleThreadExecutor().submit(new Runnable() {
            @Override
            public void run() {
                try {
                    imageManager.PutFile(imageMessage.getImageUrl(), uri).addOnCompleteListener(new OnCompleteListener<Uri>() {
                        @Override
                        public void onComplete(@NonNull Task<Uri> task) {
                            handleSendMessage(imageMessage);
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
        imageMessage.setImageUrl("messages/" + getRandomID());

        Executors.newSingleThreadExecutor().submit(new Runnable() {
            @Override
            public void run() {
                try {
                    imageManager.PutFile(imageMessage.getImageUrl(), bit).addOnCompleteListener(new OnCompleteListener<Uri>() {
                        @Override
                        public void onComplete(@NonNull Task<Uri> task) {
                            handleSendMessage(imageMessage);
                        }
                    });
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    public ChatRoom getActiveChatRoom() {
        return activeChatRoom;
    }

    public void setActiveChatRoom(ChatRoom activeChatRoom) {
        this.activeChatRoom = activeChatRoom;
        setChatRoomPath();
    }

    private void setChatRoomPath() {
        final DatabaseReference chatrooms = database.child("chat_rooms");
        chatrooms.orderByChild("name").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot shot : dataSnapshot.getChildren()) {
                    if (shot.child("name").getValue().equals(activeChatRoom.getName())) {
                        chatRoomPath = shot.getRef().getRef();
                        listenForChanges();
                        return;
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}
