package com.cards4play.models;

public class Card extends Product {
    private String rarity; // COMMON, UNCOMMON, RARE, MYTHIC

    public Card(String identification, String name, double priceUSD, String rarity) {
        super(identification, name, priceUSD, "CARD");
        this.rarity = rarity;
    }

    public String getRarity() { return rarity; }
    public void setRarity(String rarity) { this.rarity = rarity; }
}
