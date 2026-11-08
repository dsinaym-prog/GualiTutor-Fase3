Tutorial: Desarrollo del Front-End Android de Guali Tutor — Fase 3
1. Introducción

Guali Tutor es una aplicación educativa desarrollada en Java que permite a los estudiantes practicar programación mediante lecciones, un editor de código y un panel de consola.

En la Fase 3 del proyecto de Programación II se desarrolló una interfaz móvil utilizando Android Studio, Java y XML. La aplicación consume una API REST desarrollada con Spring Boot, que se conecta a una base de datos MariaDB.

2. Objetivo

Desarrollar el Front-End de una aplicación móvil Android que permita visualizar lecciones, escribir código Java, solicitar su compilación y consultar el historial y la configuración mediante una API REST.

3. Prerrequisitos

Para desarrollar el proyecto se utilizaron las siguientes herramientas:

- Windows.
- Android Studio.
- Java Development Kit (JDK) 25 para el backend.
- IntelliJ IDEA.
- Apache Maven.
- Spring Boot.
- MariaDB.
- Git y GitHub.
- Emulador Android.

Se requiere una computadora con recursos suficientes para ejecutar Android Studio y un emulador.

4. Instalación del software

4.1. Instalar Android Studio

1. Ingresar a https://developer.android.com/studio.
2. Descargar el instalador para Windows.
3. Ejecutar el instalador.
4. Seguir las instrucciones del asistente.
5. Instalar el Android SDK y las herramientas recomendadas.
6. Abrir Android Studio.

4.2. Configurar el emulador

1. Abrir Android Studio.
2. Ingresar a Device Manager.
3. Seleccionar la opción para crear un dispositivo virtual.
4. Elegir un teléfono compatible.
5. Seleccionar una imagen de Android disponible.
6. Finalizar la configuración.
7. Iniciar el emulador.

En este proyecto se utilizó un emulador Medium Phone con API 37.

4.3. Preparar el backend

1. Instalar y configurar JDK 25.
2. Instalar IntelliJ IDEA y Maven.
3. Configurar MariaDB.
4. Abrir el proyecto `guali-tutor-api`.
5. Configurar la conexión a la base de datos.
6. Iniciar Spring Boot.
7. Verificar el funcionamiento de la API mediante `http://localhost:8080/api/compilacion/estado`.

5. Crear el proyecto Android

1. Abrir Android Studio.
2. Crear un proyecto Android utilizando Java.
3. Configurar el nombre de la aplicación.
4. Definir el paquete `com.guali.tutor.android`.
5. Configurar el SDK mínimo compatible.
6. Esperar la sincronización de Gradle.

6. Estructura del proyecto

El Front-End está organizado mediante actividades Java y archivos XML.

Clases principales

- `MainActivity.java`: pantalla principal.
- `LeccionesActivity.java`: consulta y muestra las lecciones.
- `EditorActivity.java`: contiene el editor de código y la consola.
- `HistorialActivity.java`: muestra registros obtenidos de la API.
- `ConfiguracionActivity.java`: muestra los valores de configuración.
- `ApiClient.java`: permite realizar consultas HTTP a la API REST.

Archivos de interfaz

Los archivos XML de la carpeta `res/layout` definen la presentación de las pantallas, sus botones, campos de texto y otros elementos visuales.

7. Desarrollo de la pantalla principal

Se creó una pantalla de inicio con opciones para navegar hacia las diferentes funciones de Guali Tutor.

Entre las opciones disponibles se encuentran:

- Mis lecciones.
- Historial.
- Configuración.

Cada botón permite abrir la actividad correspondiente.

<img width="719" height="321" alt="image" src="https://github.com/user-attachments/assets/92f2b54a-5d52-42a5-86c6-6911d24f9ca4" />


8. Desarrollo del módulo de lecciones

La pantalla de lecciones permite consultar la información almacenada en el servidor.

El Front-End realiza una petición HTTP GET al endpoint:

`GET /api/lecciones`

Los datos recibidos se presentan en una lista. Al seleccionar una lección, se abre la pantalla del editor y se muestran sus datos.

<img width="1246" height="625" alt="Captura de pantalla 2026-11-08 200955" src="https://github.com/user-attachments/assets/988ebeb3-f44e-42dd-ace1-d2c14b00c434" />

9. Desarrollo del editor Java

Se implementó una pantalla con los siguientes elementos:

- Título de la lección activa.
- Información del objetivo de la lección.
- Campo editable para escribir código Java.
- Botón Compilar.
- Botón Ejecutar.
- Panel de consola.
- Botón Cerrar lección.

El editor permite escribir y modificar código Java.

<img width="1246" height="625" alt="Captura de pantalla 2026-11-08 201043" src="https://github.com/user-attachments/assets/008d9902-7675-443c-b563-717c40bfb722" />

10. Conexión del botón Compilar

Se implementó la comunicación entre Android Studio y el backend Spring Boot.

Al presionar Compilar:

1. La aplicación obtiene el código escrito en el editor.
2. Construye una solicitud JSON.
3. Envía una petición HTTP POST al endpoint `/api/compilacion/compilar`.
4. El backend procesa la solicitud utilizando el compilador Java.
5. La aplicación recibe el resultado.
6. El panel de consola muestra el mensaje de éxito o los errores.

Para las pruebas locales se utilizó una conexión entre el emulador y el servidor mediante ADB.

Se verificó el resultado:

`Codigo Java compilado correctamente
