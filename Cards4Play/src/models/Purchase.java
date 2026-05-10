package models;

import java.util.ArrayList;

public class Purchase {

    private String id;
    private String date;
    private ArrayList<Product> products;

    public Purchase(String id, String date, Client client) {
        this.id = id;
        this.date = date;
        this.products = new ArrayList<>();
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public ArrayList<Product> getProducts() {
        return products;
    }
}
