package com.guali.tutor.android;

import org.json.JSONArray;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;

public final class ApiClient {
    private static final String BASE_URL = "http://127.0.0.1:8080/api/";
    private ApiClient() { }
    public static JSONArray obtener(String recurso) throws Exception {
        HttpURLConnection c = (HttpURLConnection) new URL(BASE_URL + recurso).openConnection();
        try {
            c.setRequestMethod("GET");
            c.setConnectTimeout(20000);
            c.setReadTimeout(20000);
            int estado = c.getResponseCode();
            if (estado != 200) throw new Exception("HTTP " + estado);
            StringBuilder contenido = new StringBuilder();
            try (BufferedReader lector = new BufferedReader(new InputStreamReader(c.getInputStream(), StandardCharsets.UTF_8))) {
                String linea;
                while ((linea = lector.readLine()) != null) contenido.append(linea);
            }
            return new JSONArray(contenido.toString());
        } finally { c.disconnect(); }
    }
}
