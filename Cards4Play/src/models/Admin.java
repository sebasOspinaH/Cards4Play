package models;

import java.util.ArrayList;

public class Admin extends User{

    private ArrayList<Client> listaClientes;
    private Inventory inventario;

    public Admin(String id, String nombre, String contrasena, String email,
                 ArrayList<Client> listaClientes, Inventory inventario) {
        super(id, nombre, contrasena, email);
        this.listaClientes = listaClientes != null ? listaClientes : new ArrayList<>();
        this.inventario = inventario;
    }

    public ArrayList<Client> getListaClientes() {
        return listaClientes;
    }

    public Inventory getInventario() {
        return inventario;
    }
}
