package com.cards4play.persistence;

import com.cards4play.models.*;
import com.google.gson.*;
import java.lang.reflect.Type;

/**
 * Configura Gson con adaptadores para manejar la jerarquía polimórfica de Product y User.
 */
public class GsonConfig {

    public static Gson build() {
        return new GsonBuilder()
                .registerTypeAdapter(Product.class, new ProductAdapter())
                .registerTypeAdapter(User.class, new UserAdapter())
                .setPrettyPrinting()
                .create();
    }

    // --- Product adapter ---
    static class ProductAdapter implements JsonDeserializer<Product>, JsonSerializer<Product> {

        @Override
        public JsonElement serialize(Product src, Type typeOfSrc, JsonSerializationContext ctx) {
            JsonObject obj = ctx.serialize(src, src.getClass()).getAsJsonObject();
            obj.addProperty("type", src.getType());
            return obj;
        }

        @Override
        public Product deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext ctx)
                throws JsonParseException {
            JsonObject obj = json.getAsJsonObject();
            String type = obj.has("type") ? obj.get("type").getAsString() : "CARD";
            switch (type) {
                case "CARD":     return ctx.deserialize(json, Card.class);
                case "BOOSTER":  return ctx.deserialize(json, Booster.class);
                case "SEALED":   return ctx.deserialize(json, SealedProduct.class);
                case "ACCESSORY": return ctx.deserialize(json, Accessory.class);
                default: throw new JsonParseException("Tipo de producto desconocido: " + type);
            }
        }
    }

    // --- User adapter ---
    static class UserAdapter implements JsonDeserializer<User>, JsonSerializer<User> {

        @Override
        public JsonElement serialize(User src, Type typeOfSrc, JsonSerializationContext ctx) {
            JsonObject obj = ctx.serialize(src, src.getClass()).getAsJsonObject();
            obj.addProperty("role", src.getRole());
            return obj;
        }

        @Override
        public User deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext ctx)
                throws JsonParseException {
            JsonObject obj = json.getAsJsonObject();
            String role = obj.has("role") ? obj.get("role").getAsString() : "CLIENT";
            if ("ADMIN".equals(role)) return ctx.deserialize(json, Admin.class);
            return ctx.deserialize(json, Client.class);
        }
    }
}
