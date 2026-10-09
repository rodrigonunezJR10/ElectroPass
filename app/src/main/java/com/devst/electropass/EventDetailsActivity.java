package com.devst.electropass;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.CalendarContract;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Calendar;

public class EventDetailsActivity extends AppCompatActivity {

    private TextView tvBienvenidaUsuario, tvNombreEvento;
    private Button btnVerMapa, btnAgendarCalendario, btnVolverLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_details);

        // Inicializar vistas
        tvBienvenidaUsuario = findViewById(R.id.tvBienvenidaUsuario);
        tvNombreEvento = findViewById(R.id.tvNombreEvento);
        btnVerMapa = findViewById(R.id.btnVerMapa);
        btnAgendarCalendario = findViewById(R.id.btnAgendarCalendario);
        btnVolverLogin = findViewById(R.id.btnVolverLogin);

        // INTENT EXPLÍCITO 3: Recuperar datos pasados por extra desde MainActivity
        String usuario = getIntent().getStringExtra("EXTRA_USUARIO");
        String evento = getIntent().getStringExtra("EXTRA_EVENTO");

        // Validaciones contra valores nulos
        if (usuario != null && !usuario.isEmpty()) {
            tvBienvenidaUsuario.setText("Titular: " + usuario);
        } else {
            tvBienvenidaUsuario.setText("Titular: Asistente General");
        }

        if (evento != null && !evento.isEmpty()) {
            tvNombreEvento.setText("Evento: " + evento);
        }

        // INTENT IMPLÍCITO 4: Abrir la ubicación del festival en Google Maps
        btnVerMapa.setOnClickListener(v -> {
            // Esquema geo con búsqueda por nombre y coordenadas de Espacio Riesco
            Uri gmmIntentUri = Uri.parse("geo:0,0?q=Espacio+Riesco,+Santiago,+Chile");
            Intent mapIntent = new Intent(Intent.ACTION_VIEW, gmmIntentUri);
            mapIntent.setPackage("com.google.android.apps.maps");

            try {
                startActivity(mapIntent);
            } catch (Exception e) {
                // Fallback: abrir en cualquier app de mapas si Google Maps no está instalada
                Intent genericMapIntent = new Intent(Intent.ACTION_VIEW, gmmIntentUri);
                try {
                    startActivity(genericMapIntent);
                } catch (Exception ex) {
                    Toast.makeText(this, "No se encontró una aplicación de mapas", Toast.LENGTH_SHORT).show();
                }
            }
        });

        // INTENT IMPLÍCITO 5: Agregar festival a la agenda del Calendario
        btnAgendarCalendario.setOnClickListener(v -> {
            Calendar fechaInicio = Calendar.getInstance();
            fechaInicio.set(2026, Calendar.DECEMBER, 12, 21, 0); // 12 de Diciembre 2026, 21:00 hrs

            Calendar fechaTermino = Calendar.getInstance();
            fechaTermino.set(2026, Calendar.DECEMBER, 13, 5, 0); // Termina a las 05:00 hrs

            Intent calIntent = new Intent(Intent.ACTION_INSERT);
            calIntent.setData(CalendarContract.Events.CONTENT_URI);
            calIntent.putExtra(CalendarContract.Events.TITLE, "Festival ElectroPass: Afterlife Santiago");
            calIntent.putExtra(CalendarContract.Events.EVENT_LOCATION, "Espacio Riesco, Santiago");
            calIntent.putExtra(CalendarContract.Events.DESCRIPTION, "Pase VIP validado biométricamente.");
            calIntent.putExtra(CalendarContract.EXTRA_CUSTOM_SECRET_KEY, fechaInicio.getTimeInMillis());
            calIntent.putExtra(CalendarContract.EXTRA_EVENT_BEGIN_TIME, fechaInicio.getTimeInMillis());
            calIntent.putExtra(CalendarContract.EXTRA_EVENT_END_TIME, fechaTermino.getTimeInMillis());

            try {
                startActivity(calIntent);
            } catch (Exception e) {
                Toast.makeText(this, "No se encontró app de calendario para registrar el evento", Toast.LENGTH_SHORT).show();
            }
        });

        // Volver a la pantalla de Login
        btnVolverLogin.setOnClickListener(v -> finish());
    }
}