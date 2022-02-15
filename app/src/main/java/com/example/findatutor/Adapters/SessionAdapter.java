package com.example.findatutor.Adapters;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.findatutor.Activities.ChatActivity;
import com.example.findatutor.Models.Session;
import com.example.findatutor.Models.Tutor;
import com.example.findatutor.Networking.CalendarUtils;
import com.example.findatutor.R;
import com.example.findatutor.Singleton.SharedPreferenceManager;

import java.util.List;

public class SessionAdapter extends RecyclerView.Adapter<SessionAdapter.MyViewHolder> {

    private final List<Session> sessions;
    private final Context context;

    public SessionAdapter(List<Session> sessions, Context context) {
        this.sessions = sessions;
        this.context = context;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_session, parent,false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        Session session = sessions.get(position);
        holder.ID = Integer.valueOf(session.getID());
        holder.firstName.setText(session.getFirstName());
        holder.surname.setText(session.getSurname());
        holder.startTime.setText(session.getStartTime());
        holder.endTime.setText(session.getEndTime());
    }

    @Override
    public int getItemCount() {
        return sessions.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        TextView firstName, surname, startTime, endTime;
        Integer ID;
        ConstraintLayout layout;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            firstName = itemView.findViewById(R.id.rowSessionFirstName);
            surname = itemView.findViewById(R.id.rowSessionSurname);
            startTime = itemView.findViewById(R.id.rowSessionStartTime);
            endTime = itemView.findViewById(R.id.rowSessionEndTime);
            layout = itemView.findViewById(R.id.rowSessionLayout);

            layout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    SharedPreferenceManager.getmInstance(context.getApplicationContext()).MessageUser(ID);
                    context.startActivity(new Intent(context, ChatActivity.class));
                }
            });
        }
    }

}
