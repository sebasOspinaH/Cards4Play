package models;

import java.util.ArrayList;

public class Tournament {

    private String id;
    private String name;
    private String date;
    private int capacity;
    private ArrayList<Client> clientList;

    public Tournament(String id, String name, String date, int capacity) {
        this.id = id;
        this.name = name;
        this.date = date;
        this.capacity = capacity;
        this.clientList = new ArrayList<>();
    }

    public String getId() {
        return id;
    }
    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }
    public void setName(String nombre) {
        this.name = nombre;
    }

    public String getDate() {
        return date;
    }
    public void setDate(String fecha) {
        this.date = fecha;
    }

    public int getCapacity() {
        return capacity;
    }
    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }

    public ArrayList<Client> getClientList() {
        return clientList;
    }
}
