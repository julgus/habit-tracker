package org.netlight.habit_tracker.model;

import jakarta.persistence.*;
import lombok.Builder;

import java.time.LocalDate;
import java.util.UUID;

@Entity
@Builder(toBuilder = true)
public record Habit(

    @Id
    @GeneratedValue
    UUID id,

    String name,

    String description,

    @Enumerated(EnumType.STRING)
    Frequency frequency,

    @Column(name = "start_date")
    LocalDate startDate

){ }
