package com.gridnine.testing;

import java.time.LocalDateTime;
import java.util.List;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

// С JUnit. В задании было указано чтобы никкаких библиотек не добавлять. (У меня в IntelliJ IDEA была добавлена ранее)
public class MainTest {

    @Test
    void testDepartureBeforeNowFilter() {
        LocalDateTime future = LocalDateTime.now().plusDays(1);
        LocalDateTime past = LocalDateTime.now().minusDays(1);

        Flight goodFlight = new Flight(List.of(new Segment(future, future.plusHours(1))));
        Flight badFlight = new Flight(List.of(new Segment(past, past.plusHours(1))));

        DepartureBeforeNowFilter filter = new DepartureBeforeNowFilter();

        assertTrue(filter.test(goodFlight));
        assertFalse(filter.test(badFlight));
    }

    @Test
    void testArrivalBeforeDepartureFilter() {
        LocalDateTime now = LocalDateTime.now();

        Flight goodFlight = new Flight(List.of(new Segment(now, now.plusHours(1))));
        Flight badFlight = new Flight(List.of(new Segment(now, now.minusHours(1))));

        ArrivalBeforeDepartureFilter filter = new ArrivalBeforeDepartureFilter();

        assertTrue(filter.test(goodFlight));
        assertFalse(filter.test(badFlight));
    }

    @Test
    void testGroundTimeExceedsTwoHoursFilter() {
        LocalDateTime start = LocalDateTime.now();

        // Хороший: время на земле 1 час (норма)
        Flight goodFlight = new Flight(List.of(
                new Segment(start, start.plusHours(1)),
                new Segment(start.plusHours(2), start.plusHours(3))
        ));

        // Плохой: время на земле 3 часа (превышение)
        Flight badFlight = new Flight(List.of(
                new Segment(start, start.plusHours(1)),
                new Segment(start.plusHours(4), start.plusHours(5))
        ));

        GroundTimeExceedsTwoHoursFilter filter = new GroundTimeExceedsTwoHoursFilter();

        assertTrue(filter.test(goodFlight));
        assertFalse(filter.test(badFlight));
    }

    @Test
    void testFlightBuilderProducesSixFlights() {
        List<Flight> flights = FlightBuilder.createFlights();
        assertEquals(6, flights.size());
    }
}

//// Это если без JUnit (В задании было указано чтобы никкаких библиотек не добавлять)
// public class MainTest {
//
//    public static void main(String[] args) {
//        System.out.println("=== Ручное тестирование ===\n");
//
//        // Тест 1: DepartureBeforeNowFilter
//        System.out.println("Тест 1: DepartureBeforeNowFilter");
//        LocalDateTime future = LocalDateTime.now().plusDays(1);
//        LocalDateTime past = LocalDateTime.now().minusDays(1);
//
//        Flight goodFlight = new Flight(List.of(new Segment(future, future.plusHours(1))));
//        Flight badFlight = new Flight(List.of(new Segment(past, past.plusHours(1))));
//
//        DepartureBeforeNowFilter filter1 = new DepartureBeforeNowFilter();
//        System.out.println("  - Хороший перелёт (вылет в будущем): " + filter1.test(goodFlight) + " (должен быть true)");
//        System.out.println("  - Плохой перелёт (вылет в прошлом): " + filter1.test(badFlight) + " (должен быть false)");
//        System.out.println("  Результат: " + (filter1.test(goodFlight) && !filter1.test(badFlight) ? "ПРОЙДЕН" : "НЕ ПРОЙДЕН"));
//        System.out.println();
//
//        // Тест 2: ArrivalBeforeDepartureFilter
//        System.out.println("Тест 2: ArrivalBeforeDepartureFilter");
//        LocalDateTime now = LocalDateTime.now();
//
//        Flight goodFlight2 = new Flight(List.of(new Segment(now, now.plusHours(1))));
//        Flight badFlight2 = new Flight(List.of(new Segment(now, now.minusHours(1))));
//
//        ArrivalBeforeDepartureFilter filter2 = new ArrivalBeforeDepartureFilter();
//        System.out.println("  - Хороший перелёт (прилёт после вылета): " + filter2.test(goodFlight2) + " (должен быть true)");
//        System.out.println("  - Плохой перелёт (прилёт до вылета): " + filter2.test(badFlight2) + " (должен быть false)");
//        System.out.println("  Результат: " + (filter2.test(goodFlight2) && !filter2.test(badFlight2) ? "ПРОЙДЕН" : "НЕ ПРОЙДЕН"));
//        System.out.println();
//
//        // Тест 3: GroundTimeExceedsTwoHoursFilter
//        System.out.println("Тест 3: GroundTimeExceedsTwoHoursFilter");
//        LocalDateTime start = LocalDateTime.now();
//
//        Flight goodFlight3 = new Flight(List.of(
//                new Segment(start, start.plusHours(1)),
//                new Segment(start.plusHours(2), start.plusHours(3))
//        ));
//
//        Flight badFlight3 = new Flight(List.of(
//                new Segment(start, start.plusHours(1)),
//                new Segment(start.plusHours(4), start.plusHours(5))
//        ));
//
//        GroundTimeExceedsTwoHoursFilter filter3 = new GroundTimeExceedsTwoHoursFilter();
//        System.out.println("  - Хороший перелёт (время на земле 1 час): " + filter3.test(goodFlight3) + " (должен быть true)");
//        System.out.println("  - Плохой перелёт (время на земле 3 часа): " + filter3.test(badFlight3) + " (должен быть false)");
//        System.out.println("  Результат: " + (filter3.test(goodFlight3) && !filter3.test(badFlight3) ? "ПРОЙДЕН" : "НЕ ПРОЙДЕН"));
//        System.out.println();
//
//        // Тест 4: FlightBuilder возвращает 6 перелётов
//        System.out.println("Тест 4: FlightBuilder");
//        List<Flight> flights = FlightBuilder.createFlights();
//        System.out.println("  - Количество перелётов: " + flights.size() + " (должно быть 6)");
//        System.out.println("  Результат: " + (flights.size() == 6 ? "ПРОЙДЕН" : "НЕ ПРОЙДЕН"));
//
//        System.out.println("\n=== Ручное тестирование завершено ===");
//    }
//}