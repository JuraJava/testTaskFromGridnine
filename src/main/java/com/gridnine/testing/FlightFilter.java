package com.gridnine.testing;

import java.util.List;
import java.util.stream.Collectors;

@FunctionalInterface
public interface FlightFilter {
    boolean test(Flight flight);

    default List<Flight> filter(List<Flight> flights) {
        return flights.stream()
                .filter(this::test)
                .collect(Collectors.toList());
    }
}