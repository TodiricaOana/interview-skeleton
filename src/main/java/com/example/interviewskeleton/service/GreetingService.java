package com.example.interviewskeleton.service;

import com.example.interviewskeleton.exception.InvalidLocaleException;
import lombok.RequiredArgsConstructor;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class GreetingService {

    private final Environment env;
    private final TimeOfDayService timeOfDayService;

    public String getGreeting(String name, String locale) {
        String greeting = env.getProperty("greeting." + timeOfDayService.getTimeOfDay().getValue() + "." + locale);
        if (greeting == null) {
            throw new InvalidLocaleException("The provided locale is not yet supported!");
        }
        return greeting.replace("{name}", name);
    }
}
