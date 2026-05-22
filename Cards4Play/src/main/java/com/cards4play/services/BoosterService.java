package com.cards4play.services;

import com.cards4play.models.*;
import com.cards4play.persistence.DataStore;

import java.util.List;

public class BoosterService {

    private final AppState state;

    public BoosterService(AppState state) {
        this.state = state;
    }

    /**
     * Abre un booster del inventario del cliente.
     * Valida que el booster exista, pertenezca al cliente y no haya sido abierto antes.
     * Retorna las cartas obtenidas.
     */
    public List<Card> openBooster(String clientId, String boosterId) {
        Client client = state.findClientById(clientId);
        if (client == null)
            throw new IllegalArgumentException("Cliente no encontrado: " + clientId);

        Booster booster = client.getInventory().getBoosters().stream()
                .filter(b -> b.getIdentification().equals(boosterId))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException(
                        "El cliente no posee un booster con ID: " + boosterId));

        if (booster.isOpen())
            throw new IllegalStateException("Este booster ya fue abierto anteriormente");

        booster.setOpen(true);
        DataStore.save(state);
        return booster.getCardList();
    }

    /**
     * Lista los boosters del cliente indicando cuáles están abiertos o no.
     */
    public List<Booster> getClientBoosters(String clientId) {
        Client client = state.findClientById(clientId);
        if (client == null)
            throw new IllegalArgumentException("Cliente no encontrado: " + clientId);
        return client.getInventory().getBoosters();
    }
}
