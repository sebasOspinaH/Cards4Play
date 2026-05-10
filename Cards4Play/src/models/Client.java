package models;

import java.util.ArrayList;

public class Client extends User{
    private Inventory inventory;
    private ArrayList<Purchase> purchases;

    public Client(String identification, String name, String email, String password) {
        super(identification, name, email, password);
        this.inventory = new Inventory();
        this.purchases = new ArrayList<>();
    }
    public Inventory getInventory() {
        return inventory;
    }

    public ArrayList<Purchase> getPurchases() {
        return purchases;
    }
}
