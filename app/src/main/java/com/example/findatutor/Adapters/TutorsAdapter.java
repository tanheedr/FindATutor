package com.example.findatutor.Adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.findatutor.Activities.MessageActivity;
import com.example.findatutor.R;
import com.example.findatutor.Models.Tutor;

import java.util.List;

public class TutorsAdapter extends RecyclerView.Adapter<TutorsAdapter.MyViewHolder> {

    private final List<Tutor> tutors;
    private final Context context;

    public TutorsAdapter(List<Tutor> tutors, Context context) {
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
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        Tutor tutor = tutors.get(position);
        holder.firstName.setText(tutor.getFirstName());
        holder.surname.setText(tutor.getSurname());
        Glide.with(holder.itemView.getContext()).load(tutor.getPhoto()).into(holder.photo);
        holder.subjects.setText(tutor.getSubjects());
        holder.hourlyCost.setText(tutor.getHourlyCost());
        holder.qualifications.setText(tutor.getQualifications());
        holder.description.setText(tutor.getDescription());
    }

    @Override
    public int getItemCount() {
        return tutors.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        TextView firstName, surname, subjects, hourlyCost, qualifications, description;
        ImageView photo;
        Button message;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            firstName = itemView.findViewById(R.id.tutorFirstName);
            surname = itemView.findViewById(R.id.tutorSurname);
            photo = itemView.findViewById(R.id.tutorProfilePic);
            subjects = itemView.findViewById(R.id.tutorSubjects);
            hourlyCost = itemView.findViewById(R.id.tutorHourlyCost);
            qualifications = itemView.findViewById(R.id.tutorQualifications);
            description = itemView.findViewById(R.id.tutorDescription);
            message = itemView.findViewById(R.id.tutorMessage);

            message.setOnClickListener(v -> context.startActivity(new Intent(context, MessageActivity.class)));

        }
    }
}
