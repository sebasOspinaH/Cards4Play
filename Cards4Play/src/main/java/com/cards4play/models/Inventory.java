package com.cards4play.models;

import java.util.ArrayList;
import java.util.List;

public class Inventory {
    private List<Card> cards;
    private List<Booster> boosters;
    private List<SealedProduct> sealedProducts;
    private List<Accessory> accessories;

    public Inventory() {
        this.cards = new ArrayList<>();
        this.boosters = new ArrayList<>();
        this.sealedProducts = new ArrayList<>();
        this.accessories = new ArrayList<>();
    }

    public List<Card> getCards() { return cards; }
    public void setCards(List<Card> cards) { this.cards = cards; }

    public List<Booster> getBoosters() { return boosters; }
    public void setBoosters(List<Booster> boosters) { this.boosters = boosters; }

    public List<SealedProduct> getSealedProducts() { return sealedProducts; }
    public void setSealedProducts(List<SealedProduct> sealedProducts) { this.sealedProducts = sealedProducts; }

    public List<Accessory> getAccessories() { return accessories; }
    public void setAccessories(List<Accessory> accessories) { this.accessories = accessories; }
}
