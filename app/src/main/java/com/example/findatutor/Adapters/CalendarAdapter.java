package com.example.findatutor.Adapters;

import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.findatutor.Networking.CalendarUtils;
import com.example.findatutor.R;

import java.time.LocalDate;
import java.util.ArrayList;

public class CalendarAdapter extends RecyclerView.Adapter<CalendarViewHolder> {

    private final ArrayList<LocalDate> days;
    private final OnItemListener onItemListener;

    public CalendarAdapter(ArrayList<LocalDate> days, OnItemListener onItemListener) {
        this.days = days;
        this.onItemListener = onItemListener;
    }

    @NonNull
    @Override
    public CalendarViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.cell_calendar, parent, false);
        ViewGroup.LayoutParams params = view.getLayoutParams();

        if(days.size() > 15){ //month
            params.height = (int) (parent.getHeight() * 0.16666666666666666);
        }else{ //week
            params.height = (int) parent.getHeight();
        }
        return new CalendarViewHolder(view, days, onItemListener);
    }

    @Override
    public void onBindViewHolder(@NonNull CalendarViewHolder holder, int position) {
        final LocalDate date = days.get(position);
        if(date == null){
            holder.day.setText("");
        }else{
            holder.day.setText(String.valueOf(date.getDayOfMonth()));
            if(date.equals(CalendarUtils.date)){
                holder.cellCalendarView.setBackgroundColor(Color.parseColor("#008577")); //colorPrimaryDark
            }
        }
    }

    @Override
    public int getItemCount() {
        return days.size();
    }

    public interface OnItemListener{
        void onItemClick(int position, LocalDate date);
    }

}
