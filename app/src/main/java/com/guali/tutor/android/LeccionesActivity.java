package com.guali.tutor.android;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import org.json.JSONArray;
import org.json.JSONObject;
import java.util.ArrayList;

public class LeccionesActivity extends AppCompatActivity {
    private final ArrayList<String> titulos = new ArrayList<>();
    private final ArrayList<JSONObject> registros = new ArrayList<>();
    private ArrayAdapter<String> adaptador;
    private TextView estado;
    @Override protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lecciones);
        estado = findViewById(R.id.estadoLecciones);
        ListView lista = findViewById(R.id.listaLecciones);
        adaptador = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, titulos);
        lista.setAdapter(adaptador);
        lista.setOnItemClickListener((padre, vista, posicion, id) -> {
            if (posicion >= registros.size()) return;
            JSONObject item = registros.get(posicion);
            Intent intent = new Intent(this, EditorActivity.class);
            intent.putExtra("id", item.optInt("id", -1));
            intent.putExtra("nombre", item.optString("nombre", "Lección"));
            intent.putExtra("objetivo", item.optString("objetivo", ""));
            intent.putExtra("ruta", item.optString("ruta", ""));
            startActivity(intent);
        });
        findViewById(R.id.btnVolver).setOnClickListener(v -> finish());
        findViewById(R.id.btnActualizarLecciones).setOnClickListener(v -> cargar());
        cargar();
    }
    private void cargar() {
        estado.setText("Consultando lecciones...");
        new Thread(() -> {
            try {
                JSONArray datos = ApiClient.obtener("lecciones");
                ArrayList<String> nombres = new ArrayList<>();
                ArrayList<JSONObject> objetos = new ArrayList<>();
                for (int i = 0; i < datos.length(); i++) {
                    JSONObject item = datos.getJSONObject(i);
                    objetos.add(item);
                    nombres.add(item.optString("nombre", "Sin nombre") + "\nObjetivo: " + item.optString("objetivo", "No indicado"));
                }
                runOnUiThread(() -> {
                    registros.clear(); registros.addAll(objetos);
                    titulos.clear(); titulos.addAll(nombres);
                    adaptador.notifyDataSetChanged();
                    estado.setText(objetos.isEmpty() ? "No hay lecciones registradas" : objetos.size() + " lección(es). Toca una para abrirla.");
                });
            } catch (Exception ex) {
                runOnUiThread(() -> estado.setText("No se pudo conectar: " + ex.getMessage() + "\nVerifica Spring Boot y el puerto 8080."));
            }
        }).start();
    }
}
