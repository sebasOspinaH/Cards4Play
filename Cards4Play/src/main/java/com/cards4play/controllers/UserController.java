package com.cards4play.controllers;

import com.cards4play.models.User;
import com.cards4play.services.UserService;
import com.cards4play.util.AuthUtil;
import com.cards4play.util.ResponseUtil;
import com.google.gson.JsonObject;
import spark.Request;
import spark.Response;

public class UserController {

    private final UserService userService;
    private final AuthUtil auth;

    public UserController(UserService userService, AuthUtil auth) {
        this.userService = userService;
        this.auth = auth;
    }

    // POST /auth/login
    public Object login(Request req, Response res) {
        try {
            JsonObject body = ResponseUtil.parseBody(req, JsonObject.class);
            String email = body.get("email").getAsString();
            String password = body.get("password").getAsString();
            User user = userService.login(email, password);
            if (user == null) return ResponseUtil.error(res, 401, "Credenciales inválidas");
            return ResponseUtil.ok(res, user);
        } catch (Exception e) {
            return ResponseUtil.error(res, 400, e.getMessage());
        }
    }

    // GET /clients  (ADMIN)
    public Object getAllClients(Request req, Response res) {
        if (!auth.isAdmin(req)) return ResponseUtil.error(res, 403, "Solo el administrador puede ver todos los clientes");
        return ResponseUtil.ok(res, userService.getAllClients());
    }

    // POST /clients  (ADMIN)
    public Object registerClient(Request req, Response res) {
        if (!auth.isAdmin(req)) return ResponseUtil.error(res, 403, "Solo el administrador puede registrar clientes");
        try {
            JsonObject body = ResponseUtil.parseBody(req, JsonObject.class);
            String id       = body.get("identification").getAsString();
            String name     = body.get("name").getAsString();
            String email    = body.get("email").getAsString();
            String password = body.get("password").getAsString();
            return ResponseUtil.created(res, userService.registerClient(id, name, email, password));
        } catch (Exception e) {
            return ResponseUtil.error(res, 400, e.getMessage());
        }
    }

    // GET /clients/:id  (ADMIN o el propio cliente)
    public Object getClientById(Request req, Response res) {
        if (!auth.isAuthenticated(req)) return ResponseUtil.error(res, 401, "Autenticación requerida");
        try {
            String id = req.params(":id");
            // Un cliente solo puede ver su propio perfil
            if (auth.isClient(req)) {
                var client = auth.getClient(req);
                if (!client.getIdentification().equals(id))
                    return ResponseUtil.error(res, 403, "No tienes permiso para ver este perfil");
            }
            return ResponseUtil.ok(res, userService.getClientById(id));
        } catch (Exception e) {
            return ResponseUtil.error(res, 404, e.getMessage());
        }
    }

    // PUT /clients/:id  (ADMIN)
    public Object updateClient(Request req, Response res) {
        if (!auth.isAdmin(req)) return ResponseUtil.error(res, 403, "Solo el administrador puede actualizar clientes");
        try {
            String id = req.params(":id");
            JsonObject body = ResponseUtil.parseBody(req, JsonObject.class);
            String name  = body.has("name")  ? body.get("name").getAsString()  : null;
            String email = body.has("email") ? body.get("email").getAsString() : null;
            return ResponseUtil.ok(res, userService.updateClient(id, name, email));
        } catch (Exception e) {
            return ResponseUtil.error(res, 400, e.getMessage());
        }
    }

    // DELETE /clients/:id  (ADMIN)
    public Object deleteClient(Request req, Response res) {
        if (!auth.isAdmin(req)) return ResponseUtil.error(res, 403, "Solo el administrador puede eliminar clientes");
        try {
            userService.deleteClient(req.params(":id"));
            JsonObject msg = new JsonObject();
            msg.addProperty("message", "Cliente eliminado correctamente");
            return ResponseUtil.ok(res, msg);
        } catch (Exception e) {
            return ResponseUtil.error(res, 404, e.getMessage());
        }
    }
}
