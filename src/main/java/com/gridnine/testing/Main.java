package com.gridnine.testing;

import java.util.List;

public class Main {
    public static void main(String[] args) {
        // Получаем тестовый набор перелётов
        List<Flight> flights = FlightBuilder.createFlights();

        System.out.println("=== Все перелёты (исходный набор) ===");
        printFlights(flights);
        System.out.println();

        // Правило 1: вылет до текущего момента времени
        FlightFilter rule1 = new DepartureBeforeNowFilter();
        List<Flight> filteredByRule1 = rule1.filter(flights);
        System.out.println("=== Перелёты после исключения вылетевших до текущего момента ===");
        printFlights(filteredByRule1);
        System.out.println();

        // Правило 2: имеются сегменты с датой прилёта раньше даты вылета
        FlightFilter rule2 = new ArrivalBeforeDepartureFilter();
        List<Flight> filteredByRule2 = rule2.filter(flights);
        System.out.println("=== Перелёты после исключения сегментов с прилётом раньше вылета ===");
        printFlights(filteredByRule2);
        System.out.println();

        // Правило 3: общее время на земле превышает два часа
        FlightFilter rule3 = new GroundTimeExceedsTwoHoursFilter();
        List<Flight> filteredByRule3 = rule3.filter(flights);
        System.out.println("=== Перелёты после исключения с временем на земле > 2 часов ===");
        printFlights(filteredByRule3);
    }

    private static void printFlights(List<Flight> flights) {
        if (flights.isEmpty()) {
            System.out.println("(нет перелётов)");
            return;
        }
        for (int i = 0; i < flights.size(); i++) {
            System.out.println(i + 1 + ". " + flights.get(i));
        }
    }
}
