package com.example.a72button;
public class  Bottle {

    private String name = "Pepsi Max";

    private String manufacturer = "Pepsi";

    private double total_energy = 0.3;

    private double size = 0.5;

    private double prize = 1.8;

    public  Bottle(){}
    public Bottle setName(String namef) {
        this.name = namef;
        return this;
    }

    public Bottle setManufacturer(String manuf) {
        this.manufacturer = manuf;
        return this;
    }

    public Bottle setEnergy(double totE){
        this.total_energy = totE;
        return this;
    }

    public Bottle setSize(double sizef) {
        this.size = sizef;
        return this;
    }

    public Bottle setPrize(double prizef) {
        this. prize = prizef;
        return this;
    }

    public String getName(){
        return name;
    }
    public String getManufacturer(){
        return manufacturer;
    }
    public double getEnergy(){
        return total_energy;
    }
    public double getSize(){
        return size;
    }
    public double getPrize(){
        return prize;
    }
}