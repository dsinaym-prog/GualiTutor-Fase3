# Tutorial de Guali Tutor Fase 3: frontend Android

**Universidad Mariano Gálvez de Guatemala — Programación 2**  
**Proyecto:** Guali Tutor Fase 3  
**Carné:** 3190-24-18636

## 1. Objetivo
Crear un frontend Android con Java y vistas XML para consultar la API REST de Guali Tutor y presentar las pantallas de lecciones, historial, configuración, ayuda y una interfaz de lección con editor de código y consola.

## 2. Prerrequisitos
- Windows 10/11, conexión a Internet para la instalación.
- Android Studio con Android SDK API 37 y Android SDK Platform-Tools.
- JDK compatible con la versión de Android Studio y Gradle del proyecto.
- Emulador Android configurado (por ejemplo Medium Phone API 37).
- API REST de Guali Tutor Fase 2 funcionando con Spring Boot en el puerto 8080.
- MariaDB con la base `guali_tutor` disponible para el backend.
- Git y cuenta de GitHub para publicar este tutorial.

## 3. Instalación
1. Descarga Android Studio desde https://developer.android.com/studio.
2. Ejecuta el instalador y acepta Android SDK y Android Virtual Device.
3. En Android Studio, abre **Settings > Languages & Frameworks > Android SDK** e instala la plataforma API 37 y Platform-Tools.
4. Abre **Device Manager**, crea un teléfono virtual y descarga la imagen de Android.
5. Descomprime el ZIP del proyecto y abre la carpeta `GualiTutorFase3` en Android Studio.
6. Espera a que termine **Gradle Sync**. Selecciona el emulador y ejecuta **Run app**.

## 4. Creación del frontend paso a paso
1. En **New Project**, selecciona **Empty Views Activity**, lenguaje **Java**, nombre `GualiTutorFase3`, paquete `com.guali.tutor.android` y SDK mínimo API 24.
2. En `res/layout/activity_main.xml`, crea el título y los botones Mis lecciones, Historial, Configuración y Ayuda.
3. En `MainActivity.java`, enlaza cada botón con `findViewById` y usa `Intent` para abrir la pantalla correspondiente.
4. En `AndroidManifest.xml`, declara el permiso `INTERNET`, habilita tráfico HTTP local de desarrollo y registra las actividades.
5. En `ApiClient.java`, configura `http://10.0.2.2:8080/api/` para acceder al servidor del equipo anfitrión desde el emulador.
6. En `LeccionesActivity.java`, consume `GET /api/lecciones`, interpreta el JSON y muestra los nombres y objetivos en un `ListView`.
7. Crea `EditorActivity.java` y `activity_editor.xml` con título de lección, panel de detalles, editor de texto, botones Compilar/Ejecutar y consola.
8. En `HistorialActivity.java`, consulta `GET /api/historiales` y muestra fecha, estado e identificador de lección.
9. En `ConfiguracionActivity.java`, consulta `GET /api/configuraciones` y muestra las claves y sus valores.
10. Agrega botones para actualizar datos y volver a la pantalla principal.
11. Inicia el backend Spring Boot y verifica desde el navegador del emulador `http://10.0.2.2:8080/api/lecciones`.
12. Ejecuta la aplicación, abre cada pantalla y verifica que se carguen los datos.

## 5. Capturas de pantalla para insertar en GitHub
Para cumplir con el enunciado, **agrega capturas reales** tomadas en tu equipo y súbelas al repositorio en una carpeta `capturas/`. Coloca imágenes Markdown en estos puntos:

- Captura 1: pantalla de creación del proyecto en Android Studio.
- Captura 2: SDK Manager y emulador instalado.
- Captura 3: estructura de carpetas del proyecto.
- Captura 4: diseño XML de la pantalla principal.
- Captura 5: código de `ApiClient.java` y dirección `10.0.2.2`.
- Captura 6: emulador mostrando la pantalla principal.
- Captura 7: lista de lecciones cargada desde la API.
- Captura 8: pantalla de editor de lección.
- Captura 9: pantallas de historial y configuración.
- Captura 10: resultado de **Build > Make Project** sin errores.

Ejemplo para insertar una imagen después de subirla: `![Pantalla principal](capturas/pantalla-principal.png)`.

## 6. Arquitectura y endpoints
El frontend Android realiza consultas HTTP GET en segundo plano mediante `HttpURLConnection`; las respuestas JSON se interpretan con `org.json`. Las pantallas se actualizan en el hilo principal mediante `runOnUiThread`.

| Recurso | Método | Propósito |
|---|---|---|
| `/api/lecciones` | GET | Listar lecciones |
| `/api/historiales` | GET | Mostrar historial persistido |
| `/api/configuraciones` | GET | Mostrar configuración |

## 7. Limitaciones y alcance
- El backend CRUD actual entrega metadatos de lecciones, pero no expone el contenido de `leccion.md` para mostrarlo en Android.
- Los botones Compilar y Ejecutar muestran una explicación: para realizar esas acciones hace falta un servicio de compilación/ejecución en el backend conectado a `guali-dev.jar`. No se simula una compilación real.
- La aplicación requiere que la API y MariaDB estén encendidas y accesibles. La URL `10.0.2.2` funciona en el emulador Android, no necesariamente en un teléfono físico.
- El permiso para HTTP sin cifrar se usa únicamente para pruebas locales; en producción debe utilizarse HTTPS.

## 8. Publicación en GitHub
1. Crea un repositorio en tu cuenta de GitHub.
2. Sube el proyecto y este archivo como `README.md` o `TUTORIAL.md`.
3. Sube las capturas a `capturas/` y agrega las referencias Markdown.
4. Abre el tutorial en GitHub y comprueba que el enlace sea público y funcional.
5. Copia esa URL en el manual técnico PDF.

## 9. Referencias
- Android Developers: https://developer.android.com/studio
- Android Developers, Network operations: https://developer.android.com/develop/connectivity/network-ops/connecting
- GitHub Docs: https://docs.github.com/es/repositories
- Enunciado oficial del proyecto, versión 2.0.0, Fase 3 (páginas 8 y 9).
