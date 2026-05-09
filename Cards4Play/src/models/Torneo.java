package models;

import java.util.ArrayList;

public class Torneo {

    private String id;
    private String nombre;
    private String fecha;
    private int capacidad;
    private ArrayList<Client> listaClientesInscritos;

    public Torneo(String id, String nombre, String fecha, int capacidad) {
        this.id = id;
        this.nombre = nombre;
        this.fecha = fecha;
        this.capacidad = capacidad;
        this.listaClientesInscritos = new ArrayList<>();
    }

    public String getId() {
        return id;
    }
    public void setId(String id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getFecha() {
        return fecha;
    }
    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public int getCapacidad() {
        return capacidad;
    }
    public void setCapacidad(int capacidad) {
        this.capacidad = capacidad;
    }
}
