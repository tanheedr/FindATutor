package com.example.findatutor.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.findatutor.Models.Chat;
import com.example.findatutor.R;
import com.example.findatutor.Singleton.SharedPreferenceManager;
import com.example.findatutor.databinding.AppearReceivedBinding;
import com.example.findatutor.databinding.AppearSentBinding;

import java.util.List;

public class ChatsAdapter extends RecyclerView.Adapter {

    private final List<Chat> chats;
    private final Context context;
    final int ITEM_SENT = 1;
    final int ITEM_RECEIVED = 2;

    public ChatsAdapter(List<Chat> chats, Context context) {
        this.chats = chats;
        this.context = context;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if(viewType == ITEM_SENT){
            View view = LayoutInflater.from(context).inflate(R.layout.appear_sent, parent, false);
            return new SentViewHolder(view);
        }else{
            View view = LayoutInflater.from(context).inflate(R.layout.appear_received, parent, false);
            return new ReceivedViewHolder(view);
        }
    }

    @Override
    public int getItemViewType(int position) {
        Chat chat = chats.get(position);
        if(chat.getSenderID().equals(SharedPreferenceManager.getID())){
            return ITEM_SENT;
        }else{
            return ITEM_RECEIVED;
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        Chat chat = chats.get(position);
        if(holder.getClass() == SentViewHolder.class){
            SentViewHolder viewHolder = (SentViewHolder)holder;
            viewHolder.binding.appearSent.setText(chat.getMessage());
            viewHolder.binding.appearSentTimestamp.setText(chat.getTimestamp());
        }else{
            ReceivedViewHolder viewHolder = (ReceivedViewHolder)holder;
            viewHolder.binding.appearReceived.setText(chat.getMessage());
            viewHolder.binding.appearReceivedTimestamp.setText(chat.getTimestamp());
        }
    }

    @Override
    public int getItemCount() {
        return chats.size();
    }

    public static class SentViewHolder extends RecyclerView.ViewHolder{
        AppearSentBinding binding;
        public SentViewHolder(@NonNull View itemView) {
            super(itemView);
            binding = AppearSentBinding.bind(itemView);
        }
    }

    public static class ReceivedViewHolder extends RecyclerView.ViewHolder{
        AppearReceivedBinding binding;
        public ReceivedViewHolder(@NonNull View itemView) {
            super(itemView);
            binding = AppearReceivedBinding.bind(itemView);
        }
    }


}
