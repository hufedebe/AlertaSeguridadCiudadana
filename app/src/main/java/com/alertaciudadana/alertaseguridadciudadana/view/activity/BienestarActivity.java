package com.alertaciudadana.alertaseguridadciudadana.view.activity;

import android.content.Intent;
import android.media.Image;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.alertaciudadana.alertaseguridadciudadana.R;

public class BienestarActivity extends AppCompatActivity {


    private ImageView img_accidente,img_basura,img_construccion,img_problemas_viales,img_ruido,img_otros;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bienestar);


        img_accidente = findViewById(R.id.img_accidente);
        img_basura = findViewById(R.id.img_basura);
        img_construccion = findViewById(R.id.img_construccion);
        img_problemas_viales = findViewById(R.id.img_problemas_viales);
        img_ruido = findViewById(R.id.img_ruido);
        img_otros = findViewById(R.id.img_otros);



        img_accidente.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i_bienestar = new Intent(BienestarActivity.this,IncidenteActivity.class);
                i_bienestar.putExtra("INCIDENTE", "Accidente");
                i_bienestar.putExtra("IDSUBTIPO",1);
                i_bienestar.putExtra("TIPO","B");
                BienestarActivity.this.startActivity(i_bienestar);
            }
        });

        img_basura.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i_bienestar = new Intent(BienestarActivity.this,IncidenteActivity.class);
                i_bienestar.putExtra("INCIDENTE", "Basura en Vía Pública");
                i_bienestar.putExtra("TIPO","B");
                i_bienestar.putExtra("IDSUBTIPO",2);
                BienestarActivity.this.startActivity(i_bienestar);
            }
        });

        img_construccion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i_bienestar = new Intent(BienestarActivity.this,IncidenteActivity.class);
                i_bienestar.putExtra("INCIDENTE", "Construcción Ilegal");
                i_bienestar.putExtra("TIPO","B");
                i_bienestar.putExtra("IDSUBTIPO",3);
                BienestarActivity.this.startActivity(i_bienestar);
            }
        });

        img_problemas_viales.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i_bienestar = new Intent(BienestarActivity.this,IncidenteActivity.class);
                i_bienestar.putExtra("INCIDENTE", "Problemas Viales");
                i_bienestar.putExtra("IDSUBTIPO",4);
                i_bienestar.putExtra("TIPO","B");
                BienestarActivity.this.startActivity(i_bienestar);
            }
        });

        img_ruido.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i_bienestar = new Intent(BienestarActivity.this,IncidenteActivity.class);
                i_bienestar.putExtra("INCIDENTE", "Ruidos Molestos");
                i_bienestar.putExtra("IDSUBTIPO",5);
                i_bienestar.putExtra("TIPO","B");
                BienestarActivity.this.startActivity(i_bienestar);
            }
        });
        img_otros.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i_bienestar = new Intent(BienestarActivity.this,IncidenteActivity.class);
                i_bienestar.putExtra("INCIDENTE", "otro");
                i_bienestar.putExtra("IDSUBTIPO",0);
                i_bienestar.putExtra("TIPO","O");
                BienestarActivity.this.startActivity(i_bienestar);

            }
        });


    }
}
