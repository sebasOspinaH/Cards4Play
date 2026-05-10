package models;

import java.util.ArrayList;

public class Inventory {
    ArrayList<Card> cards;
    ArrayList<Booster> boosters;
    ArrayList<Accesory> accesories;
    ArrayList<SealedProduct> sealedProducts;

    public Inventory() {
        this.cards = new ArrayList<>();
        this.boosters = new ArrayList<>();
        this.accesories = new ArrayList<>();
        this.sealedProducts = new ArrayList<>();
    }

    public ArrayList<Card> getCards() {
        return cards;
    }

    public ArrayList<Booster> getBoosters() {
        return boosters;
    }

    public ArrayList<Accesory> getAccesories() {
        return accesories;
    }

    public ArrayList<SealedProduct> getSealedProducts() {
        return sealedProducts;
    }
}
