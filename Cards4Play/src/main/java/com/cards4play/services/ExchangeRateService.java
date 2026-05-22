package com.cards4play.services;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Consulta la tasa de cambio USD → COP usando la API abierta de exchangerate-api.com
 * Endpoint gratuito: https://open.er-api.com/v6/latest/USD
 */
public class ExchangeRateService {

    private static final String API_URL = "https://open.er-api.com/v6/latest/USD";
    private static double cachedRate = 0;
    private static long lastFetch = 0;
    private static final long CACHE_TTL_MS = 30 * 60 * 1000; // 30 minutos

    /**
     * Retorna la tasa USD → COP. Usa cache para no saturar la API.
     */
    public static double getUSDtoCOP() {
        long now = System.currentTimeMillis();
        if (cachedRate > 0 && (now - lastFetch) < CACHE_TTL_MS) {
            return cachedRate;
        }

        try {
            URL url = new URL(API_URL);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            conn.setConnectTimeout(5000);
            conn.setReadTimeout(5000);

            int status = conn.getResponseCode();
            if (status == 200) {
                BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                StringBuilder sb = new StringBuilder();
                String line;
                while ((line = in.readLine()) != null) sb.append(line);
                in.close();

                String json = sb.toString();
                // Parseo manual simple para no depender de Gson aquí
                // El JSON tiene: "rates":{"COP":4200.123,...}
                int ratesIdx = json.indexOf("\"rates\"");
                if (ratesIdx >= 0) {
                    int copIdx = json.indexOf("\"COP\"", ratesIdx);
                    if (copIdx >= 0) {
                        int colonIdx = json.indexOf(":", copIdx);
                        int commaIdx = json.indexOf(",", colonIdx);
                        int braceIdx = json.indexOf("}", colonIdx);
                        int end = Math.min(
                            commaIdx > 0 ? commaIdx : Integer.MAX_VALUE,
                            braceIdx > 0 ? braceIdx : Integer.MAX_VALUE
                        );
                        String rateStr = json.substring(colonIdx + 1, end).trim();
                        cachedRate = Double.parseDouble(rateStr);
                        lastFetch = now;
                        System.out.println("[ExchangeRate] Tasa obtenida: 1 USD = " + cachedRate + " COP");
                        return cachedRate;
                    }
                }
            }
        } catch (Exception e) {
            System.err.println("[ExchangeRate] Error consultando API: " + e.getMessage());
        }

        // Fallback si la API falla
        System.err.println("[ExchangeRate] Usando tasa de respaldo: 4100 COP");
        return 4100.0;
    }

    /**
     * Convierte un precio en USD a COP usando la tasa vigente.
     */
    public static double convertToCOP(double usd) {
        return Math.round(usd * getUSDtoCOP() * 100.0) / 100.0;
    }

    /** Para pruebas o forzar actualización del cache */
    public static void clearCache() {
        cachedRate = 0;
        lastFetch = 0;
    }
}
