package com.gridnine.testing;

import java.time.LocalDateTime;

/**
 * Правило №1 — вылет до текущего момента
 */
public class DepartureBeforeNowFilter implements FlightFilter {
    private final LocalDateTime now;

    public DepartureBeforeNowFilter() {
        this.now = LocalDateTime.now();
    }

    // Для тестов, чтобы можно было подменить текущее время
    DepartureBeforeNowFilter(LocalDateTime now) {
        this.now = now;
    }

    @Override
    public boolean test(Flight flight) {
        // Перелёт подходит (остаётся), если ВСЕ сегменты не имеют вылета до now
        // То есть исключаем перелёты, у которых есть хотя бы один сегмент с вылетом в прошлом
        return flight.getSegments().stream()
                .noneMatch(segment -> segment.getDepartureDate().isBefore(now));
    }
}
