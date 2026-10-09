package com.devst.electropass;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    private EditText etUsuario;
    private TextView tvEstadoAuth;
    private Button btnFaceLogin, btnSoporteEmail, btnSitioWeb;

    // INTENT EXPLÍCITO 1 (Manejo con resultado):
    // Recibe el veredicto biométrico desde FaceScanActivity
    private final ActivityResultLauncher<Intent> faceScanLauncher = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            result -> {
                if (result.getResultCode() == RESULT_OK && result.getData() != null) {
                    boolean verificado = result.getData().getBooleanExtra("auth_exitosa", false);
                    if (verificado) {
                        tvEstadoAuth.setText("Estado: Identidad Verificada ✅");
                        Toast.makeText(this, "Acceso biométrico concedido", Toast.LENGTH_SHORT).show();

                        // INTENT EXPLÍCITO 2: Navegar al detalle del evento pasando extras
                        Intent intentDetalle = new Intent(MainActivity.this, EventDetailsActivity.class);
                        intentDetalle.putExtra("EXTRA_USUARIO", etUsuario.getText().toString().trim());
                        intentDetalle.putExtra("EXTRA_EVENTO", "Afterlife Santiago 2026");
                        startActivity(intentDetalle);
                    } else {
                        tvEstadoAuth.setText("Estado: Verificación Fallida ❌");
                        Toast.makeText(this, "No se reconoció el rostro", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    tvEstadoAuth.setText("Estado: Verificación Cancelada");
                }
            }
    );

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Inicializar vistas
        etUsuario = findViewById(R.id.etUsuario);
        tvEstadoAuth = findViewById(R.id.tvEstadoAuth);
        btnFaceLogin = findViewById(R.id.btnFaceLogin);
        btnSoporteEmail = findViewById(R.id.btnSoporteEmail);
        btnSitioWeb = findViewById(R.id.btnSitioWeb);

        // Disparar Intent Explícito hacia FaceScanActivity
        btnFaceLogin.setOnClickListener(v -> {
            String usuario = etUsuario.getText().toString().trim();
            // Validación de campo vacío (Buenas prácticas / Rúbrica)
            if (usuario.isEmpty()) {
                etUsuario.setError("Ingresa tu usuario antes de iniciar el escaneo");
                etUsuario.requestFocus();
                return;
            }

            Intent intentScan = new Intent(MainActivity.this, FaceScanActivity.class);
            intentScan.putExtra("EXTRA_USER_NAME", usuario);
            faceScanLauncher.launch(intentScan);
        });

        // INTENT IMPLÍCITO 1: Enviar correo de soporte prellenado
        btnSoporteEmail.setOnClickListener(v -> {
            Intent emailIntent = new Intent(Intent.ACTION_SENDTO);
            emailIntent.setData(Uri.parse("mailto:soporte@electropass.cl"));
            emailIntent.putExtra(Intent.EXTRA_SUBJECT, "Consulta Tickets / Acceso Biométrico");
            emailIntent.putExtra(Intent.EXTRA_TEXT, "Hola equipo de ElectroPass, necesito soporte con mi cuenta.");

            try {
                startActivity(emailIntent);
            } catch (Exception e) {
                Toast.makeText(this, "No se encontró cliente de correo instalado", Toast.LENGTH_SHORT).show();
            }
        });

        // INTENT IMPLÍCITO 2: Ver sitio oficial en navegador
        btnSitioWeb.setOnClickListener(v -> {
            Uri webpage = Uri.parse("https://www.residentadvisor.net");
            Intent webIntent = new Intent(Intent.ACTION_VIEW, webpage);
            try {
                startActivity(webIntent);
            } catch (Exception e) {
                Toast.makeText(this, "No se pudo abrir el navegador web", Toast.LENGTH_SHORT).show();
            }
        });
    }
}