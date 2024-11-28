package org.netlight.habit_tracker.service;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.netlight.habit_tracker.dto.request.HabitRequest;
import org.netlight.habit_tracker.dto.response.HabitResponse;
import org.netlight.habit_tracker.exception.HabitNotFoundException;
import org.netlight.habit_tracker.model.Frequency;
import org.netlight.habit_tracker.model.Habit;
import org.netlight.habit_tracker.model.Tracking;
import org.netlight.habit_tracker.repository.HabitRepository;
import org.netlight.habit_tracker.repository.TrackingRepository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class HabitServiceTest {

    @Mock
    private HabitRepository habitRepository;

    @Mock
    private TrackingRepository trackingRepository;

    @InjectMocks
    private HabitService habitService;

    private Habit testHabit;
    private HabitRequest testHabitRequest;
    private Tracking testTracking;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        testHabit = Habit.builder()
                .id(UUID.randomUUID())
                .name("Test Habit")
                .description("A sample habit")
                .frequency(Frequency.DAILY)
                .startDate(LocalDate.now())
                .build();

        testHabitRequest = HabitRequest.builder()
                .name("Test Habit")
                .description("As sample habit")
                .frequency(Frequency.DAILY)
                .startDate(LocalDate.now())
                .build();

        testTracking = Tracking.builder()
                .id(UUID.randomUUID())
                .habit(testHabit)
                .timestamp(LocalDateTime.now())
                .note("Test tracking note")
                .build();
    }

    @Test
    void should_getAllHabits() {
        when(habitRepository.findAll()).thenReturn(List.of(testHabit));

        List<HabitResponse> habits = habitService.getAllHabits();

        assertEquals(1, habits.size());
        assertEquals("Test Habit", habits.get(0).name());
        verify(habitRepository, times(1)).findAll();
    }

    @Test
    void should_getHabitResponseById_whenHabitExists() {
        when(habitRepository.findById(testHabit.getId())).thenReturn(Optional.of(testHabit));

        HabitResponse habitResponse = habitService.getHabitResponseById(testHabit.getId());

        assertNotNull(habitResponse);
        assertEquals("Test Habit", habitResponse.name());
        verify(habitRepository, times(1)).findById(testHabit.getId());
    }

    @Test
    void should_throwHabitNotFoundException_whenHabitDoesNotExist() {
        UUID invalidId = UUID.randomUUID();
        when(habitRepository.findById(invalidId)).thenReturn(Optional.empty());

        assertThrows(HabitNotFoundException.class, () -> habitService.getHabitResponseById(invalidId));
        verify(habitRepository, times(1)).findById(invalidId);
    }

    @Test
    void should_createHabit_whenRequestIsValid() {
        when(habitRepository.save(any(Habit.class))).thenReturn(testHabit);

        HabitResponse habitResponse = habitService.createHabit(testHabitRequest);

        assertNotNull(habitResponse);
        assertEquals("Test Habit", habitResponse.name());

        ArgumentCaptor<Habit> habitCaptor = ArgumentCaptor.forClass(Habit.class);
        verify(habitRepository, times(1)).save(habitCaptor.capture());
        Habit savedHabit = habitCaptor.getValue();
        assertEquals("Test Habit", savedHabit.getName());
        assertEquals(Frequency.DAILY, savedHabit.getFrequency());
    }

    @Test
    void should_updateHabit_whenHabitExists() {
        UUID habitId = testHabit.getId();

        when(habitRepository.findById(habitId)).thenReturn(Optional.of(testHabit));

        Habit updatedHabit = Habit.builder()
                .id(habitId)
                .name("Updated Habit")
                .description("Updated description")
                .frequency(Frequency.WEEKLY)
                .startDate(LocalDate.now())
                .build();

        when(habitRepository.save(any(Habit.class))).thenReturn(updatedHabit);

        HabitRequest updatedRequest = HabitRequest.builder()
                .name("Updated Habit")
                .description("Updated description")
                .frequency(Frequency.WEEKLY)
                .startDate(LocalDate.now())
                .build();

        HabitResponse updatedHabitResponse = habitService.updateHabit(habitId, updatedRequest);

        assertNotNull(updatedHabitResponse);
        assertEquals("Updated Habit", updatedHabitResponse.name());

        verify(habitRepository, times(1)).save(updatedHabit);
        assertEquals("Updated Habit", updatedHabitResponse.name());
        assertEquals(Frequency.WEEKLY, updatedHabitResponse.frequency());
    }

    @Test
    void should_deleteHabit_whenHabitExists() {
        UUID habitId = testHabit.getId();

        when(habitRepository.existsById(habitId)).thenReturn(true);

        habitService.deleteHabit(habitId);

        verify(habitRepository, times(1)).deleteById(habitId);
    }

    @Test
    void should_throwHabitNotFoundException_whenDeletingNonexistentHabit() {
        UUID habitId = testHabit.getId();

        when(habitRepository.existsById(habitId)).thenReturn(false);

        assertThrows(HabitNotFoundException.class, () -> habitService.deleteHabit(habitId));
        verify(habitRepository, never()).deleteById(habitId);
    }

    @Test
    void should_addTrackingEntry_whenHabitExists() {
        UUID habitId = testHabit.getId();

        when(habitRepository.findById(habitId)).thenReturn(Optional.of(testHabit));
        when(trackingRepository.save(any(Tracking.class))).thenReturn(testTracking);

        Tracking tracking = habitService.addTrackingEntry(habitId, "Test tracking note");

        assertNotNull(tracking);
        assertEquals("Test tracking note", tracking.getNote());

        ArgumentCaptor<Tracking> trackingCaptor = ArgumentCaptor.forClass(Tracking.class);
        verify(trackingRepository, times(1)).save(trackingCaptor.capture());
        Tracking savedTracking = trackingCaptor.getValue();
        assertEquals("Test tracking note", savedTracking.getNote());
        assertEquals(habitId, savedTracking.getHabit().getId());
    }

    @Test
    void should_throwHabitNotFoundException_whenAddingTrackingForNonexistentHabit() {
        UUID habitId = testHabit.getId();

        when(habitRepository.existsById(habitId)).thenReturn(false);

        assertThrows(HabitNotFoundException.class, () -> habitService.addTrackingEntry(habitId, any()));
        verify(trackingRepository, never()).save(any(Tracking.class));
    }


    @Test
    void should_getAllTrackingsForHabit_whenHabitExists() {
        UUID habitId = testHabit.getId();

        when(habitRepository.findById(habitId)).thenReturn(Optional.of(testHabit));
        when(trackingRepository.findByHabitId(habitId)).thenReturn(List.of(testTracking));

        List<Tracking> trackings = habitService.getAllTrackingsForHabit(habitId);

        assertEquals(1, trackings.size());
        assertEquals("Test tracking note", trackings.get(0).getNote());

        verify(trackingRepository, times(1)).findByHabitId(habitId);
    }
}
