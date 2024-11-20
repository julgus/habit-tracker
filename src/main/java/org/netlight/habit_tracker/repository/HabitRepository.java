package org.netlight.habit_tracker.repository;

import org.netlight.habit_tracker.model.Habit;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface HabitRepository extends JpaRepository<Habit, UUID> {
}
