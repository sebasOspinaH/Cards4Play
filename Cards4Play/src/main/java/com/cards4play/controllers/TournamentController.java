package com.cards4play.controllers;

import com.cards4play.services.TournamentService;
import com.cards4play.util.AuthUtil;
import com.cards4play.util.ResponseUtil;
import com.google.gson.JsonObject;
import spark.Request;
import spark.Response;

public class TournamentController {

    private final TournamentService tournamentService;
    private final AuthUtil auth;

    public TournamentController(TournamentService tournamentService, AuthUtil auth) {
        this.tournamentService = tournamentService;
        this.auth = auth;
    }

    // GET /tournaments
    public Object getAll(Request req, Response res) {
        if (!auth.isAuthenticated(req)) return ResponseUtil.error(res, 401, "Autenticación requerida");
        return ResponseUtil.ok(res, tournamentService.getAllTournaments());
    }

    // GET /tournaments/:id
    public Object getById(Request req, Response res) {
        if (!auth.isAuthenticated(req)) return ResponseUtil.error(res, 401, "Autenticación requerida");
        try { return ResponseUtil.ok(res, tournamentService.getTournamentById(req.params(":id"))); }
        catch (Exception e) { return ResponseUtil.error(res, 404, e.getMessage()); }
    }

    // POST /tournaments  (ADMIN)
    public Object create(Request req, Response res) {
        if (!auth.isAdmin(req)) return ResponseUtil.error(res, 403, "Solo el administrador puede crear torneos");
        try {
            JsonObject b = ResponseUtil.parseBody(req, JsonObject.class);
            return ResponseUtil.created(res, tournamentService.createTournament(
                b.get("id").getAsString(),
                b.get("name").getAsString(),
                b.get("date").getAsString(),
                b.get("capacity").getAsInt()
            ));
        } catch (Exception e) { return ResponseUtil.error(res, 400, e.getMessage()); }
    }

    // PUT /tournaments/:id  (ADMIN)
    public Object update(Request req, Response res) {
        if (!auth.isAdmin(req)) return ResponseUtil.error(res, 403, "Solo el administrador puede actualizar torneos");
        try {
            JsonObject b = ResponseUtil.parseBody(req, JsonObject.class);
            return ResponseUtil.ok(res, tournamentService.updateTournament(
                req.params(":id"),
                b.has("name")     ? b.get("name").getAsString()     : null,
                b.has("date")     ? b.get("date").getAsString()     : null,
                b.has("capacity") ? b.get("capacity").getAsInt()    : null
            ));
        } catch (Exception e) { return ResponseUtil.error(res, 400, e.getMessage()); }
    }

    // DELETE /tournaments/:id  (ADMIN)
    public Object delete(Request req, Response res) {
        if (!auth.isAdmin(req)) return ResponseUtil.error(res, 403, "Solo el administrador puede eliminar torneos");
        try {
            tournamentService.deleteTournament(req.params(":id"));
            JsonObject msg = new JsonObject(); msg.addProperty("message", "Torneo eliminado");
            return ResponseUtil.ok(res, msg);
        } catch (Exception e) { return ResponseUtil.error(res, 404, e.getMessage()); }
    }

    // GET /tournaments/:id/participants
    public Object getParticipants(Request req, Response res) {
        if (!auth.isAuthenticated(req)) return ResponseUtil.error(res, 401, "Autenticación requerida");
        try { return ResponseUtil.ok(res, tournamentService.getTournamentParticipants(req.params(":id"))); }
        catch (Exception e) { return ResponseUtil.error(res, 404, e.getMessage()); }
    }

    // POST /tournaments/:id/register  (CLIENT o ADMIN)
    public Object register(Request req, Response res) {
        if (!auth.isAuthenticated(req)) return ResponseUtil.error(res, 401, "Autenticación requerida");
        try {
            String tournamentId = req.params(":id");
            String clientId;
            if (auth.isAdmin(req)) {
                JsonObject b = ResponseUtil.parseBody(req, JsonObject.class);
                clientId = b.get("clientId").getAsString();
            } else {
                clientId = auth.getClient(req).getIdentification();
            }
            return ResponseUtil.created(res, tournamentService.registerClient(tournamentId, clientId));
        } catch (IllegalStateException e) {
            return ResponseUtil.error(res, 409, e.getMessage());
        } catch (Exception e) {
            return ResponseUtil.error(res, 400, e.getMessage());
        }
    }

    // DELETE /tournaments/:id/register/:clientId  (ADMIN)
    public Object unregister(Request req, Response res) {
        if (!auth.isAdmin(req)) return ResponseUtil.error(res, 403, "Solo el administrador puede desinscribir clientes");
        try {
            tournamentService.unregisterClient(req.params(":id"), req.params(":clientId"));
            JsonObject msg = new JsonObject(); msg.addProperty("message", "Cliente desinscrito del torneo");
            return ResponseUtil.ok(res, msg);
        } catch (Exception e) { return ResponseUtil.error(res, 400, e.getMessage()); }
    }
}
