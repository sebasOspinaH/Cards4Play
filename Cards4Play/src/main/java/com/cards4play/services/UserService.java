package com.cards4play.services;

import com.cards4play.models.*;
import com.cards4play.persistence.DataStore;

import java.util.List;

public class UserService {

    private final AppState state;

    public UserService(AppState state) {
        this.state = state;
    }

    // --- Autenticación ---

    public User login(String email, String password) {
        Admin admin = state.getAdmin();
        if (admin.getEmail().equals(email) && admin.getPassword().equals(password)) {
            return admin;
        }
        Client client = state.findClientByEmail(email);
        if (client != null && client.getPassword().equals(password)) {
            return client;
        }
        return null;
    }

    // --- Gestión de clientes (ADMIN) ---

    public Client registerClient(String id, String name, String email, String password) {
        if (state.findClientByEmail(email) != null) {
            throw new IllegalArgumentException("Ya existe un cliente con ese email: " + email);
        }
        Client client = new Client(id, name, email, password);
        state.getClients().add(client);
        DataStore.save(state);
        return client;
    }

    public List<Client> getAllClients() {
        return state.getClients();
    }

    public Client getClientById(String id) {
        Client c = state.findClientById(id);
        if (c == null) throw new IllegalArgumentException("Cliente no encontrado: " + id);
        return c;
    }

    public Client updateClient(String id, String name, String email) {
        Client c = getClientById(id);
        if (name != null) c.setName(name);
        if (email != null) c.setEmail(email);
        DataStore.save(state);
        return c;
    }

    public void deleteClient(String id) {
        Client c = getClientById(id);
        state.getClients().remove(c);
        DataStore.save(state);
    }
}
