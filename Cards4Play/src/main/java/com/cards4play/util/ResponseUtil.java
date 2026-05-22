package com.cards4play.util;

import com.google.gson.*;
import spark.Response;

public class ResponseUtil {

    private static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();

    public static String ok(Response res, Object data) {
        res.type("application/json");
        res.status(200);
        JsonObject obj = new JsonObject();
        obj.addProperty("success", true);
        obj.add("data", GSON.toJsonTree(data));
        return GSON.toJson(obj);
    }

    public static String created(Response res, Object data) {
        res.type("application/json");
        res.status(201);
        JsonObject obj = new JsonObject();
        obj.addProperty("success", true);
        obj.add("data", GSON.toJsonTree(data));
        return GSON.toJson(obj);
    }

    public static String error(Response res, int status, String message) {
        res.type("application/json");
        res.status(status);
        JsonObject obj = new JsonObject();
        obj.addProperty("success", false);
        obj.addProperty("error", message);
        return GSON.toJson(obj);
    }

    public static <T> T parseBody(spark.Request req, Class<T> clazz) {
        return GSON.fromJson(req.body(), clazz);
    }
}
