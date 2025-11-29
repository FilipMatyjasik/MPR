package com.examplecarrental.demo;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

@Component
class RentalStorage {
    private final ArrayList<Rental> rentals = new ArrayList<>();

    public RentalStorage() {
        rentals.add(new Rental(1,"Y0A5550SAB", LocalDate.of(2025, 11, 15), LocalDate.of(2025, 12, 16)));
        rentals.add(new Rental(2,"Y0A8550SAB", LocalDate.of(2025, 11, 30), LocalDate.of(2025, 12, 18)));
        rentals.add(new Rental(3,"T69N550SAB", LocalDate.of(2024, 12, 15), LocalDate.of(2026, 1, 6)));
        rentals.add(new Rental(6969,"Y0AXDD0SAB", LocalDate.of(2023, 10, 10), LocalDate.of(2026, 12, 21)));
    }
    public List<Rental> getList() {
        return rentals;
    }

}
