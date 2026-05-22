package com.cards4play.models;

import java.util.ArrayList;
import java.util.List;

public class AppState {
    private Admin admin;
    private List<Client> clients;
    private Inventory storeInventory; // inventario de la tienda (admin)
    private List<Tournament> tournaments;
    private List<Purchase> allPurchases;

    public AppState() {
        this.clients = new ArrayList<>();
        this.storeInventory = new Inventory();
        this.tournaments = new ArrayList<>();
        this.allPurchases = new ArrayList<>();
    }

    public Admin getAdmin() { return admin; }
    public void setAdmin(Admin admin) { this.admin = admin; }

    public List<Client> getClients() { return clients; }
    public void setClients(List<Client> clients) { this.clients = clients; }

    public Inventory getStoreInventory() { return storeInventory; }
    public void setStoreInventory(Inventory storeInventory) { this.storeInventory = storeInventory; }

    public List<Tournament> getTournaments() { return tournaments; }
    public void setTournaments(List<Tournament> tournaments) { this.tournaments = tournaments; }

    public List<Purchase> getAllPurchases() { return allPurchases; }
    public void setAllPurchases(List<Purchase> allPurchases) { this.allPurchases = allPurchases; }

    // --- helpers ---
    public Client findClientById(String id) {
        return clients.stream().filter(c -> c.getIdentification().equals(id)).findFirst().orElse(null);
    }

    public Client findClientByEmail(String email) {
        return clients.stream().filter(c -> c.getEmail().equals(email)).findFirst().orElse(null);
    }

    public Tournament findTournamentById(String id) {
        return tournaments.stream().filter(t -> t.getId().equals(id)).findFirst().orElse(null);
    }

    public Card findCardById(String id) {
        return storeInventory.getCards().stream()
                .filter(c -> c.getIdentification().equals(id)).findFirst().orElse(null);
    }

    public Booster findBoosterById(String id) {
        return storeInventory.getBoosters().stream()
                .filter(b -> b.getIdentification().equals(id)).findFirst().orElse(null);
    }

    public SealedProduct findSealedById(String id) {
        return storeInventory.getSealedProducts().stream()
                .filter(s -> s.getIdentification().equals(id)).findFirst().orElse(null);
    }

    public Accessory findAccessoryById(String id) {
        return storeInventory.getAccessories().stream()
                .filter(a -> a.getIdentification().equals(id)).findFirst().orElse(null);
    }
}
