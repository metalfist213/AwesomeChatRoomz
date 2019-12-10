package com.example.awesomechatroomz.services;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.os.Build;
import android.os.IBinder;
import android.util.Log;

import androidx.core.app.NotificationCompat;

import com.example.awesomechatroomz.R;
import com.example.awesomechatroomz.implementations.ActiveChatInstance;
import com.example.awesomechatroomz.implementations.ActiveChatManager;
import com.example.awesomechatroomz.models.ChatRoom;
import com.example.awesomechatroomz.models.Message;
import com.example.awesomechatroomz.models.TextMessage;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.Executors;

import javax.inject.Inject;

import dagger.android.AndroidInjection;


public class ChatRoomSubscriptionService extends Service {
    private static final String TAG = "ChatRoomSubscriptionSer";
    private Intent intent;
    private Set<ActiveChatInstance> subscribedTo;

    private NotificationManager notificationManager;

    private static String CHANNEL_ID = "ACR-Service";

    @Inject
    ActiveChatManager activeChatManager;
    private boolean running;



    @Inject
    public ChatRoomSubscriptionService() {
        subscribedTo = new HashSet<>();
    }

    @Override
    public void onCreate() {
        AndroidInjection.inject(this);

        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel(CHANNEL_ID, "Awesome Chat Roomz",NotificationManager.IMPORTANCE_DEFAULT);
            notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
            notificationManager.createNotificationChannel(channel);
            System.out.println("Setting up notifcation man");
        }


        Log.d(TAG, "onCreate() called, ActiveChatManager: "+activeChatManager);

        running = true;
        Executors.newSingleThreadExecutor().submit(new Runnable() {
            @Override
            public void run() {
                while(running) {
                    try {
                        Thread.sleep(1000);
                        Log.i(TAG, "run: Subscribed to:");
                        for(ActiveChatInstance i : activeChatManager.getSubscribedTo()) {
                            if(!subscribedTo.contains(i)) {
                                subscribe(i);
                                subscribedTo.add(i);
                            }

                        }
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        System.out.println("Starting Service...");

        return START_NOT_STICKY;
    }

    private void subscribe(final ActiveChatInstance i) {
        i.setEventHandler(new ActiveChatInstance.ChatMessagesEvent() {
            @Override
            public void onMessageReceived(Message message) {
                System.out.println("SERVICE: message received: "+message);
                pushNotification(i.getActiveChatRoom(), message);
            }
        });

    }

    private void pushNotification(ChatRoom room, Message message) {
        String messageContent = (message.getMessageType() == Message.IMAGE) ? "an image" : ((TextMessage) message).getMessage();

        Notification notification = new NotificationCompat.Builder(this, CHANNEL_ID)
                .setSmallIcon(R.drawable.common_google_signin_btn_icon_light)
                .setContentTitle(room.getName())
                .setContentText(room.getUserPool().get(message.getSender()).getName()+" sent: "+messageContent)
                .setPriority(NotificationCompat.PRIORITY_DEFAULT).build();

        System.out.println("Pushing notification...");

        notificationManager.notify(2323232, notification);
    }


    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
}
