package com.cards4play.models;

import java.util.ArrayList;
import java.util.List;

public class Tournament {
    private String id;
    private String name;
    private String date;
    private int capacity;
    private List<String> clientIds;

    public Tournament(String id, String name, String date, int capacity) {
        this.id = id;
        this.name = name;
        this.date = date;
        this.capacity = capacity;
        this.clientIds = new ArrayList<>();
    }

    public boolean hasCapacity() {
        return clientIds.size() < capacity;
    }

    public boolean isClientRegistered(String clientId) {
        return clientIds.contains(clientId);
    }

    public String getId() { return id; }
    public void setId(String id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getDate() { return date; }
    public void setDate(String date) { this.date = date; }

    public int getCapacity() { return capacity; }
    public void setCapacity(int capacity) { this.capacity = capacity; }

    public List<String> getClientIds() { return clientIds; }
    public void setClientIds(List<String> clientIds) { this.clientIds = clientIds; }

    public int getSpotsLeft() { return capacity - clientIds.size(); }
}
