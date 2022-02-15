package com.example.findatutor.Models;

import com.google.gson.annotations.SerializedName;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;

public class Session {

    @SerializedName("ID") private String ID;
    @SerializedName("FirstName") private String firstName;
    @SerializedName("Surname") private String surname;
    @SerializedName("StartTime") private String startTime;
    @SerializedName("EndTime") private String endTime;

    public String getID(){
        return ID;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getSurname() {
        return surname;
    }

    public String getDate() {
        return startTime.substring(0, startTime.length() - 9);
    }

    public String getStartTime(){
        return startTime.substring(11, startTime.length() - 3);
    }

    public String getEndTime(){
        return endTime.substring(11, endTime.length() - 3);
    }

/*    public static ArrayList<Session> sessionsList = new ArrayList<>();

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
    private String time;

    public Session(String name, LocalDate date, String time) {
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

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }*/

}
