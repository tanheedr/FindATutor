package com.example.findatutor.Adapters;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.findatutor.R;

import java.time.LocalDate;
import java.util.ArrayList;

public class CalendarViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

    public final View cellCalendarView;
    public final TextView day;
    private final ArrayList<LocalDate> days;
    private final CalendarAdapter.OnItemListener onItemListener;

    public CalendarViewHolder(@NonNull View itemView, ArrayList<LocalDate> days, CalendarAdapter.OnItemListener onItemListener) {
        super(itemView);
        cellCalendarView = itemView.findViewById(R.id.cellCalendarView);
        day = itemView.findViewById(R.id.cellDay);
        this.days = days;
        this.onItemListener = onItemListener;

        itemView.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        onItemListener.onItemClick(getAbsoluteAdapterPosition(), days.get(getAbsoluteAdapterPosition()));
    }
}
