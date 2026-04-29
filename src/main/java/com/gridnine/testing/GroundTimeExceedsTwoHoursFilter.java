package com.gridnine.testing;

import java.time.Duration;
import java.util.List;

/**
 * Правило №3 — время на земле > 2 часов
 */
public class GroundTimeExceedsTwoHoursFilter implements FlightFilter {
    private static final long MAX_GROUND_TIME_HOURS = 2;

    @Override
    public boolean test(Flight flight) {
        List<Segment> segments = flight.getSegments();

        // Перелёт с одним сегментом всегда подходит (нет времени на земле)
        if (segments.size() <= 1) {
            return true;
        }

        long totalGroundMinutes = 0;

        for (int i = 0; i < segments.size() - 1; i++) {
            Segment current = segments.get(i);
            Segment next = segments.get(i + 1);

            // Время на земле = вылет следующего - прилёт текущего
            long groundMinutes = Duration.between(
                    current.getArrivalDate(),
                    next.getDepartureDate()
            ).toMinutes();

            if (groundMinutes > 0) {
                totalGroundMinutes += groundMinutes;
            }
        }

        // Перелёт подходит (остаётся), если общее время на земле НЕ превышает 2 часа
        return totalGroundMinutes <= MAX_GROUND_TIME_HOURS * 60;
    }
}
