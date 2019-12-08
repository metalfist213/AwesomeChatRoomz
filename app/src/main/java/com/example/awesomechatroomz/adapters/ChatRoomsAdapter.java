package com.example.awesomechatroomz.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.awesomechatroomz.R;
import com.example.awesomechatroomz.models.ChatRoom;

import javax.inject.Inject;

public class ChatRoomsAdapter extends RecyclerView.Adapter<ChatRoomsAdapter.ChatViewHolder> {
    private String[] mock = {"Chat Room","Chat Room","Chat Room","Chat Room","Chat Room","Chat Room","Chat Room","Chat Room","Chat Room","Chat Room","Chat Room","Chat Room","Chat Room","Chat Room"};
    private ChatRoomEvent itemClickListener;

    public interface ChatRoomEvent {
        public void onChatRoomClicked(ChatRoom room);
    }

    public static class ChatViewHolder extends RecyclerView.ViewHolder {
        public TextView chatroomTitleTextView;
        public TextView chatroomDescriptionTextView;
        public ChatViewHolder(@NonNull View itemView) {
            super(itemView);
            this.chatroomTitleTextView = itemView.findViewById(R.id.chat_room_title);
            this.chatroomDescriptionTextView = itemView.findViewById(R.id.chat_room_description);
        }
    }

    public void setOnClickListener(ChatRoomEvent listener) {
        itemClickListener = listener;
    }
    @Inject
    public ChatRoomsAdapter() {//To be continued.. Pass the data from repository. (Inject)

    }

    @NonNull
    @Override
    public ChatViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.chat_menu_layouts, parent, false);
        ChatViewHolder cvh = new ChatViewHolder(view);

        return cvh;
    }

    @Override
    public void onBindViewHolder(@NonNull ChatViewHolder holder, int position) {
        String current = mock[position];

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ChatRoom mock = new ChatRoom();
                mock.setName("Test Chatroom");
                mock.setDescription("This is a standard chat room");
                itemClickListener.onChatRoomClicked(mock);
            }
        });

        holder.chatroomDescriptionTextView.setText("Dette er en test streng som skal simulere en chat room description. Det er ikke det vildeste, men burde være et find proof of concept.");
        holder.chatroomTitleTextView.setText(current + " "+position);
    }

    public void bind() {

    }


    @Override
    public int getItemCount() {
        return mock.length;
    }
}
