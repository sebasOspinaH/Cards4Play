package com.cards4play.models;

public abstract class Product {
    private String identification;
    private String name;
    private double priceUSD;
    private double priceCOP;
    private String type; // "CARD", "BOOSTER", "SEALED", "ACCESSORY"

    public Product(String identification, String name, double priceUSD, String type) {
        this.identification = identification;
        this.name = name;
        this.priceUSD = priceUSD;
        this.type = type;
    }

    public String getIdentification() { return identification; }
    public void setIdentification(String identification) { this.identification = identification; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public double getPriceUSD() { return priceUSD; }
    public void setPriceUSD(double priceUSD) { this.priceUSD = priceUSD; }

    public double getPriceCOP() { return priceCOP; }
    public void setPriceCOP(double priceCOP) { this.priceCOP = priceCOP; }

    public String getType() { return type; }
    public void setType(String type) { this.type = type; }
}
