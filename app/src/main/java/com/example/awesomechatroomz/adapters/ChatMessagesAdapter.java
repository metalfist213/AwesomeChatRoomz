package com.example.awesomechatroomz.adapters;

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
import com.example.awesomechatroomz.implementations.ActiveChatManager;
import com.example.awesomechatroomz.models.ChatRoom;
import com.example.awesomechatroomz.models.Message;
import com.example.awesomechatroomz.models.TextMessage;
import com.example.awesomechatroomz.models.User;
import com.squareup.picasso.Picasso;

import javax.inject.Inject;

public class ChatMessagesAdapter extends RecyclerView.Adapter<ChatMessagesAdapter.ChatViewHolder> {
    private ChatRoomEvent itemClickListener;
    private ActiveChatManager activeChatManager;
    private ChatRoom current;

    public interface ChatRoomEvent {
        public void onChatRoomClicked(ChatRoom room);
    }
    public interface ChatMessagesAdapterListener {
        public void onGetOlderDone();
    }

    public void getOlderMessages(ChatMessagesAdapterListener listener) {
        activeChatManager.loadAmountMessages(50);
        listener.onGetOlderDone();
    }

    public static class ChatViewHolder extends RecyclerView.ViewHolder {
        public TextView userName;
        public TextView messageDate;
        public TextView messageContent;
        public ImageView userAvatar;

        public ChatViewHolder(@NonNull View itemView) {
            super(itemView);
            this.userName = itemView.findViewById(R.id.user_name_textView);
            this.messageDate = itemView.findViewById(R.id.date_textView);
            this.userAvatar = itemView.findViewById(R.id.avatar_imageView);
            this.messageContent = itemView.findViewById(R.id.message_textView);
        }
    }

    public void setOnClickListener(ChatRoomEvent listener) {
        itemClickListener = listener;
    }

    @Inject
    public ChatMessagesAdapter(ActiveChatManager manager) {//To be continued.. Pass the data from repository. (Inject)
        this.activeChatManager = manager;
        prepare();
    }

    public void prepare(final ChatMessagesAdapterListener event) {
        LiveData<ChatRoom> room = activeChatManager.getChatRoomData();


        room.observeForever(new Observer<ChatRoom>() {
            @Override
            public void onChanged(ChatRoom chatRoom) {
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
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.text_chat_layout, parent, false);
        ChatViewHolder cvh = new ChatViewHolder(view);

        return cvh;
    }

    @Override
    public void onBindViewHolder(@NonNull ChatViewHolder holder, int position) {
        Message message = current.getMessages().get(position);

        holder.messageDate.setText(message.getDate().toString());

        User user = current.getUserPool().get(message.getSender());

        if(user!=null) {
            String userName = user.getId().equals(current.getUser().getId()) ? "You:" : user.getName()+":";
            holder.userName.setText(userName);
            Picasso.get().load(user.getAvatarURI()).resize(100, 100).into(holder.userAvatar);
        }

        switch(message.getMessageType()) {
            case Message.TEXT:
                holder.messageContent.setText(((TextMessage) message).getMessage());
                break;
        }
    }


    @Override
    public int getItemCount() {
        return current != null ? current.getMessages().size() : 0;
    }
}
