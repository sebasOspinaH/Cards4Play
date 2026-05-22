package com.cards4play.routes;

import com.cards4play.controllers.*;
import static spark.Spark.*;

/**
 * Registra todas las rutas de la API REST de Cards4Play.
 *
 * Autenticación: cabeceras HTTP  X-User-Email  y  X-User-Password
 *
 * Prefijo de rutas:
 *   /auth          → autenticación
 *   /clients       → gestión de clientes
 *   /inventory     → inventario de la tienda
 *   /tournaments   → torneos
 *   /purchases     → compras e historial
 */
public class Router {

    public static void register(
            UserController userCtrl,
            InventoryController inventoryCtrl,
            BoosterController boosterCtrl,
            TournamentController tournamentCtrl,
            PurchaseController purchaseCtrl
    ) {
        // CORS (para desarrollo)
        options("/*", (req, res) -> {
            res.header("Access-Control-Allow-Headers", "X-User-Email,X-User-Password,Content-Type");
            res.header("Access-Control-Allow-Methods", "GET,POST,PUT,DELETE,OPTIONS");
            return "OK";
        });
        before((req, res) -> res.header("Access-Control-Allow-Origin", "*"));

        // ---- AUTH ----
        post("/auth/login", userCtrl::login);

        // ---- CLIENTES ----
        get   ("/clients",     userCtrl::getAllClients);
        post  ("/clients",     userCtrl::registerClient);
        get   ("/clients/:id", userCtrl::getClientById);
        put   ("/clients/:id", userCtrl::updateClient);
        delete("/clients/:id", userCtrl::deleteClient);

        // Inventario del cliente
        get("/clients/:id/inventory", inventoryCtrl::getClientInventory);

        // Boosters del cliente
        get ("/clients/:id/boosters", boosterCtrl::getClientBoosters);
        post("/clients/:id/boosters/:boosterId/open", boosterCtrl::openBooster);

        // Historial de compras del cliente
        get("/clients/:id/purchases", purchaseCtrl::getClientHistory);

        // ---- INVENTARIO TIENDA ----
        // Cartas
        get   ("/inventory/cards",     inventoryCtrl::getAllCards);
        get   ("/inventory/cards/:id", inventoryCtrl::getCard);
        post  ("/inventory/cards",     inventoryCtrl::addCard);
        put   ("/inventory/cards/:id", inventoryCtrl::updateCard);
        delete("/inventory/cards/:id", inventoryCtrl::deleteCard);

        // Boosters
        get   ("/inventory/boosters",     inventoryCtrl::getAllBoosters);
        get   ("/inventory/boosters/:id", inventoryCtrl::getBooster);
        post  ("/inventory/boosters",     inventoryCtrl::addBooster);
        delete("/inventory/boosters/:id", inventoryCtrl::deleteBooster);

        // Productos sellados
        get   ("/inventory/sealed",     inventoryCtrl::getAllSealed);
        post  ("/inventory/sealed",     inventoryCtrl::addSealed);
        delete("/inventory/sealed/:id", inventoryCtrl::deleteSealed);

        // Accesorios
        get   ("/inventory/accessories",     inventoryCtrl::getAllAccessories);
        post  ("/inventory/accessories",     inventoryCtrl::addAccessory);
        delete("/inventory/accessories/:id", inventoryCtrl::deleteAccessory);

        // ---- TORNEOS ----
        get   ("/tournaments",                          tournamentCtrl::getAll);
        get   ("/tournaments/:id",                      tournamentCtrl::getById);
        post  ("/tournaments",                          tournamentCtrl::create);
        put   ("/tournaments/:id",                      tournamentCtrl::update);
        delete("/tournaments/:id",                      tournamentCtrl::delete);
        get   ("/tournaments/:id/participants",         tournamentCtrl::getParticipants);
        post  ("/tournaments/:id/register",             tournamentCtrl::register);
        delete("/tournaments/:id/register/:clientId",   tournamentCtrl::unregister);

        // ---- COMPRAS ----
        post("/purchases",     purchaseCtrl::purchase);
        get ("/purchases",     purchaseCtrl::getAllPurchases);
        get ("/purchases/:id", purchaseCtrl::getPurchaseById);

        // ---- HEALTH CHECK ----
        get("/health", (req, res) -> {
            res.type("application/json");
            return "{\"status\":\"UP\",\"app\":\"Cards4Play\"}";
        });
    }
}
