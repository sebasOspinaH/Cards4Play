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

    public void setCards(ArrayList<Card> cards) {
        this.cards = cards;
    }

    public ArrayList<Booster> getBoosters() {
        return boosters;
    }

    public void setBoosters(ArrayList<Booster> boosters) {
        this.boosters = boosters;
    }

    public ArrayList<Accesory> getAccesories() {
        return accesories;
    }

    public void setAccesories(ArrayList<Accesory> accesories) {
        this.accesories = accesories;
    }

    public ArrayList<SealedProduct> getSealedProducts() {
        return sealedProducts;
    }

    public void setSealedProducts(ArrayList<SealedProduct> sealedProducts) {
        this.sealedProducts = sealedProducts;
    }
}
