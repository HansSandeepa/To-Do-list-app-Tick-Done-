package com.example.to_dolistapp;

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
}
