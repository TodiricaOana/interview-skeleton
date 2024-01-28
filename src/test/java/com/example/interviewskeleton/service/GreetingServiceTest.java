
package com.example.interviewskeleton.service;

import com.example.interviewskeleton.exception.InvalidLocaleException;
import junitparams.JUnitParamsRunner;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.core.env.Environment;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@RunWith(JUnitParamsRunner.class)
class GreetingServiceTest {

    @InjectMocks
    private GreetingService greetingService;

    @Mock
    private Environment environment;

    @Mock
    private TimeOfDayService timeOfDayService;

    @ParameterizedTest
    @EnumSource(TimeOfDayService.TimeOfDay.class)
    void testGetGreeting_shouldReturnCorrectGreeting(TimeOfDayService.TimeOfDay timeOfDay) throws InvalidLocaleException {
        // Arrange
        when(environment.getProperty(String.format("greeting.%s.en", timeOfDay.getValue())))
                .thenReturn(String.format("Good %s, {name}!", timeOfDay.getValue()));
        when(timeOfDayService.getTimeOfDay()).thenReturn(timeOfDay);

        // Act
        String greeting = greetingService.getGreeting("John", "en");

        // Assert
        assertEquals(String.format("Good %s, John!", timeOfDay.getValue()), greeting);
    }

    @Test
    void testGetGreeting_throwsError_localeNotSupported() {
        // Arrange
        when(timeOfDayService.getTimeOfDay()).thenReturn(TimeOfDayService.TimeOfDay.MORNING);

        // Act & Assert
        Exception exception = assertThrows(InvalidLocaleException.class, () -> greetingService.getGreeting("John", "invalid"));

        // Assert
        assertTrue(exception.getMessage().contains("The provided locale is not yet supported!"));
    }
}
