package org.netlight.habit_tracker.dto.request;

import lombok.Builder;
import org.netlight.habit_tracker.model.Frequency;

import java.time.LocalDate;

@Builder(toBuilder = true)
public record HabitRequest(
    String name,
    String description,
    Frequency frequency,
    LocalDate startDate
) {}
