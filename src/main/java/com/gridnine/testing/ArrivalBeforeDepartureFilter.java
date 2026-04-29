package com.gridnine.testing;

/**
 * Правило №2 — прилёт раньше вылета в одном сегменте
 */
public class ArrivalBeforeDepartureFilter implements FlightFilter {
    @Override
    public boolean test(Flight flight) {
        // Перелёт подходит, если НЕТ ни одного сегмента, где прилёт раньше вылета
        return flight.getSegments().stream()
                .noneMatch(segment -> segment.getArrivalDate().isBefore(segment.getDepartureDate()));
    }
}
