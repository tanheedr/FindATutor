package com.example.findatutor.Models;

import com.google.gson.annotations.SerializedName;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;

public class Session {

    /*@SerializedName("TutorID") private String tutorID;
    @SerializedName("ParentID") private String parentID;
    @SerializedName("Timestamp") private String timestamp;

    public String getTutorID() {
        return tutorID;
    }

    public String getParentID() {
        return parentID;
    }

    public String getDate() {
        return timestamp.substring(0, timestamp.length() - 9);
    }

    public String getTime(){
        return timestamp.substring(11);
    }*/

    public static ArrayList<Session> sessionsList = new ArrayList<>();

    public static ArrayList<Session> sessionsPerDay(LocalDate date){
        ArrayList<Session> sessions = new ArrayList<>();
        for(Session session : sessionsList){
            if(session.getDate().equals(date)){
                sessions.add(session);
            }
        }
        return sessions;
    }

    private String name;
    private LocalDate date;
    private LocalTime time;

    public Session(String name, LocalDate date, LocalTime time) {
        this.name = name;
        this.date = date;
        this.time = time;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public LocalTime getTime() {
        return time;
    }

    public void setTime(LocalTime time) {
        this.time = time;
    }
}
