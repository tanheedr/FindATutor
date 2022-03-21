package com.example.findatutor.Adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.findatutor.Models.Name;
import com.example.findatutor.R;

import java.util.List;

public class NamesAdapter extends BaseAdapter{

    /*
    Used to present the names of the most recently messaged in CalendarEditWeeklyActivity.
    Displays the user's full name.
    */

    public Context context;
    private final List<Name> names;

    public NamesAdapter(Context context, List<Name> names){
        this.context = context;
        this.names = names;
    }

    @Override
    public int getCount() {
        return names.size();
    }

    @Override
    public Object getItem(int position) {
        return position;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        @SuppressLint("ViewHolder") View rootView = LayoutInflater.from(context).inflate(R.layout.row_name, parent, false);
        Name name = names.get(position);
        TextView firstName = rootView.findViewById(R.id.sessionFirstName);
        TextView surname = rootView.findViewById(R.id.sessionSurname);
        firstName.setText(name.getFirstName());
        surname.setText(name.getSurname());
        return rootView;
    }
}

