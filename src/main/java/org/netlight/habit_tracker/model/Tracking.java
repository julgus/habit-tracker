package org.netlight.habit_tracker.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.Objects;
import java.util.UUID;

@Entity
@Builder(toBuilder = true)
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Table(name = "trackings")
public class Tracking {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO) // Ensure this works with UUID
    private UUID id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "habit_id", nullable = false)
    private Habit habit;

    @Column(nullable = false)
    private LocalDateTime timestamp;

    @Column(length = 500)
    private String note;

    // Optional: Override equals and hashCode to avoid issues with proxies
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Tracking)) return false;
        final Tracking tracking = (Tracking) o;
        return Objects.equals(id, tracking.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

}
