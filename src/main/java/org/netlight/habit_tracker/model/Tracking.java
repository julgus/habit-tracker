package org.netlight.habit_tracker.model;

import jakarta.persistence.*;
import lombok.Builder;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Builder(toBuilder = true)
public record Tracking(

    @Id
    @GeneratedValue
    UUID id,

    @ManyToOne
    @JoinColumn(name = "habit_id")
    Habit habit,

    LocalDateTime timestamp,

    String note

) {}