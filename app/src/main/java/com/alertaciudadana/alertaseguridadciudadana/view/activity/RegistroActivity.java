package com.alertaciudadana.alertaseguridadciudadana.view.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.alertaciudadana.alertaseguridadciudadana.R;

public class RegistroActivity extends AppCompatActivity {


    private ImageView btn_bienestar, btn_seguridad;
    private Button  btn_otro;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro);

        btn_bienestar = findViewById(R.id.btn_bienestar);
        btn_seguridad = findViewById(R.id.btn_seguridad);
        btn_otro=findViewById(R.id.btn_otro);

        btn_bienestar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i_bienestar = new Intent(RegistroActivity.this,BienestarActivity.class);
                RegistroActivity.this.startActivity(i_bienestar);
            }
        });

        btn_seguridad.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i_seguridad= new Intent(RegistroActivity.this,SeguridadActivity.class);
                RegistroActivity.this.startActivity(i_seguridad);

            }
        });

        btn_otro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i_incidente = new Intent(RegistroActivity.this,IncidenteActivity.class);
                RegistroActivity.this.startActivity(i_incidente);
            }
        });


    }
}
