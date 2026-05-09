package models;

public class Card extends Product {
    private String rarity;

    public Card(String identification, String name, double price, String rarity) {
        super(identification, name, price);
        this.rarity = rarity;
    }

    public String getRarity() {
        return rarity;
    }

    public void setRarity(String rarity) {
        this.rarity = rarity;
    }
}