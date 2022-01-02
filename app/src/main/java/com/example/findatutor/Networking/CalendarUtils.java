package com.example.findatutor.Networking;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.YearMonth;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Objects;

public class CalendarUtils {

    public static LocalDate date;

    public static ArrayList<LocalDate> daysOfMonthArray(LocalDate date) {
        ArrayList<LocalDate> daysOfMonthArray = new ArrayList<>();
        YearMonth yearMonth = YearMonth.from(date);
        int daysOfMonth = yearMonth.lengthOfMonth();
        LocalDate firstDayOfMonth = date.withDayOfMonth(1);
        int daysOfWeek = firstDayOfMonth.getDayOfWeek().getValue();

        for(int i = 1; i <= 42; i++){
            if(i <= daysOfWeek || i > daysOfMonth + daysOfWeek){
                daysOfMonthArray.add(null);
            }else{
                daysOfMonthArray.add(LocalDate.of(date.getYear(), date.getMonth(), i - daysOfWeek));
            }
        }
        return  daysOfMonthArray;
    }

    public static String monthYearFromDate(LocalDate date){
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MMMM yyyy");
        return date.format(formatter);
    }

    public static String formattedDate(LocalDate date) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd MMMM yyyy");
        return date.format(formatter);
    }

    public static String formattedTime(LocalTime time) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("hh:mm a");
        return time.format(formatter);
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
