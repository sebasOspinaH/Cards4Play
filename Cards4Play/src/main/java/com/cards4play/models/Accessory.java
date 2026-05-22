package com.cards4play.models;

public class Accessory extends Product {
    private String accessoryType; // SLEEVE, BINDER, PLAYMAT, etc.

    public Accessory(String identification, String name, double priceUSD, String accessoryType) {
        super(identification, name, priceUSD, "ACCESSORY");
        this.accessoryType = accessoryType;
    }

    public String getAccessoryType() { return accessoryType; }
    public void setAccessoryType(String accessoryType) { this.accessoryType = accessoryType; }
}
