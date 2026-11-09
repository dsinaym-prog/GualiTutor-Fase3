package com.guali.tutor.android;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import org.json.JSONArray;
import org.json.JSONObject;
import java.util.ArrayList;

public class ConfiguracionActivity extends AppCompatActivity {
    private TextView estado;
    private final ArrayList<String> elementos = new ArrayList<>();
    private ArrayAdapter<String> adaptador;
    @Override protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_configuracion);
        estado = findViewById(R.id.estadoConfiguracion);
        ListView lista = findViewById(R.id.listaConfiguracion);
        adaptador = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, elementos);
        lista.setAdapter(adaptador);
        findViewById(R.id.btnVolverConfiguracion).setOnClickListener(v -> finish());
        findViewById(R.id.btnActualizarConfiguracion).setOnClickListener(v -> cargar());
        cargar();
    }
    private void cargar() {
        estado.setText("Consultando configuración...");
        new Thread(() -> {
            try {
                JSONArray datos = ApiClient.obtener("configuraciones");
                ArrayList<String> lista = new ArrayList<>();
                for (int i = 0; i < datos.length(); i++) {
                    JSONObject x = datos.getJSONObject(i);
                    lista.add(x.optString("clave", "Sin clave") + "\n" + x.optString("valor", ""));
                }
                runOnUiThread(() -> {
                    elementos.clear(); elementos.addAll(lista); adaptador.notifyDataSetChanged();
                    estado.setText(lista.isEmpty() ? "Sin configuraciones registradas" : lista.size() + " configuración(es) en la API");
                });
            } catch (Exception ex) {
                runOnUiThread(() -> estado.setText("Error de conexión: " + ex.getMessage()));
            }
        }).start();
    }
}
