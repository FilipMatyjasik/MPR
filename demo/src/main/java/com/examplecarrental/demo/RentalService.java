package com.examplecarrental.demo;

import org.springframework.stereotype.Component;

import java.time.LocalDate;

@Component
class RentalService {
    private final CarStorage carStorage;
    private final RentalStorage rentalStorage;

    public RentalService(CarStorage carStorage, RentalStorage rentalStorage) {
        this.carStorage = carStorage;
        this.rentalStorage = rentalStorage;
    }

    public void isavailable(String model, String vin, LocalDate from, LocalDate to) {
        rentalStorage.getList();
        
    }

    public void rental() {
        carStorage.getList();
    }
}
