package com.example.findatutor;

import android.content.Context;
import android.os.Binder;
import android.renderscript.ScriptGroup;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;

public class UsersAdapter extends RecyclerView.Adapter<UsersAdapter.UserViewHolder>{

    private List<User> users;
    private Context context;

    public UsersAdapter(List<User> users, Context context) {
        this.users = users;
        this.context = context;
    }

    @NonNull
    @Override
    public UserViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.row_conversation, parent, false);
        return new UserViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull UserViewHolder holder, int position) {
        User user = users.get(position);
        Glide.with(holder.itemView.getContext()).load(user.getPhoto()).into(holder.photo);
        holder.firstName.setText(user.getFirstName());
        holder.surname.setText(user.getSurname());
        holder.lastMessage.setText(user.getLastMessage());
        holder.timestamp.setText(user.getTimestamp());
    }

    @Override
    public int getItemCount() {
        return users.size();
    }

    public class UserViewHolder extends RecyclerView.ViewHolder{

        ImageView photo;
        TextView firstName, surname, lastMessage, timestamp;

        public UserViewHolder(@NonNull View itemView) {
            super(itemView);
            photo = itemView.findViewById(R.id.chatPhoto);
            firstName = itemView.findViewById(R.id.chatFirstName);
            surname = itemView.findViewById(R.id.chatSurname);
            lastMessage = itemView.findViewById(R.id.chatLastMessage);
            timestamp = itemView.findViewById(R.id.chatTimestamp);
        }
    }

}
