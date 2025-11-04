package org.netlight.habit_tracker.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.netlight.habit_tracker.dto.request.HabitRequest;
import org.netlight.habit_tracker.dto.response.HabitResponse;
import org.netlight.habit_tracker.model.Frequency;
import org.netlight.habit_tracker.model.Habit;
import org.netlight.habit_tracker.model.Tracking;
import org.netlight.habit_tracker.service.HabitService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.UUID;

import static org.mockito.Mockito.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.eq;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(HabitTrackerController.class)
public class HabitTrackerControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private HabitService habitService;

    private UUID habitId;
    private HabitResponse habitResponse;
    private Habit habit;

    @BeforeEach
    void setUp() {
        habitId = UUID.randomUUID();

        habitResponse = HabitResponse.builder()
                .id(habitId)
                .name("Test Habit")
                .description("This is a test habit")
                .frequency(Frequency.DAILY)
                .startDate(LocalDate.now())
                .build();

        habit = Habit.builder()
                .id(habitId)
                .name("Test Habit")
                .description("This is a test habit")
                .frequency(Frequency.DAILY)
                .startDate(LocalDate.now())
                .build();
    }

    @Test
    void should_getAllHabits_whenServiceReturnsHabits() throws Exception {
        // Mock the service layer to return a list of habits
        when(habitService.getAllHabits()).thenReturn(Collections.singletonList(habitResponse));

        mockMvc.perform(get("/api/habits"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].name").value("Test Habit"))
                .andExpect(jsonPath("$[0].description").value("This is a test habit"));

        verify(habitService, times(1)).getAllHabits(); // Verify service method is called
    }

    @Test
    void should_getHabitById_whenHabitExists() throws Exception {
        // Mock the service to return a habit by ID
        when(habitService.getHabitResponseById(habitId)).thenReturn(habitResponse);

        mockMvc.perform(get("/api/habits/{id}", habitId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Test Habit"))
                .andExpect(jsonPath("$.description").value("This is a test habit"));

        verify(habitService, times(1)).getHabitResponseById(habitId);
    }

    @Test
    void should_addHabit_whenRequestIsValid() throws Exception {
        HabitRequest habitRequest = HabitRequest.builder()
                .name("New Habit")
                .description("A new habit description")
                .frequency(Frequency.DAILY)
                .startDate(LocalDate.now())
                .build();

        HabitResponse createdResponse = HabitResponse.builder()
                .id(UUID.randomUUID())
                .name(habitRequest.name())
                .description(habitRequest.description())
                .frequency(habitRequest.frequency())
                .startDate(habitRequest.startDate())
                .build();

        // Mock the service to return the created habit
        when(habitService.createHabit(any(HabitRequest.class))).thenReturn(createdResponse);

        mockMvc.perform(post("/api/habits")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                        {
                            "name": "New Habit",
                            "description": "A new habit description",
                            "frequency": "DAILY",
                            "startDate": "2024-01-01"
                        }
                        """))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("New Habit"))
                .andExpect(jsonPath("$.description").value("A new habit description"));

        verify(habitService, times(1)).createHabit(any(HabitRequest.class));
    }

    @Test
    void should_updateHabit_whenHabitExists() throws Exception {
        HabitRequest updatedRequest = HabitRequest.builder()
                .name("Updated Habit")
                .description("Updated description")
                .frequency(Frequency.WEEKLY)
                .startDate(LocalDate.now())
                .build();

        HabitResponse updatedResponse = HabitResponse.builder()
                .id(habitId)
                .name(updatedRequest.name())
                .description(updatedRequest.description())
                .frequency(updatedRequest.frequency())
                .startDate(updatedRequest.startDate())
                .build();

        // Mock the service to return updated habit
        when(habitService.updateHabit(eq(habitId), any(HabitRequest.class))).thenReturn(updatedResponse);

        mockMvc.perform(patch("/api/habits/{id}", habitId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                        {
                            "name": "Updated Habit",
                            "description": "Updated description",
                            "frequency": "WEEKLY",
                            "startDate": "2024-01-01"
                        }
                        """))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Updated Habit"))
                .andExpect(jsonPath("$.description").value("Updated description"));

        verify(habitService, times(1)).updateHabit(eq(habitId), any(HabitRequest.class));
    }

    @Test
    void should_deleteHabit_whenHabitExists() throws Exception {
        doNothing().when(habitService).deleteHabit(habitId); // Mock the delete behavior

        mockMvc.perform(delete("/api/habits/{id}", habitId))
                .andExpect(status().isOk());

        verify(habitService, times(1)).deleteHabit(habitId);
    }

    @Test
    void should_addTracking_whenHabitExists() throws Exception {
        String note = "First tracking entry";
        Tracking trackingResponse = Tracking.builder()
                .id(UUID.randomUUID())
                .habit(habit)
                .timestamp(LocalDateTime.now())
                .note(note)
                .build();

        when(habitService.addTrackingEntry(eq(habitId), eq(note))).thenReturn(trackingResponse);

        mockMvc.perform(post("/api/habits/{habitId}/tracking", habitId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(note))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.note").value("First tracking entry"));

        verify(habitService, times(1)).addTrackingEntry(eq(habitId), eq(note));
    }

    @Test
    void should_getTrackings_whenHabitHasTrackings() throws Exception {
        Tracking tracking = Tracking.builder()
                .id(UUID.randomUUID())
                .habit(habit)
                .timestamp(LocalDateTime.now())
                .note("First tracking entry")
                .build();

        when(habitService.getAllTrackingsForHabit(habitId)).thenReturn(Collections.singletonList(tracking));

        mockMvc.perform(get("/api/habits/{habitId}/tracking", habitId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].note").value("First tracking entry"));

        verify(habitService, times(1)).getAllTrackingsForHabit(habitId);
    }
}
