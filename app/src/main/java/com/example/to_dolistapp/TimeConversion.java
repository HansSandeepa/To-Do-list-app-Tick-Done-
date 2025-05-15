package com.example.to_dolistapp;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

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
}
