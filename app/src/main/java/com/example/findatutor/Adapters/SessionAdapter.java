package com.example.findatutor.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import com.example.findatutor.Models.Session;
import com.example.findatutor.Models.Tutor;
import com.example.findatutor.Networking.CalendarUtils;
import com.example.findatutor.R;

import java.util.List;

public class SessionAdapter extends RecyclerView.Adapter<SessionAdapter.MyViewHolder> /*ArrayAdapter<Session>*/ {

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
    }

    @Override
    public int getItemCount() {
        return sessions.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        TextView name, time;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            name = itemView.findViewById(R.id.rowSessionName);
            time = itemView.findViewById(R.id.rowSessionTime);
        }
    }

    /*public SessionAdapter(@NonNull Context context, List<Session> sessions) {
        super(context, 0, sessions);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        Session session = getItem(position);
        if(convertView == null){
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.cell_calendar_weekly, parent, false);
        }
        TextView cellSession = convertView.findViewById(R.id.cellSession);
        String sessionName = session.getName() + " " + CalendarUtils.formattedTime(session.getTime());
        cellSession.setText(sessionName);
        return convertView;
    }*/
}
