package com.example.findatutor.Adapters;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.findatutor.Models.Message;
import com.example.findatutor.Models.Tutor;
import com.example.findatutor.R;

import java.util.List;

public class ChatsAdapter extends RecyclerView.Adapter {

    private List<Message> messages;
    private Context context;

    public ChatsAdapter(List<Message> messages, Context context) {
        this.messages = messages;
        this.context = context;
    }

    final int ITEM_SENT = 1;
    final int ITEM_RECEIVED = 2;

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
        //Message message = messages.get(position);
        if(1 == 1){
            return ITEM_SENT;
        }else{
            return ITEM_RECEIVED;
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        Message message = messages.get(position);
        if(holder.getClass() == SentViewHolder.class){
            SentViewHolder viewHolder = (SentViewHolder)holder;
            viewHolder.sent.setText(message.getMessage());
        }else{
            ReceivedViewHolder viewHolder = (ReceivedViewHolder)holder;
            viewHolder.received.setText(message.getMessage());
        }
    }

    @Override
    public int getItemCount() {
        return messages.size();
    }

    public class SentViewHolder extends RecyclerView.ViewHolder{

        TextView sent;

        public SentViewHolder(@NonNull View itemView) {
            super(itemView);
            sent = itemView.findViewById(R.id.appearSent);
        }
    }

    public class ReceivedViewHolder extends RecyclerView.ViewHolder{

        TextView received;

        public ReceivedViewHolder(@NonNull View itemView) {
            super(itemView);
            received = itemView.findViewById(R.id.appearReceived);
        }
    }


}
