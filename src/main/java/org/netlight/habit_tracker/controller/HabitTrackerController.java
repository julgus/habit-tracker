package org.netlight.habit_tracker.controller;

import lombok.extern.log4j.Log4j2;
import org.netlight.habit_tracker.dto.request.HabitRequest;
import org.netlight.habit_tracker.dto.response.HabitResponse;
import org.netlight.habit_tracker.service.HabitService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

/**
 * The controller defines the API endpoints available to the client
 */
@Log4j2
@RestController
@RequestMapping("/api/habits")
public class HabitTrackerController {

    @Autowired
    private HabitService habitService;

    @GetMapping
    public List<HabitResponse> getAllHabits() {
        log.info("Fetching all habits");
        return habitService.getAllHabits();
    }

    @PostMapping
    public HabitResponse addHabit(@RequestBody final HabitRequest habitRequest) {
        System.out.println("Adding habit");
        return habitService.createHabit(habitRequest);
    }

    @GetMapping("/{id}")
    public HabitResponse getHabitById(@PathVariable("id") final UUID id) {
        System.out.println("Get habit by id");
        return habitService.getHabitResponseById(id);
    }

    // TODO: Add endpoint PATCH /habits/{id}: Update information for a specific habit.

    // TODO: Add endpoint DELETE /habits/{id}: Delete a specific habit.

    // TODO: Add endpoint POST /habits/{id}/tracking: Add a new tracking for a specific habit.

    // TODO: Add endpoint GET /habits/{id}/tracking: Retrieve all trackings for a specific habit.

}
