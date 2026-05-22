package com.cards4play.models;

import java.util.ArrayList;
import java.util.List;

public class Purchase {
    private String id;
    private String clientId;
    private String date;
    private List<Product> products;
    private double totalCOP;

    public Purchase(String id, String clientId, String date) {
        this.id = id;
        this.clientId = clientId;
        this.date = date;
        this.products = new ArrayList<>();
        this.totalCOP = 0;
    }

    public String getId() { return id; }
    public void setId(String id) { this.id = id; }

    public String getClientId() { return clientId; }
    public void setClientId(String clientId) { this.clientId = clientId; }

    public String getDate() { return date; }
    public void setDate(String date) { this.date = date; }

    public List<Product> getProducts() { return products; }
    public void setProducts(List<Product> products) { this.products = products; }

    public double getTotalCOP() { return totalCOP; }
    public void setTotalCOP(double totalCOP) { this.totalCOP = totalCOP; }
}
