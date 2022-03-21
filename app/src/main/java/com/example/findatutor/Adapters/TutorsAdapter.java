package com.example.findatutor.Adapters;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.findatutor.Activities.ChatActivity;
import com.example.findatutor.Models.Tutor;
import com.example.findatutor.R;
import com.example.findatutor.Singleton.SharedPreferenceManager;

import java.util.List;

public class TutorsAdapter extends RecyclerView.Adapter<TutorsAdapter.TutorViewHolder> {

    /*
    Used to present available tutors based on what has been searched in SearchActivity
    For each tutor, their full name, picture, subjects, hourly cost, qualifications and description
    are presented. The id is also stored, and if the user clicks on a tutor's card, ChatActivity
    will open up with the tutor's stored id used to gather information about the recipient.
    */

    private final List<Tutor> tutors;
    private final Context context;

    public TutorsAdapter(List<Tutor> tutors, Context context) {
        this.tutors = tutors;
        this.context = context;
    }

    @NonNull
    @Override
    public TutorViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.row_tutor,viewGroup,false);
        return new TutorViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TutorViewHolder holder, int position) {
        Tutor tutor = tutors.get(position);
        holder.ID = Integer.valueOf(tutor.getID());
        holder.firstName.setText(tutor.getFirstName());
        holder.surname.setText(tutor.getSurname());

        byte[] decodedString = Base64.decode(tutor.getPhoto(), Base64.DEFAULT);
        Bitmap decodedByte = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
        holder.photo.setImageBitmap(decodedByte);

        holder.subjects.setText(tutor.getSubjects());
        holder.hourlyCost.setText(tutor.getHourlyCost());
        holder.qualifications.setText(tutor.getQualifications());
        holder.description.setText(tutor.getDescription());
    }

    @Override
    public int getItemCount() {
        return tutors.size();
    }

    public class TutorViewHolder extends RecyclerView.ViewHolder {

        TextView firstName, surname, subjects, hourlyCost, qualifications, description;
        ImageView photo;
        CardView layout;
        Integer ID;

        public TutorViewHolder(@NonNull View itemView) {
            super(itemView);

            firstName = itemView.findViewById(R.id.tutorFirstName);
            surname = itemView.findViewById(R.id.tutorSurname);
            photo = itemView.findViewById(R.id.tutorProfilePic);
            subjects = itemView.findViewById(R.id.tutorSubjects);
            hourlyCost = itemView.findViewById(R.id.tutorHourlyCost);
            qualifications = itemView.findViewById(R.id.tutorQualifications);
            description = itemView.findViewById(R.id.tutorDescription);
            layout = itemView.findViewById(R.id.tutorLayout);

            layout.setOnClickListener(v -> {
                SharedPreferenceManager.getmInstance(context.getApplicationContext()).MessageUser(ID);
                context.startActivity(new Intent(context, ChatActivity.class));
            });
        }
    }
}
