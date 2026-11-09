package com.guali.tutor.android;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import org.json.JSONArray;
import org.json.JSONObject;
import java.util.ArrayList;

public class HistorialActivity extends AppCompatActivity {
    private TextView estado;
    private final ArrayList<String> elementos = new ArrayList<>();
    private ArrayAdapter<String> adaptador;
    @Override protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_historial);
        estado = findViewById(R.id.estadoHistorial);
        ListView lista = findViewById(R.id.listaHistorial);
        adaptador = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, elementos);
        lista.setAdapter(adaptador);
        findViewById(R.id.btnVolverHistorial).setOnClickListener(v -> finish());
        findViewById(R.id.btnActualizarHistorial).setOnClickListener(v -> cargar());
        cargar();
    }
    private void cargar() {
        estado.setText("Consultando historial...");
        new Thread(() -> {
            try {
                JSONArray datos = ApiClient.obtener("historiales");
                ArrayList<String> lista = new ArrayList<>();
                for (int i = 0; i < datos.length(); i++) {
                    JSONObject x = datos.getJSONObject(i);
                    lista.add("Lección ID: " + x.optString("leccionId", "—")
                        + "\nApertura: " + x.optString("fechaApertura", "—")
                        + "\nEstado: " + x.optString("estado", "PENDIENTE"));
                }
                runOnUiThread(() -> {
                    elementos.clear(); elementos.addAll(lista); adaptador.notifyDataSetChanged();
                    estado.setText(lista.isEmpty() ? "Sin intentos registrados" : lista.size() + " registro(s) persistidos en la API");
                });
            } catch (Exception ex) {
                runOnUiThread(() -> estado.setText("Error de conexión: " + ex.getMessage()));
            }
        }).start();
    }
}
