package com.example.findatutor.Networking;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Objects;

public class CalendarUtils {

    /*
    Used to create the calendar for CalendarWeeklyActivity
    */

    public static LocalDate date;

    public static String monthYearFromDate(LocalDate date){
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MMMM yyyy");
        return date.format(formatter);
    }

    public static String formattedDate(LocalDate date) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd MMMM yyyy");
        return date.format(formatter);
    }

    public static ArrayList<LocalDate> daysOfWeekArray(LocalDate date) {
        ArrayList<LocalDate> daysOfWeekArray = new ArrayList<>();
        LocalDate current = sundayForDate(date);
        LocalDate endDate = Objects.requireNonNull(current).plusWeeks(1);

        while(current.isBefore(endDate)){
            daysOfWeekArray.add(current);
            current = current.plusDays(1);
        }

        return daysOfWeekArray;
    }

    private static LocalDate sundayForDate(LocalDate current) {
        LocalDate lastWeek = current.minusWeeks(1);

        while(current.isAfter(lastWeek)){
            if(current.getDayOfWeek() == DayOfWeek.SUNDAY){
                return current;
            }
            current = current.minusDays(1);
        }
        return null;
    }

}
