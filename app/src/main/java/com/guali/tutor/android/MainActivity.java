package com.guali.tutor.android;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {
    @Override protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ((Button)findViewById(R.id.btnLecciones)).setOnClickListener(v -> startActivity(new Intent(this, LeccionesActivity.class)));
        ((Button)findViewById(R.id.btnHistorial)).setOnClickListener(v -> startActivity(new Intent(this, HistorialActivity.class)));
        ((Button)findViewById(R.id.btnConfiguracion)).setOnClickListener(v -> startActivity(new Intent(this, ConfiguracionActivity.class)));
        ((Button)findViewById(R.id.btnAyuda)).setOnClickListener(v -> new androidx.appcompat.app.AlertDialog.Builder(this)
            .setTitle("Ayuda de Guali Tutor")
            .setMessage("1. Abre Mis lecciones.\n2. Selecciona una lección de la API REST.\n3. Consulta el contenido y escribe tu código en el editor.\n4. Revisa Historial y Configuración.\n\nLa compilación y ejecución requieren un servicio compatible con guali-dev.jar; no están disponibles en este frontend Android.")
            .setPositiveButton("Entendido", null).show());
    }
}
