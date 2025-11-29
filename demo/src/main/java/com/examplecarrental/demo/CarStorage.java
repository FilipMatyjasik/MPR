package com.examplecarrental.demo;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

@Component
class CarStorage {
    private final ArrayList<Car> cars = new ArrayList<>();

    public CarStorage() {
        cars.add(new Car("Mercedes", "S Klasa", "Y0A5550SAB"));
        cars.add(new Car("Audi", "A4", "Y0A8550SAB"));
        cars.add(new Car("Volkswagen", "Passat", "T69N550SAB"));
        cars.add(new Car("Renault", "Clio E-Tech", "Y0AXDD0SAB"));
    }

    public List<Car> getList() {
        return cars;
    }

}
