package org.netlight.habit_tracker.repository;

import org.netlight.habit_tracker.model.Tracking;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface TrackingRepository extends JpaRepository<Tracking, UUID> {

    List<Tracking> findByHabitId(UUID habitId);

}
