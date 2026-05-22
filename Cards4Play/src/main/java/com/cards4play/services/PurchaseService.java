package com.cards4play.services;

import com.cards4play.models.*;
import com.cards4play.persistence.DataStore;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

public class PurchaseService {

    private final AppState state;
    private static final DateTimeFormatter FMT = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    public PurchaseService(AppState state) {
        this.state = state;
    }

    /**
     * Procesa una compra. El cliente puede comprar cualquier combinación de:
     * cartas, boosters (sin abrir), productos sellados, accesorios.
     *
     * @param clientId  ID del cliente
     * @param productIds lista de IDs de productos a comprar
     */
    public Purchase purchase(String clientId, List<String> productIds) {
        Client client = state.findClientById(clientId);
        if (client == null)
            throw new IllegalArgumentException("Cliente no encontrado: " + clientId);
        if (productIds == null || productIds.isEmpty())
            throw new IllegalArgumentException("Debe incluir al menos un producto");

        String purchaseId = "PUR-" + System.currentTimeMillis();
        String date = LocalDateTime.now().format(FMT);
        Purchase purchase = new Purchase(purchaseId, clientId, date);

        double totalCOP = 0;

        for (String pid : productIds) {
            Product found = resolveProduct(pid);
            if (found == null)
                throw new IllegalArgumentException("Producto no encontrado en el inventario de la tienda: " + pid);

            // Agregar al inventario del cliente
            addToClientInventory(client, found);
            purchase.getProducts().add(found);
            totalCOP += found.getPriceCOP();
        }

        purchase.setTotalCOP(totalCOP);
        client.getPurchases().add(purchase);
        state.getAllPurchases().add(purchase);
        DataStore.save(state);
        return purchase;
    }

    private Product resolveProduct(String id) {
        Product p = state.findCardById(id);
        if (p != null) return p;
        p = state.findBoosterById(id);
        if (p != null) return p;
        p = state.findSealedById(id);
        if (p != null) return p;
        p = state.findAccessoryById(id);
        return p;
    }

    private void addToClientInventory(Client client, Product product) {
        Inventory inv = client.getInventory();
        String type = product.getType();
        switch (type) {
            case "CARD":
                inv.getCards().add((Card) product);
                break;
            case "BOOSTER":
                // Se crea una COPIA del booster para el cliente (cerrado)
                Booster original = (Booster) product;
                Booster copy = new Booster(
                    original.getIdentification() + "-" + System.nanoTime(),
                    original.getName(),
                    original.getPriceUSD()
                );
                copy.setPriceCOP(original.getPriceCOP());
                copy.setCardList(new ArrayList<>(original.getCardList()));
                inv.getBoosters().add(copy);
                break;
            case "SEALED":
                inv.getSealedProducts().add((SealedProduct) product);
                break;
            case "ACCESSORY":
                inv.getAccessories().add((Accessory) product);
                break;
        }
    }

    public List<Purchase> getClientHistory(String clientId) {
        Client client = state.findClientById(clientId);
        if (client == null)
            throw new IllegalArgumentException("Cliente no encontrado: " + clientId);
        return client.getPurchases();
    }

    public List<Purchase> getAllPurchases() {
        return state.getAllPurchases();
    }

    public Purchase getPurchaseById(String purchaseId) {
        return state.getAllPurchases().stream()
                .filter(p -> p.getId().equals(purchaseId))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Compra no encontrada: " + purchaseId));
    }
}
