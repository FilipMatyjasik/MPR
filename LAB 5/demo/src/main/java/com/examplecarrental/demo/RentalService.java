package com.examplecarrental.demo;

import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Component
public class RentalService {
    private final CarStorage carStorage;
    private final RentalStorage rentalStorage;

    public RentalService(CarStorage carStorage, RentalStorage rentalStorage) {
        this.carStorage = carStorage;
        this.rentalStorage = rentalStorage;
    }

    /**
     * SCENARIUSZ 1: Sprawdzenie dostępności KONKRETNEGO egzemplarza (po VIN).
     * Przydatne, gdy system chce zweryfikować auto przed samym zapisem.
     */
    public boolean isAvailable(String vin, LocalDate from, LocalDate to) {
        // Jeśli auto nie istnieje w bazie, to nie jest dostępne
        boolean carExists = carStorage.getList().stream()
                .anyMatch(c -> c.getVin().equals(vin));

        if (!carExists) {
            return false;
        }

        // Sprawdzamy, czy ten konkretny VIN nie ma kolizji w datach
        return !hasCollisions(vin, from, to);
    }

    /**
     * SCENARIUSZ 2: Szukanie PIERWSZEGO WOLNEGO auta danego modelu.
     * Przydatne, gdy klient pyta: "Czy macie jakiegoś Passata?"
     */
    public Optional<Car> findAvailableCar(String model, LocalDate from, LocalDate to) {
        // 1. Pobierz wszystkie auta tego modelu
        List<Car> carsOfModel = carStorage.getList().stream()
                .filter(c -> c.getModel().equalsIgnoreCase(model))
                .toList();

        // 2. Przejdź po nich i znajdź pierwsze, które nie ma kolizji
        for (Car car : carsOfModel) {
            if (!hasCollisions(car.getVin(), from, to)) {
                return Optional.of(car); // Zwracamy znalezione auto
            }
        }

        // 3. Jeśli pętla się skończyła, znaczy że wszystkie Passaty są zajęte
        return Optional.empty();
    }

    /**
     * Główna metoda wynajmu.
     * Wyrzuca wyjątek, jeśli ktoś próbuje wynająć zajęte auto.
     */
    public Rental rent(String vin, int clientId, LocalDate from, LocalDate to) {
        if (isAvailable(vin, from, to)) {
            Rental newRental = new Rental(clientId, vin, from, to);
            rentalStorage.addRental(newRental);
            return newRental;
        } else {
            throw new IllegalStateException("Wybrany samochód jest niedostępny w tym terminie.");
        }
    }

    // --- Metody Pomocnicze (Private) ---

    // Sprawdza czy dany VIN ma jakiekolwiek kolizje w rezerwacjach
    private boolean hasCollisions(String vin, LocalDate start, LocalDate end) {
        return rentalStorage.getList().stream()
                .filter(rental -> rental.getVin().equals(vin))
                .anyMatch(rental -> isOverlapping(start, end, rental));
    }

    // Logika matematyczna z Twojej tablicy
    private boolean isOverlapping(LocalDate start, LocalDate end, Rental existing) {
        return !start.isAfter(existing.getTo()) && !end.isBefore(existing.getFrom());
    }
}