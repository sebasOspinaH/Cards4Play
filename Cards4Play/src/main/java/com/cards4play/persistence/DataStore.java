package com.cards4play.persistence;

import com.cards4play.models.*;
import com.google.gson.Gson;

import java.io.*;
import java.nio.file.*;

public class DataStore {

    private static final String DATA_PATH;

    static {
        String env = System.getenv("DATA_PATH");
        DATA_PATH = (env != null && !env.isEmpty()) ? env : "data/state.json";
    }

    private static final Gson GSON = GsonConfig.build();

    public static void save(AppState state) {
        try {
            Path path = Paths.get(DATA_PATH);
            Files.createDirectories(path.getParent());
            String json = GSON.toJson(state);
            Files.writeString(path, json);
        } catch (IOException e) {
            System.err.println("[DataStore] Error guardando: " + e.getMessage());
        }
    }

    public static AppState load() {
        try {
            Path path = Paths.get(DATA_PATH);
            if (!Files.exists(path)) return null;
            String json = Files.readString(path);
            return GSON.fromJson(json, AppState.class);
        } catch (Exception e) {
            System.err.println("[DataStore] Error cargando: " + e.getMessage());
            return null;
        }
    }
}
