package com.cards4play.util;

import com.cards4play.models.*;
import com.cards4play.services.UserService;
import spark.Request;

/**
 * Autenticación básica via cabecera "X-User-Email" y "X-User-Password".
 * Diseñado para pruebas con Postman/curl sin requerir JWT.
 */
public class AuthUtil {

    private final UserService userService;

    public AuthUtil(UserService userService) {
        this.userService = userService;
    }

    public User authenticate(Request req) {
        String email = req.headers("X-User-Email");
        String password = req.headers("X-User-Password");
        if (email == null || password == null) return null;
        return userService.login(email, password);
    }

    public boolean isAdmin(Request req) {
        User u = authenticate(req);
        return u != null && "ADMIN".equals(u.getRole());
    }

    public boolean isClient(Request req) {
        User u = authenticate(req);
        return u != null && "CLIENT".equals(u.getRole());
    }

    public boolean isAuthenticated(Request req) {
        return authenticate(req) != null;
    }

    public Client getClient(Request req) {
        User u = authenticate(req);
        if (u instanceof Client) return (Client) u;
        return null;
    }
}
