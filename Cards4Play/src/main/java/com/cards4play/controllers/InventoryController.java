package com.cards4play.controllers;

import com.cards4play.services.InventoryService;
import com.cards4play.util.AuthUtil;
import com.cards4play.util.ResponseUtil;
import com.google.gson.JsonObject;
import spark.Request;
import spark.Response;

public class InventoryController {

    private final InventoryService inventoryService;
    private final AuthUtil auth;

    public InventoryController(InventoryService inventoryService, AuthUtil auth) {
        this.inventoryService = inventoryService;
        this.auth = auth;
    }

    // =========== CARTAS ===========

    // GET /inventory/cards  (todos)
    public Object getAllCards(Request req, Response res) {
        if (!auth.isAuthenticated(req)) return ResponseUtil.error(res, 401, "Autenticación requerida");
        return ResponseUtil.ok(res, inventoryService.getAllCards());
    }

    // GET /inventory/cards/:id
    public Object getCard(Request req, Response res) {
        if (!auth.isAuthenticated(req)) return ResponseUtil.error(res, 401, "Autenticación requerida");
        try { return ResponseUtil.ok(res, inventoryService.getCardById(req.params(":id"))); }
        catch (Exception e) { return ResponseUtil.error(res, 404, e.getMessage()); }
    }

    // POST /inventory/cards  (ADMIN)
    public Object addCard(Request req, Response res) {
        if (!auth.isAdmin(req)) return ResponseUtil.error(res, 403, "Solo el administrador puede agregar cartas");
        try {
            JsonObject b = ResponseUtil.parseBody(req, JsonObject.class);
            return ResponseUtil.created(res, inventoryService.addCard(
                b.get("identification").getAsString(),
                b.get("name").getAsString(),
                b.get("priceUSD").getAsDouble(),
                b.get("rarity").getAsString()
            ));
        } catch (Exception e) { return ResponseUtil.error(res, 400, e.getMessage()); }
    }

    // PUT /inventory/cards/:id  (ADMIN)
    public Object updateCard(Request req, Response res) {
        if (!auth.isAdmin(req)) return ResponseUtil.error(res, 403, "Solo el administrador puede actualizar cartas");
        try {
            JsonObject b = ResponseUtil.parseBody(req, JsonObject.class);
            return ResponseUtil.ok(res, inventoryService.updateCard(
                req.params(":id"),
                b.has("name")     ? b.get("name").getAsString()     : null,
                b.has("priceUSD") ? b.get("priceUSD").getAsDouble() : null,
                b.has("rarity")   ? b.get("rarity").getAsString()   : null
            ));
        } catch (Exception e) { return ResponseUtil.error(res, 400, e.getMessage()); }
    }

    // DELETE /inventory/cards/:id  (ADMIN)
    public Object deleteCard(Request req, Response res) {
        if (!auth.isAdmin(req)) return ResponseUtil.error(res, 403, "Solo el administrador puede eliminar cartas");
        try {
            inventoryService.deleteCard(req.params(":id"));
            JsonObject msg = new JsonObject(); msg.addProperty("message", "Carta eliminada");
            return ResponseUtil.ok(res, msg);
        } catch (Exception e) { return ResponseUtil.error(res, 404, e.getMessage()); }
    }

    // =========== BOOSTERS ===========

    // GET /inventory/boosters
    public Object getAllBoosters(Request req, Response res) {
        if (!auth.isAuthenticated(req)) return ResponseUtil.error(res, 401, "Autenticación requerida");
        return ResponseUtil.ok(res, inventoryService.getAllBoosters());
    }

    // GET /inventory/boosters/:id
    public Object getBooster(Request req, Response res) {
        if (!auth.isAuthenticated(req)) return ResponseUtil.error(res, 401, "Autenticación requerida");
        try { return ResponseUtil.ok(res, inventoryService.getBoosterById(req.params(":id"))); }
        catch (Exception e) { return ResponseUtil.error(res, 404, e.getMessage()); }
    }

    // POST /inventory/boosters  (ADMIN)
    public Object addBooster(Request req, Response res) {
        if (!auth.isAdmin(req)) return ResponseUtil.error(res, 403, "Solo el administrador puede crear boosters");
        try {
            JsonObject b = ResponseUtil.parseBody(req, JsonObject.class);
            return ResponseUtil.created(res, inventoryService.addBooster(
                b.get("identification").getAsString(),
                b.get("name").getAsString(),
                b.get("priceUSD").getAsDouble()
            ));
        } catch (Exception e) { return ResponseUtil.error(res, 400, e.getMessage()); }
    }

    // DELETE /inventory/boosters/:id  (ADMIN)
    public Object deleteBooster(Request req, Response res) {
        if (!auth.isAdmin(req)) return ResponseUtil.error(res, 403, "Solo el administrador puede eliminar boosters");
        try {
            inventoryService.deleteBooster(req.params(":id"));
            JsonObject msg = new JsonObject(); msg.addProperty("message", "Booster eliminado");
            return ResponseUtil.ok(res, msg);
        } catch (Exception e) { return ResponseUtil.error(res, 404, e.getMessage()); }
    }

    // =========== PRODUCTOS SELLADOS ===========

    // GET /inventory/sealed
    public Object getAllSealed(Request req, Response res) {
        if (!auth.isAuthenticated(req)) return ResponseUtil.error(res, 401, "Autenticación requerida");
        return ResponseUtil.ok(res, inventoryService.getAllSealed());
    }

    // POST /inventory/sealed  (ADMIN)
    public Object addSealed(Request req, Response res) {
        if (!auth.isAdmin(req)) return ResponseUtil.error(res, 403, "Solo el administrador puede agregar productos sellados");
        try {
            JsonObject b = ResponseUtil.parseBody(req, JsonObject.class);
            return ResponseUtil.created(res, inventoryService.addSealed(
                b.get("identification").getAsString(),
                b.get("name").getAsString(),
                b.get("priceUSD").getAsDouble(),
                b.has("edition") ? b.get("edition").getAsString() : "N/A"
            ));
        } catch (Exception e) { return ResponseUtil.error(res, 400, e.getMessage()); }
    }

    // DELETE /inventory/sealed/:id  (ADMIN)
    public Object deleteSealed(Request req, Response res) {
        if (!auth.isAdmin(req)) return ResponseUtil.error(res, 403, "Solo el administrador puede eliminar productos sellados");
        try {
            inventoryService.deleteSealed(req.params(":id"));
            JsonObject msg = new JsonObject(); msg.addProperty("message", "Producto sellado eliminado");
            return ResponseUtil.ok(res, msg);
        } catch (Exception e) { return ResponseUtil.error(res, 404, e.getMessage()); }
    }

    // =========== ACCESORIOS ===========

    // GET /inventory/accessories
    public Object getAllAccessories(Request req, Response res) {
        if (!auth.isAuthenticated(req)) return ResponseUtil.error(res, 401, "Autenticación requerida");
        return ResponseUtil.ok(res, inventoryService.getAllAccessories());
    }

    // POST /inventory/accessories  (ADMIN)
    public Object addAccessory(Request req, Response res) {
        if (!auth.isAdmin(req)) return ResponseUtil.error(res, 403, "Solo el administrador puede agregar accesorios");
        try {
            JsonObject b = ResponseUtil.parseBody(req, JsonObject.class);
            return ResponseUtil.created(res, inventoryService.addAccessory(
                b.get("identification").getAsString(),
                b.get("name").getAsString(),
                b.get("priceUSD").getAsDouble(),
                b.has("accessoryType") ? b.get("accessoryType").getAsString() : "OTHER"
            ));
        } catch (Exception e) { return ResponseUtil.error(res, 400, e.getMessage()); }
    }

    // DELETE /inventory/accessories/:id  (ADMIN)
    public Object deleteAccessory(Request req, Response res) {
        if (!auth.isAdmin(req)) return ResponseUtil.error(res, 403, "Solo el administrador puede eliminar accesorios");
        try {
            inventoryService.deleteAccessory(req.params(":id"));
            JsonObject msg = new JsonObject(); msg.addProperty("message", "Accesorio eliminado");
            return ResponseUtil.ok(res, msg);
        } catch (Exception e) { return ResponseUtil.error(res, 404, e.getMessage()); }
    }

    // =========== INVENTARIO CLIENTE ===========

    // GET /clients/:id/inventory
    public Object getClientInventory(Request req, Response res) {
        if (!auth.isAuthenticated(req)) return ResponseUtil.error(res, 401, "Autenticación requerida");
        try {
            String clientId = req.params(":id");
            if (auth.isClient(req) && !auth.getClient(req).getIdentification().equals(clientId))
                return ResponseUtil.error(res, 403, "No tienes permiso para ver este inventario");
            return ResponseUtil.ok(res, inventoryService.getClientInventory(clientId));
        } catch (Exception e) { return ResponseUtil.error(res, 404, e.getMessage()); }
    }
}
