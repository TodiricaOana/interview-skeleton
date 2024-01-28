package com.example.interviewskeleton.controller;

import com.example.interviewskeleton.service.GreetingService;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.example.interviewskeleton.exception.InvalidLocaleException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
public class GreetingControllerTest {

    @InjectMocks
    private GreetingController greetingController;

    @Mock
    private GreetingService greetingService;

    private MockMvc mockMvc;

    private final ObjectMapper objectMapper = new ObjectMapper();

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders
                .standaloneSetup(greetingController)
                .build();

        objectMapper.registerModule(new JavaTimeModule());
    }

    @Test
    void getGreeting_returnsExpected() throws Exception {
        // Arrange
        final String expected = "Good morning, John!";

        when(greetingService.getGreeting("John", "en")).thenReturn(expected);

        // Act
        MvcResult result = mockMvc.perform(get("/greet/John?locale=en")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();

        // Assert
        assertThat(result.getResponse().getContentAsString()).isEqualTo(expected);
    }

    @Test
    void getGreeting_throwsInvalidLocaleException() throws InvalidLocaleException {
        // Arrange
        when(greetingService.getGreeting("John", "invalid")).thenThrow(mock(InvalidLocaleException.class));

        // Act & Assert
        assertThrows(
                InvalidLocaleException.class,
                () -> greetingController.getGreetingMessage("John", "invalid")
        );
    }
}
