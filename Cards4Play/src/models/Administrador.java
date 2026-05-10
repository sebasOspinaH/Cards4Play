package models;

import java.util.ArrayList;

public class Administrador extends User{

    private ArrayList<Client> listaClientes;
    private Inventory inventario;

    public Administrador(String id, String nombre, String contrasena, String email,
                         ArrayList<Client> listaClientes, Inventory inventario) {
        super(id, nombre, contrasena, email);
        this.listaClientes = listaClientes != null ? listaClientes : new ArrayList<>();
        this.inventario = inventario;
    }
}
