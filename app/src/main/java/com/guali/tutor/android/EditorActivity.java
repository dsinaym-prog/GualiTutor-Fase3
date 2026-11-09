
package com.guali.tutor.android;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import org.json.JSONObject;

import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;

public class EditorActivity extends AppCompatActivity {

    private EditText editor;
    private TextView consola;
    private Button btnCompilar;
    private Button btnEjecutar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editor);

        String nombre = getIntent().getStringExtra("nombre");
        String objetivo = getIntent().getStringExtra("objetivo");
        String ruta = getIntent().getStringExtra("ruta");

        ((TextView) findViewById(R.id.tituloLeccion))
                .setText(nombre == null ? "Lección" : nombre);

        ((TextView) findViewById(R.id.detalleLeccion))
                .setText("Objetivo: " + objetivo
                        + "\nRuta de origen: " + ruta
                        + "\n\nEl archivo leccion.md está en la ruta registrada por el servidor.");

        editor = findViewById(R.id.editorCodigo);
        consola = findViewById(R.id.consola);
        btnCompilar = findViewById(R.id.btnCompilar);
        btnEjecutar = findViewById(R.id.btnEjecutar);

        editor.setText("public class Main {\n"
                + "    public static void main(String[] args) {\n"
                + "        System.out.println(\"Hola mundo\");\n"
                + "    }\n"
                + "}");

        consola.setText("Consola lista.");
        btnEjecutar.setEnabled(false);

        editor.addTextChangedListener(new android.text.TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                btnEjecutar.setEnabled(false);
            }

            @Override
            public void afterTextChanged(android.text.Editable s) {}
        });

        btnCompilar.setOnClickListener(v -> compilarCodigo());

        btnEjecutar.setOnClickListener(v ->
                consola.setText("La ejecución todavía no está implementada. "
                        + "Se necesita un entorno aislado en el servidor."));

        findViewById(R.id.btnCerrarLeccion)
                .setOnClickListener(v -> finish());
    }

    private void compilarCodigo() {
        String codigo = editor.getText().toString();

        btnCompilar.setEnabled(false);
        btnEjecutar.setEnabled(false);
        consola.setText("Compilando código Java...");

        new Thread(() -> {
            HttpURLConnection conexion = null;

            try {
                URL url = new URL(
                        "http://127.0.0.1:8080/api/compilacion/compilar"
                );

                conexion = (HttpURLConnection) url.openConnection();
                conexion.setRequestMethod("POST");
                conexion.setRequestProperty(
                        "Content-Type", "application/json; charset=UTF-8");
                conexion.setConnectTimeout(10000);
                conexion.setReadTimeout(20000);
                conexion.setDoOutput(true);

                JSONObject solicitud = new JSONObject();
                solicitud.put("codigo", codigo);

                try (OutputStream salida = conexion.getOutputStream()) {
                    salida.write(solicitud.toString()
                            .getBytes(StandardCharsets.UTF_8));
                }

                int estado = conexion.getResponseCode();

                java.io.InputStream entrada = estado >= 200 && estado < 300
                        ? conexion.getInputStream()
                        : conexion.getErrorStream();

                String respuesta;

                try (java.io.InputStream flujo = entrada;
                     java.io.ByteArrayOutputStream buffer =
                             new java.io.ByteArrayOutputStream()) {

                    byte[] datos = new byte[4096];
                    int cantidad;

                    while ((cantidad = flujo.read(datos)) != -1) {
                        buffer.write(datos, 0, cantidad);
                    }

                    respuesta = buffer.toString("UTF-8");
                }

                JSONObject resultado = new JSONObject(respuesta);

                boolean exito = resultado.optBoolean("exito", false);
                String mensaje = resultado.optString("mensaje", "");
                String errores = resultado.optString("errores", "");

                runOnUiThread(() -> {
                    consola.setText(mensaje
                            + (errores.isEmpty() ? "" : "\n\n" + errores));

                    // La compilación funciona, pero aún no hay
                    // un servicio seguro para ejecutar el programa.
                    btnEjecutar.setEnabled(false);
                    btnCompilar.setEnabled(true);
                });

            } catch (Exception e) {
                runOnUiThread(() -> {
                    consola.setText("Error de conexión: " + e.getMessage());
                    btnCompilar.setEnabled(true);
                    btnEjecutar.setEnabled(false);
                });
            } finally {
                if (conexion != null) {
                    conexion.disconnect();
                }
            }
        }).start();
    }
}
