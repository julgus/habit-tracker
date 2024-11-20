package org.netlight.habit_tracker.exception;

public class HabitNotFoundException extends RuntimeException {

    public HabitNotFoundException(String message) {
        super(message);
    }

}