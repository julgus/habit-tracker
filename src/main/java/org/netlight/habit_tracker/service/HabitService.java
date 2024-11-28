package org.netlight.habit_tracker.service;

import org.netlight.habit_tracker.dto.request.HabitRequest;
import org.netlight.habit_tracker.dto.response.HabitResponse;
import org.netlight.habit_tracker.exception.HabitNotFoundException;
import org.netlight.habit_tracker.mapper.HabitMapper;
import org.netlight.habit_tracker.model.Habit;
import org.netlight.habit_tracker.model.Tracking;
import org.netlight.habit_tracker.repository.HabitRepository;
import org.netlight.habit_tracker.repository.TrackingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
public class HabitService {

    @Autowired
    private HabitRepository habitRepository;

    @Autowired
    private TrackingRepository trackingRepository;

    public List<HabitResponse> getAllHabits() {
        return habitRepository.findAll().stream()
            .map(HabitMapper::mapToHabitResponse)
            .toList();
    }

    public HabitResponse getHabitResponseById(final UUID id) {
        return HabitMapper.mapToHabitResponse(getHabitById(id));
    }

    private Habit getHabitById(final UUID id) {
        return habitRepository.findById(id)
            .orElseThrow(() -> new HabitNotFoundException("Habit with ID " + id + " not found"));
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

    public HabitResponse updateHabit(final UUID id,
                             final HabitRequest habitRequest) {
        final Habit existingHabit = getHabitById(id); // will throw HabitNotFoundException if not found

        // Create a new Habit record with updated fields, using existing data if updates are absent
        Habit updatedHabit = Habit.builder()
            .id(existingHabit.getId())
            .name(habitRequest.name() != null
                ? habitRequest.name()
                : existingHabit.getName())
            .description(habitRequest.description() != null
                ? habitRequest.description()
                : existingHabit.getDescription())
            .frequency(habitRequest.frequency() != null
                ? habitRequest.frequency()
                : existingHabit.getFrequency())
            .startDate(habitRequest.startDate() != null
                ? habitRequest.startDate()
                : existingHabit.getStartDate())
            .build();

        return HabitMapper.mapToHabitResponse(habitRepository.save(updatedHabit));
    }

    public void deleteHabit(final UUID id) {
        if (!habitRepository.existsById(id)) {
            throw new HabitNotFoundException("Habit with ID " + id + " not found");
        }
        habitRepository.deleteById(id);
    }

    public Tracking addTrackingEntry(final UUID habitId,
                                     final String note) {
        final Habit habit = getHabitById(habitId);

        Tracking newTracking = Tracking.builder()
            .id(UUID.randomUUID())
            .habit(habit)
            .timestamp(LocalDateTime.now())
            .note(note)
            .build();

        return trackingRepository.save(newTracking);
    }

    public List<Tracking> getAllTrackingsForHabit(final UUID habitId) {
        getHabitById(habitId);
        return trackingRepository.findByHabitId(habitId);
    }
}
