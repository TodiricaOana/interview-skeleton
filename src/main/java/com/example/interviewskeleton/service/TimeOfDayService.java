package com.example.interviewskeleton.service;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalTime;

@Service
public class TimeOfDayService {

    public TimeOfDay getTimeOfDay() {
        LocalTime localTime = LocalTime.now();

        if (localTime.isBefore(LocalTime.NOON)) {
            return TimeOfDay.MORNING;
        }
        if (localTime.isBefore(LocalTime.of(18, 0))) {
            return TimeOfDay.AFTERNOON;
        }
        return TimeOfDay.EVENING;
    }

    @RequiredArgsConstructor
    @Getter
    public enum TimeOfDay {
        MORNING("morning"),
        AFTERNOON("afternoon"),
        EVENING("evening");

        private final String value;
    }
}
