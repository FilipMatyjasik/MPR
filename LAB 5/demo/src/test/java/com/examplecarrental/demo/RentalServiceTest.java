package com.examplecarrental.demo;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class RentalServiceTest {

    @Mock
    private CarStorage carStorage;

    @Mock
    private RentalStorage rentalStorage;

    @InjectMocks
    private RentalService rentalService;

    // --- TESTY DLA isAvailable (Po VIN) ---

    @Test
    void shouldReturnTrue_WhenSpecificCarIsAvailable() {
        // given
        String vin = "VIN1";
        LocalDate start = LocalDate.of(2025, 1, 1);
        LocalDate end = LocalDate.of(2025, 1, 5);

        when(carStorage.getList()).thenReturn(List.of(new Car("Audi", "A4", vin)));
        when(rentalStorage.getList()).thenReturn(Collections.emptyList()); // Brak innych wynajmów

        // when
        boolean result = rentalService.isAvailable(vin, start, end);

        // then
        assertTrue(result);
    }

    @Test
    void shouldReturnFalse_WhenDatesOverlap() {
        // given - Sytuacja z tablicy (kolizja)
        String vin = "VIN1";
        Rental existing = new Rental(1, vin, LocalDate.of(2025, 1, 1), LocalDate.of(2025, 1, 10));

        when(carStorage.getList()).thenReturn(List.of(new Car("Audi", "A4", vin)));
        when(rentalStorage.getList()).thenReturn(List.of(existing));

        // when - próba wynajmu w środku istniejącego terminu
        boolean result = rentalService.isAvailable(vin, LocalDate.of(2025, 1, 2), LocalDate.of(2025, 1, 5));

        // then
        assertFalse(result);
    }

    // --- TESTY DLA findAvailableCar (Po Modelu) ---

    @Test
    void shouldFindAvailableCar_WhenOneIsFreeAndOneIsBusy() {
        // given
        // Mamy dwa Passaty. Jeden (VIN1) jest zajęty, drugi (VIN2) wolny.
        Car passatBusy = new Car("VW", "Passat", "VIN_BUSY");
        Car passatFree = new Car("VW", "Passat", "VIN_FREE");

        Rental busyRental = new Rental(1, "VIN_BUSY", LocalDate.of(2025, 1, 1), LocalDate.of(2025, 1, 10));

        when(carStorage.getList()).thenReturn(List.of(passatBusy, passatFree));
        when(rentalStorage.getList()).thenReturn(List.of(busyRental)); // Tylko VIN_BUSY ma rezerwację

        // when - szukamy Passata w terminie 1-5 stycznia
        Optional<Car> result = rentalService.findAvailableCar("Passat", LocalDate.of(2025, 1, 1), LocalDate.of(2025, 1, 5));

        // then
        assertTrue(result.isPresent());
        assertEquals("VIN_FREE", result.get().getVin(), "Powinien znaleźć ten wolny samochód");
    }

    @Test
    void shouldReturnEmpty_WhenAllCarsOfModelAreBusy() {
        // given
        Car passat1 = new Car("VW", "Passat", "VIN1");
        Rental rental1 = new Rental(1, "VIN1", LocalDate.of(2025, 1, 1), LocalDate.of(2025, 1, 10));

        when(carStorage.getList()).thenReturn(List.of(passat1));
        when(rentalStorage.getList()).thenReturn(List.of(rental1));

        // when
        Optional<Car> result = rentalService.findAvailableCar("Passat", LocalDate.of(2025, 1, 2), LocalDate.of(2025, 1, 5));

        // then
        assertTrue(result.isEmpty(), "Nie powinno znaleźć auta, bo jedyny Passat jest zajęty");
    }
}