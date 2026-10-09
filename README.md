# 🎫 ElectroPass - Acceso Biométrico & Tickets para Eventos Electrónicos

Aplicación Android desarrollada en Java para la gestión de accesos VIP y compra de tickets en eventos de música electrónica[cite: 6]. Implementa navegación multicomponente mediante **Intents Explícitos e Implícitos**, validaciones en tiempo de ejecución y procesamiento en segundo plano con **Threads**[cite: 1, 3, 6, 8].

---

## 📱 Especificaciones Técnicas

* **Plataforma:** Android Studio
* **Lenguaje:** Java 8 / 11[cite: 6]
* **Compile SDK:** 34
* **Min SDK:** 26 (Android 8.0 Oreo)
* **Gradle / AGP Version:** Android Gradle Plugin 8.x[cite: 8]
* **Rama de desarrollo:** `feature/intents`[cite: 3, 8]

---

## 🚀 Listado de Intents Implementados

Cumplimiento estricto de los 8 intents requeridos por la pauta de evaluación[cite: 3, 7]:

### 🔹 3 Intents Explícitos

1. **`MainActivity` ➔ `FaceScanActivity` (Con Resultado)**[cite: 8]
    * **Mecanismo:** `registerForActivityResult(ActivityResultContracts.StartActivityForResult())`[cite: 8].
    * **Objetivo:** Lanza la pantalla de escaneo facial y espera la confirmación de identidad del usuario para habilitar el acceso[cite: 8].
2. **Procesamiento de Biometría en Background (`Thread`)**[cite: 1, 3, 6]
    * **Mecanismo:** Hilo secundario (`new Thread().start()`) con actualización en `runOnUiThread()`[cite: 1, 3, 6].
    * **Objetivo:** Simula el cálculo y vectorización de rasgos faciales durante 2.5 segundos sin congelar la interfaz de usuario[cite: 1, 3].
3. **`MainActivity` ➔ `EventDetailsActivity` (Traspaso de Datos)**[cite: 8]
    * **Mecanismo:** `Intent.putExtra()` y `getIntent().getStringExtra()`[cite: 8].
    * **Objetivo:** Transfiere el nombre de usuario autenticado y el festival seleccionado a la pantalla de detalle del ticket[cite: 8].
###

   Capturas
<img width="696" height="422" alt="image" src="https://github.com/user-attachments/assets/2eeacf16-33c5-4db4-a29d-cfee6335cc41" />
<img width="183" height="305" alt="image" src="https://github.com/user-attachments/assets/4d0ec0dc-0eb8-4172-8974-87de42e333cb" />
<img width="358" height="299" alt="image" src="https://github.com/user-attachments/assets/e33bb3f5-3eb2-4b8b-85e2-4dbc47708cd5" />
<img width="391" height="311" alt="image" src="https://github.com/user-attachments/assets/a2a3758e-150e-4db9-8403-1fa20c529100" />
<img width="288" height="386" alt="image" src="https://github.com/user-attachments/assets/a7a9449d-82a4-4bb2-81b7-1721485120d2" />


### 🔸 5 Intents Implícitos

1. **Captura Fotográfica (`MediaStore.ACTION_IMAGE_CAPTURE`)**[cite: 7]
    * Abre la cámara del dispositivo para capturar la foto miniatura del rostro del asistente[cite: 7].
2. **Soporte Técnico de Tickets (`Intent.ACTION_SENDTO` con `mailto:`)**[cite: 7]
    * Abre el cliente de correo prellenando el destinatario institucional (`soporte@electropass.cl`) y el asunto de ayuda[cite: 7].
3. **Line-up y Festivales Oficiales (`Intent.ACTION_VIEW` con `https://`)**[cite: 7]
    * Abre el navegador web predeterminado redirigiendo al sitio del evento[cite: 7].
4. **Ubicación del Recinto en Google Maps (`Intent.ACTION_VIEW` con esquema `geo:`)**[cite: 7]
    * Abre Google Maps marcando las coordenadas y recinto del festival (Espacio Riesco)[cite: 7].
5. **Agendamiento en Calendario (`Intent.ACTION_INSERT` con `Events.CONTENT_URI`)**[cite: 7]
    * Abre la app nativa de calendario para agendar fecha, hora y descripción del evento[cite: 7].

---

## 🛡️ Validaciones y Manejo de Errores

* **Permisos en Runtime:** Validación y solicitud dinámica para `android.permission.CAMERA` antes de invocar la cámara[cite: 3, 8].
* **Control de Nulos y Vacíos:** Comprobación estricta de campos de texto antes de procesar el login[cite: 3].
* **Protección ante Cierres Inesperados (Try-Catch):** Bloques de captura de excepciones en cada Intent implícito por si el dispositivo no posee una app asociada instalada[cite: 3].

---

## 🧪 Pasos de Prueba

1. **Login:** Ingrese un nombre de usuario en el campo de texto y pulse *Ingresar con Verificación Facial*.
2. **Cámara y Threads:** Presione *Abrir Cámara y Capturar Rostro*, tome una foto y observe la barra de progreso mientras el hilo secundario procesa la biometría. Pulse *Confirmar e Ingresar*.
3. **Detalle de Ticket:** Verifique la recepción de su usuario en la vista de detalle y presione los botones de *Google Maps* y *Agendar Evento en Calendario*.
4. **Soporte y Web:** Desde la vista principal, pruebe los botones de correo de soporte y sitio web oficial.

---

## 📸 Capturas de Pantalla
*(Espacio reservado para agregar mínimo 4 capturas una vez compilado y probado en el emulador)*[cite: 8]
