package org.netlight.habit_tracker.dto.response;

import lombok.Builder;
import org.netlight.habit_tracker.model.Frequency;

import java.time.LocalDate;
import java.util.UUID;

@Builder(toBuilder = true)
public record HabitResponse(
    UUID id,
    String name,
    String description,
    Frequency frequency,
    LocalDate startDate
) {}