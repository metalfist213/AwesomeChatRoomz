package com.example.awesomechatroomz.adapters;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.RecyclerView;

import com.example.awesomechatroomz.R;
import com.example.awesomechatroomz.implementations.ActiveChatInstance;
import com.example.awesomechatroomz.models.ChatRoom;
import com.example.awesomechatroomz.models.ImageMessage;
import com.example.awesomechatroomz.models.Message;
import com.example.awesomechatroomz.models.TextMessage;
import com.example.awesomechatroomz.models.User;
import com.squareup.picasso.Picasso;

import javax.inject.Inject;

public class ChatMessagesAdapter extends RecyclerView.Adapter<ChatMessagesAdapter.ChatViewHolder> {
    private ActiveChatInstance activeChatInstance;
    private ChatRoom current;

    private static final String TAG = "ChatMessagesAdapter";

    public void setActiveInstance(ActiveChatInstance activeChatInstance) {
        Log.d(TAG, "setActiveInstance() called with: activeChatInstance.room = [" + activeChatInstance.getActiveChatRoom() + "]");
        this.activeChatInstance = activeChatInstance;
    }

    public interface ChatMessagesAdapterListener {
        void onGetOlderDone();
    }

    public void getOlderMessages(ChatMessagesAdapterListener listener) {
        this.getOlderMessages();
        listener.onGetOlderDone();
    }
    public void getOlderMessages() {
        activeChatInstance.loadAmountMessages(50);
    }

    public static class ChatViewHolder extends RecyclerView.ViewHolder {
        public TextView userName;
        public TextView messageDate;
        public TextView messageContent;
        public ImageView userAvatar;
        public ImageView uploadedImage;

        public ChatViewHolder(@NonNull View itemView) {
            super(itemView);
            this.userName = itemView.findViewById(R.id.user_name_textView);
            this.messageDate = itemView.findViewById(R.id.date_textView);
            this.userAvatar = itemView.findViewById(R.id.avatar_imageView);
            this.messageContent = itemView.findViewById(R.id.message_textView);
            this.uploadedImage = itemView.findViewById(R.id.image_message_imageView);
        }
    }


    @Inject
    public ChatMessagesAdapter() {//To be continued.. Pass the data from repository. (Inject)

    }

    public void prepare(final ChatMessagesAdapterListener event) {
        LiveData<ChatRoom> room = activeChatInstance.getChatRoomData();
        Log.d(TAG, "prepare: Observers: "+room.hasActiveObservers());

        //Subscribes to the chat room for changes.
        room.observeForever(new Observer<ChatRoom>() {
            @Override
            public void onChanged(ChatRoom chatRoom) {
                if(chatRoom.getMessages().size() < 50) {
                    getOlderMessages();
                }

                current = chatRoom;
                ChatMessagesAdapter.this.notifyDataSetChanged();
            }
        });
    }

    public void prepare() {
        prepare(null);
    }


    @NonNull
    @Override
    public ChatViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = null;

        //Using the message type, the view is chosen.
        switch(viewType) {
            case Message.TEXT:
                Log.d(TAG, "onCreateViewHolder: About to display text.");
                view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_chat_text, parent, false);
                break;
                
            case Message.IMAGE:
                Log.d(TAG, "onCreateViewHolder: About to display image.");
                view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_chat_image, parent, false);
                break;
                
            default:
                Log.e(TAG, "onCreateViewHolder: ", new IllegalArgumentException("Message type: "+viewType+" is not supported!"));
        }

        return new ChatViewHolder(view);
    }

    @Override
    public int getItemViewType(int position) {
        return current.getMessages().get(position).getMessageType();
    }

    @Override
    public void onBindViewHolder(@NonNull ChatViewHolder holder, int position) {
        Message message = current.getMessages().get(position);
        holder.messageDate.setText(message.getDate().toString());

        User user = current.getUserPool().get(message.getSender());

        if(user!=null) {
            //If the message is from you, it writes "you:" instead of "Name:".
            String userName = user.getId().equals(current.getUser().getId()) ? "You:" : user.getName()+":";
            holder.userName.setText(userName);
            Picasso.get().load(user.getAvatarURI()).resize(100, 100).into(holder.userAvatar);
        }

        //Sets content of the view using the message type.
        switch(message.getMessageType()) {
            case Message.TEXT:
                holder.messageContent.setText(((TextMessage) message).getMessage());
                break;

            case Message.IMAGE:
                Picasso.get().load(((ImageMessage) message).getImageUri()).resize(200, 0).into(holder.uploadedImage);
                break;
        }
    }


    @Override
    public int getItemCount() {
        return current != null ? current.getMessages().size() : 0;
    }
}
