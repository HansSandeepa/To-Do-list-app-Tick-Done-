package com.example.to_dolistapp;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.Locale;

public class TimeConversion {
    public String convertedTime(String standardTime) {
        LocalTime time = LocalTime.parse(standardTime, DateTimeFormatter.ofPattern("HH:mm"));

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("hh:mm a");
        String convertedTime = time.format(formatter);

        System.out.println(convertedTime);

        return convertedTime;
    }

    public static boolean isDateTimeInThePast(String date,String time){
        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HH:mm");

        // Parse the date and time strings
        LocalDate givenDate = LocalDate.parse(date, dateFormatter);
        LocalTime givenTime = LocalTime.parse(time, timeFormatter);

        // Combine them into a LocalDateTime object
        LocalDateTime givenDateTime = LocalDateTime.of(givenDate, givenTime);

        // Get the current date and time
        LocalDateTime currentDateTime = LocalDateTime.now();

        // Compare: returns true if givenDateTime is before currentDateTime
        return givenDateTime.isBefore(currentDateTime);
    }

    public long combineDateAndTime(String dateStr,String timeStr){
        try {
            String dateTimeStr = dateStr + " " + timeStr;

            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.getDefault());
            Date date = sdf.parse(dateTimeStr);

            assert date != null;
            return date.getTime();

        } catch (ParseException e) {
            e.printStackTrace();
            return 0;
        }
    }
}
