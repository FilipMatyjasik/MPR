package com.examplecarrental.demo;

import java.time.LocalDate;

public class Rental {
    private int clientId;
    private String vin;
    private LocalDate from;
    private LocalDate to;

    //Konstruktor
    public Rental(int clientId, String vin, LocalDate from, LocalDate to) {
        this.clientId = clientId;
        this.vin = vin;
        this.from = from;
        this.to = to;
    }

    //Gettery
    public int getClientId() {
        return clientId;
    }

    public String getVin() {
        return vin;
    }

    public LocalDate getFrom() {
        return from;
    }

    public LocalDate getTo() {
        return to;
    }
}