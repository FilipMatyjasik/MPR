package com.examplecarrental.demo;

import java.time.LocalDate;

public class Rental {
    private int klient_id;
    private String vin;
    private LocalDate from;
    private LocalDate to;

public Rental(int klient_id, String vin, LocalDate from, LocalDate to) {
    this.klient_id = klient_id;
    this.vin = vin;
}
}