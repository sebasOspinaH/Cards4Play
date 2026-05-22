package com.cards4play;

import com.cards4play.controllers.*;
import com.cards4play.models.*;
import com.cards4play.persistence.DataStore;
import com.cards4play.routes.Router;
import com.cards4play.services.*;
import com.cards4play.util.AuthUtil;

import static spark.Spark.*;

public class Main {

    public static void main(String[] args) {
        String portEnv = System.getenv("PORT");
        int port = (portEnv != null && !portEnv.isEmpty()) ? Integer.parseInt(portEnv) : 8080;
        port(port);

        AppState state = DataStore.load();
        if (state == null) {
            System.out.println("[Main] No se encontró datos previos. Inicializando estado por defecto...");
            state = new AppState();
            // Admin por defecto
            Admin admin = new Admin("ADMIN-001", "Administrador", "admin@cards4play.com", "admin123");
            state.setAdmin(admin);
            DataStore.save(state);
        } else {
            System.out.println("[Main] Estado cargado desde persistencia.");
        }

        // ── Servicios ───────────────────────────────────────────────────────
        UserService      userService      = new UserService(state);
        InventoryService inventoryService = new InventoryService(state);
        BoosterService   boosterService   = new BoosterService(state);
        TournamentService tournamentService = new TournamentService(state);
        PurchaseService  purchaseService  = new PurchaseService(state);

        // ── Auth helper ─────────────────────────────────────────────────────
        AuthUtil auth = new AuthUtil(userService);

        // ── Controllers ─────────────────────────────────────────────────────
        UserController       userCtrl       = new UserController(userService, auth);
        InventoryController  inventoryCtrl  = new InventoryController(inventoryService, auth);
        BoosterController    boosterCtrl    = new BoosterController(boosterService, auth);
        TournamentController tournamentCtrl = new TournamentController(tournamentService, auth);
        PurchaseController   purchaseCtrl   = new PurchaseController(purchaseService, auth);

        // ── Rutas ───────────────────────────────────────────────────────────
        Router.register(userCtrl, inventoryCtrl, boosterCtrl, tournamentCtrl, purchaseCtrl);

        // ── Manejo global de excepciones ────────────────────────────────────
        exception(Exception.class, (e, req, res) -> {
            res.type("application/json");
            res.status(500);
            res.body("{\"success\":false,\"error\":\"Error interno: " + e.getMessage() + "\"}");
        });

        System.out.println("[Main] Cards4Play API corriendo en http://localhost:" + port);
        System.out.println("[Main] Admin por defecto: admin@cards4play.com / admin123");
    }
}
