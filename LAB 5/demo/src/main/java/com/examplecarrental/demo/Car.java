package com.examplecarrental.demo;

public class Car {
    private String marka;
    private String model;
    private String vin;

    public Car(String marka, String model, String vin){
        this.marka = marka;
        this.model = model;
        this.vin = vin;
    }

    public String getMarka() {return marka;}
    public String getModel() {return model;}
    public String getVin() {return vin;}
}
