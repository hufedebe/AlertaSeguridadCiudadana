package com.alertaciudadana.alertaseguridadciudadana.view.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.alertaciudadana.alertaseguridadciudadana.R;

public class SeguridadActivity extends AppCompatActivity {

    private ImageView img_acoso,img_extraviado, img_peleas, img_robo, img_vandalismo, img_venta_droga, img_violencia_familiar, img_otros;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_seguridad);

        img_acoso = findViewById(R.id.img_acoso);
        img_extraviado = findViewById(R.id.img_extraviado);
        img_peleas = findViewById(R.id.img_peleas);
        img_robo = findViewById(R.id.img_robo);
        img_vandalismo= findViewById(R.id.img_vandalismo);
        img_venta_droga= findViewById(R.id.img_venta_droga);
        img_violencia_familiar=findViewById(R.id.img_violencia_familiar);
        img_otros = findViewById(R.id.img_otros);


        img_acoso.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i_seguridad = new Intent(SeguridadActivity.this,IncidenteActivity.class);
                i_seguridad.putExtra("INCIDENTE", "Acoso");
                i_seguridad.putExtra("IDSUBTIPO",1);
                i_seguridad.putExtra("TIPO","S");
                SeguridadActivity.this.startActivity(i_seguridad);
            }
        });

        img_extraviado.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i_seguridad = new Intent(SeguridadActivity.this,IncidenteActivity.class);
                i_seguridad.putExtra("INCIDENTE", "Extraviado");
                i_seguridad.putExtra("IDSUBTIPO",2);
                i_seguridad.putExtra("TIPO","S");
                SeguridadActivity.this.startActivity(i_seguridad);
            }
        });

        img_peleas.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i_seguridad = new Intent(SeguridadActivity.this,IncidenteActivity.class);
                i_seguridad.putExtra("INCIDENTE", "Peleas");
                i_seguridad.putExtra("IDSUBTIPO",3);
                i_seguridad.putExtra("TIPO","S");
                SeguridadActivity.this.startActivity(i_seguridad);
            }
        });


        img_robo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i_seguridad = new Intent(SeguridadActivity.this,IncidenteActivity.class);
                i_seguridad.putExtra("INCIDENTE", "Robo");
                i_seguridad.putExtra("IDSUBTIPO",4);
                i_seguridad.putExtra("TIPO","S");
                SeguridadActivity.this.startActivity(i_seguridad);
            }
        });

        img_vandalismo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i_seguridad = new Intent(SeguridadActivity.this,IncidenteActivity.class);
                i_seguridad.putExtra("INCIDENTE", "Vandalismo");
                i_seguridad.putExtra("IDSUBTIPO",5);
                i_seguridad.putExtra("TIPO","S");
                SeguridadActivity.this.startActivity(i_seguridad);
            }
        });

        img_venta_droga.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i_seguridad = new Intent(SeguridadActivity.this,IncidenteActivity.class);
                i_seguridad.putExtra("INCIDENTE", "Venta Drogas");
                i_seguridad.putExtra("IDSUBTIPO",6);
                i_seguridad.putExtra("TIPO","S");
                SeguridadActivity.this.startActivity(i_seguridad);
            }
        });

        img_violencia_familiar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i_seguridad = new Intent(SeguridadActivity.this,IncidenteActivity.class);
                i_seguridad.putExtra("INCIDENTE", "Violencia Familiar");
                i_seguridad.putExtra("IDSUBTIPO",7);
                i_seguridad.putExtra("TIPO","S");
                SeguridadActivity.this.startActivity(i_seguridad);
            }
        });

        img_otros.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i_seguridad = new Intent(SeguridadActivity.this,IncidenteActivity.class);
                i_seguridad.putExtra("INCIDENTE", "otro");
                i_seguridad.putExtra("IDSUBTIPO",0);
                i_seguridad.putExtra("TIPO","O");
                SeguridadActivity.this.startActivity(i_seguridad);
            }
        });



    }
}
