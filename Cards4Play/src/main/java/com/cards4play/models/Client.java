package com.cards4play.models;

import java.util.ArrayList;
import java.util.List;

public class Client extends User {
    private Inventory inventory;
    private List<Purchase> purchases;

    public Client(String identification, String name, String email, String password) {
        super(identification, name, email, password, "CLIENT");
        this.inventory = new Inventory();
        this.purchases = new ArrayList<>();
    }

    public Inventory getInventory() { return inventory; }
    public void setInventory(Inventory inventory) { this.inventory = inventory; }

    public List<Purchase> getPurchases() { return purchases; }
    public void setPurchases(List<Purchase> purchases) { this.purchases = purchases; }
}
