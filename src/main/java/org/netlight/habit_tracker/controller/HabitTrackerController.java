package org.netlight.habit_tracker.controller;

import org.netlight.habit_tracker.dto.request.HabitRequest;
import org.netlight.habit_tracker.dto.response.HabitResponse;
import org.netlight.habit_tracker.model.Habit;
import org.netlight.habit_tracker.model.Tracking;
import org.netlight.habit_tracker.service.HabitService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/habits")
public class HabitTrackerController {

    @Autowired
    private HabitService habitService;

    @GetMapping
    public List<HabitResponse> getAllHabits() {
        return habitService.getAllHabits();
    }

    @GetMapping("/{id}")
    public HabitResponse getHabitById(@PathVariable final UUID id) {
        return habitService.getHabitResponseById(id);
    }

    @PostMapping
    public HabitResponse addHabit(@RequestBody final HabitRequest habitRequest) {
        return habitService.createHabit(habitRequest);
    }

    @PatchMapping("/{id}")
    public HabitResponse updateHabit(@PathVariable final UUID id,
                             @RequestBody final HabitRequest habitRequest) {
        return habitService.updateHabit(id, habitRequest);
    }

    @DeleteMapping("/{id}")
    public void deleteHabit(@PathVariable final UUID id) {
        habitService.deleteHabit(id);
    }

    @PostMapping("{habitId}/tracking")
    public Tracking addTracking(@PathVariable final UUID habitId,
                                @RequestBody final String note) {
        return habitService.addTrackingEntry(habitId, note);
    }

    @GetMapping("{habitId}/tracking")
    public List<Tracking> getTrackings(@PathVariable final UUID habitId) {
        return habitService.getAllTrackingsForHabit(habitId);
    }

}
