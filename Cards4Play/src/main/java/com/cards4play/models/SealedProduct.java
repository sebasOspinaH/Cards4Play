package com.cards4play.models;

public class SealedProduct extends Product {
    private String edition;

    public SealedProduct(String identification, String name, double priceUSD, String edition) {
        super(identification, name, priceUSD, "SEALED");
        this.edition = edition;
    }

    public String getEdition() { return edition; }
    public void setEdition(String edition) { this.edition = edition; }
}
