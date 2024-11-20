package org.netlight.habit_tracker.mapper;

import org.netlight.habit_tracker.dto.response.HabitResponse;
import org.netlight.habit_tracker.model.Habit;

public final class HabitMapper {

    /**
     * Private constructor to prevent initialization
     */
    private HabitMapper() {}

    public static HabitResponse mapToHabitResponse(final Habit habit) {
        return HabitResponse.builder()
            .id(habit.id())
            .name(habit.name())
            .description(habit.description())
            .frequency(habit.frequency())
            .startDate(habit.startDate())
            .build();
    }

}
