package com.cards4play.controllers;

import com.cards4play.services.PurchaseService;
import com.cards4play.util.AuthUtil;
import com.cards4play.util.ResponseUtil;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import spark.Request;
import spark.Response;

import java.util.ArrayList;
import java.util.List;

public class PurchaseController {

    private final PurchaseService purchaseService;
    private final AuthUtil auth;

    public PurchaseController(PurchaseService purchaseService, AuthUtil auth) {
        this.purchaseService = purchaseService;
        this.auth = auth;
    }

    // POST /purchases  - Realizar una compra (CLIENT)
    // Body: { "clientId": "...", "productIds": ["id1","id2"] }
    public Object purchase(Request req, Response res) {
        if (!auth.isAuthenticated(req)) return ResponseUtil.error(res, 401, "Autenticación requerida");
        try {
            JsonObject body = ResponseUtil.parseBody(req, JsonObject.class);
            String clientId;
            if (auth.isAdmin(req)) {
                clientId = body.get("clientId").getAsString();
            } else {
                clientId = auth.getClient(req).getIdentification();
            }
            JsonArray arr = body.getAsJsonArray("productIds");
            List<String> productIds = new ArrayList<>();
            arr.forEach(e -> productIds.add(e.getAsString()));
            return ResponseUtil.created(res, purchaseService.purchase(clientId, productIds));
        } catch (Exception e) { return ResponseUtil.error(res, 400, e.getMessage()); }
    }

    // GET /purchases  - Todas las compras (ADMIN)
    public Object getAllPurchases(Request req, Response res) {
        if (!auth.isAdmin(req)) return ResponseUtil.error(res, 403, "Solo el administrador puede ver todas las compras");
        return ResponseUtil.ok(res, purchaseService.getAllPurchases());
    }

    // GET /purchases/:id  - Detalle de una compra (ADMIN o cliente propietario)
    public Object getPurchaseById(Request req, Response res) {
        if (!auth.isAuthenticated(req)) return ResponseUtil.error(res, 401, "Autenticación requerida");
        try {
            var purchase = purchaseService.getPurchaseById(req.params(":id"));
            if (auth.isClient(req) && !auth.getClient(req).getIdentification().equals(purchase.getClientId()))
                return ResponseUtil.error(res, 403, "No tienes permiso para ver esta compra");
            return ResponseUtil.ok(res, purchase);
        } catch (Exception e) { return ResponseUtil.error(res, 404, e.getMessage()); }
    }

    // GET /clients/:id/purchases  - Historial de compras de un cliente
    public Object getClientHistory(Request req, Response res) {
        if (!auth.isAuthenticated(req)) return ResponseUtil.error(res, 401, "Autenticación requerida");
        try {
            String clientId = req.params(":id");
            if (auth.isClient(req) && !auth.getClient(req).getIdentification().equals(clientId))
                return ResponseUtil.error(res, 403, "No tienes permiso para ver este historial");
            return ResponseUtil.ok(res, purchaseService.getClientHistory(clientId));
        } catch (Exception e) { return ResponseUtil.error(res, 404, e.getMessage()); }
    }
}
