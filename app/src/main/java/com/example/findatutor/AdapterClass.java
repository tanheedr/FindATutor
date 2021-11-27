package com.example.findatutor;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class AdapterClass extends RecyclerView.Adapter<AdapterClass.MyViewHolder> {

    private List<Tutor> tutors;
    private Context context;

    public AdapterClass(List<Tutor> tutors, Context context) {
        this.tutors = tutors;
        this.context = context;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.card_holder,viewGroup,false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder myViewHolder, int position) {
        myViewHolder.fullName.setText(tutors.get(position).getFullName());
        myViewHolder.subjects.setText(tutors.get(position).getSubjects());
        myViewHolder.hourlyCost.setText(tutors.get(position).getHourlyCost());
        myViewHolder.qualifications.setText(tutors.get(position).getQualifications());
        myViewHolder.description.setText(tutors.get(position).getDescription());
    }

    @Override
    public int getItemCount() {
        return tutors.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {
        TextView fullName, subjects, hourlyCost, qualifications, description;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            fullName = itemView.findViewById(R.id.tutorFullName);
            subjects = itemView.findViewById(R.id.tutorSubjects);
            hourlyCost = itemView.findViewById(R.id.tutorHourlyCost);
            qualifications = itemView.findViewById(R.id.tutorQualifications);
            description = itemView.findViewById(R.id.tutorDescription);
        }
    }
}
