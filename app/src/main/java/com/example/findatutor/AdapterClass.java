package com.example.findatutor;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.Image;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.net.URL;
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
        myViewHolder.firstName.setText(tutors.get(position).getFirstName());
        myViewHolder.surname.setText(tutors.get(position).getSurname());
        Glide.with(myViewHolder.itemView.getContext()).load(tutors.get(position).getPhoto()).into(myViewHolder.photo);
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

            message.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(context, MessageActivity.class);
                    context.startActivity(intent);

                }
            });

        }
    }
}
