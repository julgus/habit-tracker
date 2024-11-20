package org.netlight.habit_tracker.dto.request;

import com.sun.istack.NotNull;
import lombok.Builder;
import org.netlight.habit_tracker.model.Frequency;

import java.time.LocalDate;
import java.util.UUID;

@Builder(toBuilder = true)
public record HabitRequest(
    String name,
    String description,
    Frequency frequency,
    LocalDate startDate
) {}
