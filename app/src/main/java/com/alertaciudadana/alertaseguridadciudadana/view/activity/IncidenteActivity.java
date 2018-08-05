package com.alertaciudadana.alertaseguridadciudadana.view.activity;

import android.content.Intent;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

import com.alertaciudadana.alertaseguridadciudadana.R;

public class IncidenteActivity extends AppCompatActivity {

    ImageButton btn_ubicacion, btn_camera;
    Button btn_registrar;
    static final int REQUEST_IMAGE_CAPTURE = 1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_incidente);

        btn_ubicacion = findViewById(R.id.btn_ubicacion);
        btn_camera = findViewById(R.id.btn_camara);
        btn_registrar = findViewById(R.id.btn_registrar);

        btn_ubicacion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i_ubicacion = new Intent(IncidenteActivity.this,UbicacionActivity.class);
                startActivity(i_ubicacion);

            }
        });

        btn_camera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
                    startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
                }
            }
        });

        btn_registrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i_principal = new Intent(IncidenteActivity.this,MainActivity.class);
                startActivity(i_principal);
            }
        });
    }
}
