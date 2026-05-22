package com.cards4play.controllers;

import com.cards4play.services.BoosterService;
import com.cards4play.util.AuthUtil;
import com.cards4play.util.ResponseUtil;
import spark.Request;
import spark.Response;

public class BoosterController {

    private final BoosterService boosterService;
    private final AuthUtil auth;

    public BoosterController(BoosterService boosterService, AuthUtil auth) {
        this.boosterService = boosterService;
        this.auth = auth;
    }

    // GET /clients/:id/boosters  - Lista boosters del cliente
    public Object getClientBoosters(Request req, Response res) {
        if (!auth.isAuthenticated(req)) return ResponseUtil.error(res, 401, "Autenticación requerida");
        try {
            String clientId = req.params(":id");
            if (auth.isClient(req) && !auth.getClient(req).getIdentification().equals(clientId))
                return ResponseUtil.error(res, 403, "No tienes permiso para ver estos boosters");
            return ResponseUtil.ok(res, boosterService.getClientBoosters(clientId));
        } catch (Exception e) { return ResponseUtil.error(res, 404, e.getMessage()); }
    }

    // POST /clients/:id/boosters/:boosterId/open  - Abrir un booster
    public Object openBooster(Request req, Response res) {
        if (!auth.isAuthenticated(req)) return ResponseUtil.error(res, 401, "Autenticación requerida");
        try {
            String clientId  = req.params(":id");
            String boosterId = req.params(":boosterId");
            if (auth.isClient(req) && !auth.getClient(req).getIdentification().equals(clientId))
                return ResponseUtil.error(res, 403, "Solo puedes abrir tus propios boosters");
            return ResponseUtil.ok(res, boosterService.openBooster(clientId, boosterId));
        } catch (IllegalStateException e) {
            return ResponseUtil.error(res, 409, e.getMessage());
        } catch (Exception e) {
            return ResponseUtil.error(res, 404, e.getMessage());
        }
    }
}
