package org.netlight.habit_tracker.service;

import lombok.AllArgsConstructor;
import org.netlight.habit_tracker.dto.request.HabitRequest;
import org.netlight.habit_tracker.dto.response.HabitResponse;
import org.netlight.habit_tracker.mapper.HabitMapper;
import org.netlight.habit_tracker.model.Habit;
import org.netlight.habit_tracker.model.Tracking;
import org.netlight.habit_tracker.repository.HabitRepository;
import org.netlight.habit_tracker.repository.TrackingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

/**
 * This service will contain the business logic for the habit tracker.
 * Implement the service by completing the TODOs below.
 */
@Service
@AllArgsConstructor
public class HabitService {

    @Autowired
    private final HabitRepository habitRepository;

    @Autowired
    private final TrackingRepository trackingRepository;

    public List<HabitResponse> getAllHabits() {
        return habitRepository.findAll().stream()
            .map(HabitMapper::mapToHabitResponse)
            .toList();
    }

    public HabitResponse createHabit(final HabitRequest habitRequest) {
        final Habit habit = Habit.builder()
            .name(habitRequest.name())
            .description(habitRequest.description())
            .frequency(habitRequest.frequency())
            .startDate(habitRequest.startDate())
            .build();

        try {
            final Habit savedHabit = habitRepository.save(habit);
            return HabitMapper.mapToHabitResponse(savedHabit);
        } catch (final Exception e) {
            return null;
        }

    }

    public HabitResponse getHabitResponseById(final UUID id) {
        // TODO: Return the habit with the provided id from the database
        // Remember to consider what happens if there is no habit that matches
        // the provided id
        return HabitResponse.builder().build();
    }

    public HabitResponse updateHabit(final UUID id,
                             final HabitRequest habitRequest) {
        // TODO: Update the habit with the provided id in the database
        // Remember to consider what happens if there is no habit that matches
        // the provided id
        return HabitResponse.builder().build();
    }

    public void deleteHabit(final UUID id) {
        // TODO: Delete the habit with the provided id in the database
        // Remember to consider what happens if there is no habit that matches
        // the provided id
    }

    public Tracking addTrackingEntry(final UUID habitId,
                                     final String note) {
        // TODO: Create a new tracking entry in the database and return it
        return Tracking.builder().build();
    }

    public List<Tracking> getAllTrackingsForHabit(final UUID habitId) {
        // TODO: Return all tracking entries in the database
        return List.of();
    }

}
