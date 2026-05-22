package com.cards4play.services;

import com.cards4play.models.*;
import com.cards4play.persistence.DataStore;

import java.util.List;

public class InventoryService {

    private final AppState state;

    public InventoryService(AppState state) {
        this.state = state;
    }

    private Inventory inv() {
        return state.getStoreInventory();
    }

    // ===================== CARTAS =====================

    public Card addCard(String id, String name, double priceUSD, String rarity) {
        if (state.findCardById(id) != null)
            throw new IllegalArgumentException("Ya existe una carta con ID: " + id);
        double priceCOP = ExchangeRateService.convertToCOP(priceUSD);
        Card card = new Card(id, name, priceUSD, rarity);
        card.setPriceCOP(priceCOP);
        inv().getCards().add(card);
        DataStore.save(state);
        return card;
    }

    public List<Card> getAllCards() {
        return inv().getCards();
    }

    public Card getCardById(String id) {
        Card c = state.findCardById(id);
        if (c == null) throw new IllegalArgumentException("Carta no encontrada: " + id);
        return c;
    }

    public Card updateCard(String id, String name, Double priceUSD, String rarity) {
        Card c = getCardById(id);
        if (name != null) c.setName(name);
        if (rarity != null) c.setRarity(rarity);
        if (priceUSD != null) {
            c.setPriceUSD(priceUSD);
            c.setPriceCOP(ExchangeRateService.convertToCOP(priceUSD));
        }
        DataStore.save(state);
        return c;
    }

    public void deleteCard(String id) {
        Card c = getCardById(id);
        inv().getCards().remove(c);
        DataStore.save(state);
    }

    // ===================== BOOSTERS =====================

    /**
     * Crea un booster en el inventario de la tienda con 10 cartas aleatorias.
     * Si hay menos de 10 cartas distintas, repite las existentes.
     */
    public Booster addBooster(String id, String name, double priceUSD) {
        if (state.findBoosterById(id) != null)
            throw new IllegalArgumentException("Ya existe un booster con ID: " + id);

        List<Card> available = inv().getCards();
        if (available.isEmpty())
            throw new IllegalStateException("No hay cartas en el inventario para crear un booster");

        double priceCOP = ExchangeRateService.convertToCOP(priceUSD);
        Booster booster = new Booster(id, name, priceUSD);
        booster.setPriceCOP(priceCOP);
        booster.setCardList(selectRandomCards(available, 10));
        inv().getBoosters().add(booster);
        DataStore.save(state);
        return booster;
    }

    private List<Card> selectRandomCards(List<Card> pool, int count) {
        List<Card> result = new java.util.ArrayList<>();
        java.util.Random rng = new java.util.Random();
        for (int i = 0; i < count; i++) {
            result.add(pool.get(rng.nextInt(pool.size())));
        }
        return result;
    }

    public List<Booster> getAllBoosters() {
        return inv().getBoosters();
    }

    public Booster getBoosterById(String id) {
        Booster b = state.findBoosterById(id);
        if (b == null) throw new IllegalArgumentException("Booster no encontrado: " + id);
        return b;
    }

    public void deleteBooster(String id) {
        Booster b = getBoosterById(id);
        inv().getBoosters().remove(b);
        DataStore.save(state);
    }

    // ===================== PRODUCTOS SELLADOS =====================

    public SealedProduct addSealed(String id, String name, double priceUSD, String edition) {
        if (state.findSealedById(id) != null)
            throw new IllegalArgumentException("Ya existe un producto sellado con ID: " + id);
        double priceCOP = ExchangeRateService.convertToCOP(priceUSD);
        SealedProduct sp = new SealedProduct(id, name, priceUSD, edition);
        sp.setPriceCOP(priceCOP);
        inv().getSealedProducts().add(sp);
        DataStore.save(state);
        return sp;
    }

    public List<SealedProduct> getAllSealed() {
        return inv().getSealedProducts();
    }

    public SealedProduct getSealedById(String id) {
        SealedProduct s = state.findSealedById(id);
        if (s == null) throw new IllegalArgumentException("Producto sellado no encontrado: " + id);
        return s;
    }

    public void deleteSealed(String id) {
        SealedProduct s = getSealedById(id);
        inv().getSealedProducts().remove(s);
        DataStore.save(state);
    }

    // ===================== ACCESORIOS =====================

    public Accessory addAccessory(String id, String name, double priceUSD, String accessoryType) {
        if (state.findAccessoryById(id) != null)
            throw new IllegalArgumentException("Ya existe un accesorio con ID: " + id);
        double priceCOP = ExchangeRateService.convertToCOP(priceUSD);
        Accessory acc = new Accessory(id, name, priceUSD, accessoryType);
        acc.setPriceCOP(priceCOP);
        inv().getAccessories().add(acc);
        DataStore.save(state);
        return acc;
    }

    public List<Accessory> getAllAccessories() {
        return inv().getAccessories();
    }

    public Accessory getAccessoryById(String id) {
        Accessory a = state.findAccessoryById(id);
        if (a == null) throw new IllegalArgumentException("Accesorio no encontrado: " + id);
        return a;
    }

    public void deleteAccessory(String id) {
        Accessory a = getAccessoryById(id);
        inv().getAccessories().remove(a);
        DataStore.save(state);
    }

    // ===================== INVENTARIO CLIENTE =====================

    public Inventory getClientInventory(String clientId) {
        Client c = state.findClientById(clientId);
        if (c == null) throw new IllegalArgumentException("Cliente no encontrado: " + clientId);
        return c.getInventory();
    }
}
